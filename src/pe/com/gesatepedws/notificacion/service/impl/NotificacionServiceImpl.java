package pe.com.gesatepedws.notificacion.service.impl;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.common.GesatepedConstants;
import pe.com.gesatepedws.common.GesatepedUtils;
import pe.com.gesatepedws.mail.service.MailService;
import pe.com.gesatepedws.mail.service.impl.MailResponseCodes;
import pe.com.gesatepedws.model.ControllerTienda;
import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.model.extend.MensajeData;
import pe.com.gesatepedws.model.extend.NotificacionResponse;
import pe.com.gesatepedws.model.extend.TiendaMensajeData;
import pe.com.gesatepedws.notificacion.dao.NotificacionDAO;
import pe.com.gesatepedws.notificacion.service.NotificacionService;
import pe.com.gesatepedws.sms.service.SMSService;
import pe.com.gesatepedws.sms.service.impl.SMSResponseCodes;

@Service("NotificacionService")
public class NotificacionServiceImpl implements NotificacionService {

	private static final Logger logger = Logger.getLogger(NotificacionServiceImpl.class);
	
	private static final String VALIDATION_FAIL_MSG_TEMPLATE = "El mensaje no se envío, razón: %s";
	private static final String REGISTRO_FAIL_MSG_TEMPLATE = "Mensaje a %s sobre pedido %s, se envio al numero %s satisfactoriamente, pero no se pudo registrar en el sistema.";
	private static final String SUCCESS_MSG_TEMPLATE = "Mensaje a %s sobre pedido %s, se envio al numero %s satisfactoriamente";
	
	public static final Integer SUCCESS_CODE = 1;
	public static final Integer VALIDATION_FAIL_CODE = -1;
	public static final Integer SMS_FAIL_CODE = -2;
	public static final Integer REGISTRO_FAIL_CODE = -3;
	public static final Integer MAIL_FAIL_CODE = -4;
	
	@Autowired
	private NotificacionDAO notificacionDAO;
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public boolean notificarAlertaStockMinimo(List<Kardex> kardexs) {
		List<Kardex> notificables = new ArrayList<>();
		for (Kardex kardex : kardexs) {
			if(kardex.getStockMinimo() > kardex.getStockActual()) {
				notificables.add(kardex);
			}
		}
		
		System.out.println("Notificacion:");
		for (Kardex kardex : notificables) {
			System.out.println(">>" + kardex.getProducto().getNombre());
		}
		
		return false;
	}

	@Override
	public NotificacionResponse notificarVentanaHoraria(String codigoHojaRuta, String codigoPedido) {
		NotificacionResponse response = new NotificacionResponse();
		MensajeData mensajeData = this.notificacionDAO
				.obtenerMensajeData(codigoHojaRuta, codigoPedido);
		procesarYEnviarMensajeSMS(mensajeData);
		return response;
	}

	@Override
	public List<NotificacionResponse> notificarTodo() {
		List<NotificacionResponse> responses = new ArrayList<>();
		
		List<MensajeData> mensajes = this.notificacionDAO
				.obtenerMensajesParaTodasLasRutas(GesatepedUtils.getDiaSiguiente());
		
		Map<String,List<MensajeData>> mensajeTiendaMap = new HashMap<>(); 
		
		int smsCounter = 0;
		int smsFailCounter = 0;
		for (MensajeData mensajeData : mensajes) {
			//Deben agruparse mensajes para retiro en tienda (por cada bodega)
			if(mensajeData.getTiendaDespacho() != null) {
				mensajeTiendaMap.putIfAbsent(
						mensajeData.getTiendaDespacho().getCodigo(), 
						new ArrayList<>());
				mensajeTiendaMap.get(mensajeData.getTiendaDespacho().getCodigo())
				.add(mensajeData);
			} else {
				//Los mensajes para clientes (no retiro)
				NotificacionResponse smsResponse = procesarYEnviarMensajeSMS(mensajeData);
				responses.add(smsResponse);
				if(smsResponse.getCodigo() == SUCCESS_CODE) {
					smsCounter++;
				} else {
					smsFailCounter++;
				}
			}
		}
		
		
		//Mensajes para tienda se envia por email
		int mailFails = 0;
		for(List<MensajeData> mensajesData : mensajeTiendaMap.values()) {
			NotificacionResponse mailResponse = this.procesarYEnviarMensajesEmail(mensajesData);
			responses.add(mailResponse);
			if(mailResponse.getCodigo() == SUCCESS_CODE) {
				mailFails++;
			}
		}
		
		
		int failCounter = 0;
		for(NotificacionResponse response: responses) {
			if(response.getCodigo() != SUCCESS_CODE) {
				failCounter++;
			}
		}
		logger.info("******** RESUMEN NOTIFICACION ************");
		logger.info("Total de pedidos procesados: " + responses.size());
		logger.info("Mensajes SMS enviados con éxito: " + smsCounter);
		logger.info("Mensajes SMS fallidos: " + smsFailCounter);
		logger.info("Email enviados con éxito: " + mailFails);
		logger.info("Email fallidos: " + (mensajeTiendaMap.size()-mailFails));
		logger.info("Notificaciones no transmitidas (SMS o EMAIL):" + failCounter);
		return responses;
	}
	
