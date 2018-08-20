package pe.com.gesatepedws.notificacion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.common.GesatepedConstants;
import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.model.extend.MensajeData;
import pe.com.gesatepedws.notificacion.dao.NotificacionDAO;
import pe.com.gesatepedws.notificacion.service.NotificacionService;
import pe.com.gesatepedws.sms.service.SMSService;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Autowired
	private NotificacionDAO notificacionDAO;
	
	@Autowired
	private SMSService smsService;
	
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
	public boolean notificarVentanaHoraria(String codigoHojaRuta, String codigoPedido) {
		MensajeData mensajeData = this.notificacionDAO
				.obtenerMensajeData(codigoHojaRuta, codigoPedido);
		
		if(mensajeData != null) {
			String mensaje = String.format(
					GesatepedConstants.SMS_MESSAGE_TEMPLATE, 
					mensajeData.getCliente(),
					mensajeData.getNumeroReserva(),
					mensajeData.getNumeroVerificacion(),
					mensajeData.getFechaDespacho(),
					mensajeData.getRangoHorario()
				);
			return this.smsService.sendSMS(mensaje, mensajeData.getNumero());
		}
		
		return false;
	}

}