	private NotificacionResponse procesarYEnviarMensajeSMS(MensajeData mensajeData) {
		NotificacionResponse response = new NotificacionResponse();
		if(mensajeData != null 
				&& mensajeData.getNumero() != null 
				&& !mensajeData.getNumero().isEmpty()
				&& !mensajeData.getNumero().equals("-")) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es","PE"));
			String mensaje = String.format(
					GesatepedConstants.SMS_MESSAGE_TEMPLATE, 
					mensajeData.getDestinatario(),
					mensajeData.getCodigoPedido(),
					mensajeData.getNumeroVerificacion(),
					dateFormat.format(mensajeData.getFechaDespacho()),
					mensajeData.getRangoHorario()
				);
			Integer smsResponseCode = this.smsService.sendSMS(mensaje, mensajeData.getNumero());
			
			if(smsResponseCode == SMSResponseCodes.SUCESS) {
				boolean isSuccess = this.notificacionDAO.registrarNotificacion(mensajeData);
				if(isSuccess) {
					response.setCodigo(SUCCESS_CODE);
					response.addMensaje(String.format(SUCCESS_MSG_TEMPLATE, 
							mensajeData.getDestinatario(), 
							mensajeData.getCodigoPedido(), 
							mensajeData.getNumero() ));
				} else {
					response.setCodigo(REGISTRO_FAIL_CODE);
					response.addMensaje(String.format(REGISTRO_FAIL_MSG_TEMPLATE, 
							mensajeData.getDestinatario(), 
							mensajeData.getCodigoPedido(), 
							mensajeData.getNumero() ));
				}
			} else {
				response.setCodigo(SMS_FAIL_CODE);
				response.addMensaje("Fallo al enviar mensaje a " + 
						mensajeData.getDestinatario());
			}
			
		}  else {
			response.setCodigo(VALIDATION_FAIL_CODE);
			response.addMensaje(String.format(VALIDATION_FAIL_MSG_TEMPLATE, 
					this.obtenerRazonFalla(mensajeData)));
		}
		return response;
	}
	
	private NotificacionResponse procesarYEnviarMensajesEmail(List<MensajeData> mensajes) {
		NotificacionResponse response = new NotificacionResponse();
		
		TiendaMensajeData dataControllerTienda = generarDataControllerTienda(mensajes);
		
		String mensajeControllerTienda = generarMensajeControllerTienda(dataControllerTienda);
		
		ControllerTienda controllerTienda = this.notificacionDAO.getControllerTienda(dataControllerTienda.getTienda().getCodigo());
		System.out.println("Enviando email a: " + controllerTienda.getEmail());
		Integer mailResponse = this.mailService.sendEmail(mensajeControllerTienda,controllerTienda.getEmail());
		
		if(mailResponse == MailResponseCodes.SUCESS) {
			for (MensajeData mensajeData : mensajes) {
				this.notificacionDAO.registrarNotificacion(mensajeData);
			}
			response.setCodigo(SUCCESS_CODE);
		} else {
			response.setCodigo(MAIL_FAIL_CODE);
		}
		return response;
	}
	
	private String obtenerRazonFalla(MensajeData mensajeData) {
		if(mensajeData == null) {
			return "Imposible obtener parametros para mensaje";
		} else if (mensajeData.getNumero() == null || 
				mensajeData.getNumero().isEmpty() ||
				mensajeData.getNumero().equals("-")
				) {
			return "Destinatario " + mensajeData.getDestinatario()  +" no tiene número";
		}
		throw new IllegalStateException("Error inesperado para " + mensajeData.getDestinatario());
	}
	
	private TiendaMensajeData generarDataControllerTienda(List<MensajeData> mensajes) {
		if(mensajes == null || mensajes.isEmpty() || mensajes.get(0) == null) {
			throw new IllegalStateException("No puede construirse mensaje a controller sin data");
		}
		
		TiendaMensajeData result = new TiendaMensajeData(
				mensajes.get(0).getNumeroVerificacion(), 
				mensajes.get(0).getFechaDespacho(), 
				mensajes.get(0).getTiendaDespacho());
		
		for (MensajeData mensajeData : mensajes) {
			result.addMensaje(mensajeData);
		}
		
		return result;
	}
	
	private String generarMensajeControllerTienda(TiendaMensajeData data) {
		VelocityContext context = new VelocityContext();
		context.put("data", data);
		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init( p ); 
		Template template = Velocity.getTemplate( "templates/email_html.vm");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();
	}

}
