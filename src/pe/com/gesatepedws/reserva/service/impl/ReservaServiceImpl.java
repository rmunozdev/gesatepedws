package pe.com.gesatepedws.reserva.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.extend.ReservaResponse;
import pe.com.gesatepedws.parametro.service.ParametroService;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;
import pe.com.gesatepedws.reserva.service.ReservaService;
import pe.com.gesatepedws.validacion.service.impl.GoogleMapsValidatorUtils;
import pe.com.gesatepedws.validacion.service.impl.PedidoValidator;

@Service
public class ReservaServiceImpl implements ReservaService {
	
	private static final int CODIGO_RESPUESTA_FALLO_VALIDACION = -1;
	private static final int CODIGO_RESPUESTA_FALLA_DAO = -2;
	private static final int CODIGO_RESPUESTA_EXITO = 0;
	
	private static final Logger logger = Logger.getLogger(ReservaServiceImpl.class);
	
	@Autowired
	private ReservaDAO reservaDAO;
	
	public ReservaServiceImpl(@Autowired ParametroService parametroService) {
		//Inicializacion de componentes estaticos
		GoogleMapsValidatorUtils.setAPIKey(parametroService.getGoogleMapAPIKey());
	}
	
	@Override
	public ReservaResponse reservarPedido(Pedido pedido) {
		ReservaResponse response = new ReservaResponse();
		
		PedidoValidator pedidoValidator = new PedidoValidator(pedido, reservaDAO)
				.conCodigoPedidoOobligatorio()
				.conCodigoDeClienteObligatorio();
		
		if(pedidoValidator.valid()) {
			pedidoValidator
				.conFechaSolicitudObligatoria()
				.conCodigoPedidoNoReutilizado()
				.conNumeroReservaNoReutilizado()
				.conFechaVentaObligatoria()
				.conFechaDespachoObligatoria()
				.conFechaDespachoAdecuada()
				.conDireccionYDistritoDespachoAdecuados()
				.conAlMenosUnDetalle()
				.conDetallesAdecuados()
				.conCodigoDistritoExistente()
				.conCodigoTiendaExistente()
				.sinConflictoEmailCliente()
				.sinConflictoTelefonoCliente()
			;
			
			Cliente cliente = this.reservaDAO.getCliente(pedido.getCliente().getCodigo());
			if(cliente == null) {
				//Solo en nuevo cliente se valida DNI y nombres
				pedidoValidator
				.conDatosDeClienteObligatorios()
				.conDNIClienteNoReutilizado()
				.conNombresYApellidosClienteAdecuados()
				.conNumeroDNIClienteAdecuado()
				;
			}
			
			//Se revisan una vez se establecieron controles anteriores
			pedidoValidator
				.conEmailClienteAdecuado()
				.conTelefonoClienteAdecuado()
				.conDireccionDeClienteAdecuada();
			
			
			if(pedidoValidator.valid()) {
				//Fill cod_ped on detalles
				for(DetallePedido detalle : pedido.getDetalles()) {
					detalle.setPedido(pedido);
				}
				
				if(pedido.getTiendaDespacho() != null && 
						pedido.getTiendaDespacho().getCodigo() != null && 
						!pedido.getTiendaDespacho().getCodigo().isEmpty()) {
					Date fechaRetiroEnTienda = this.obtenerFechaRetiroEnTienda(pedido);
					pedido.setFechaRetiroTienda(fechaRetiroEnTienda);
					logger.info(String.format("Se establecio fecha retiro en tienda: %s para pedido %s", 
							fechaRetiroEnTienda,pedido.getCodigo()));
				}
				
				if(this.reservaDAO.reservarPedido(pedido)) {
					response.setCodigo(CODIGO_RESPUESTA_EXITO);
					response.getMensajes().add("La reserva de pedido fue realizada con �xito");
					
				} else {
					response.setCodigo(CODIGO_RESPUESTA_FALLA_DAO);
				}
				
			}
		}
		
		if(!pedidoValidator.valid()) {
			response.setCodigo(CODIGO_RESPUESTA_FALLO_VALIDACION);
			for(String failName : pedidoValidator.getFailMap().keySet()) {
				if(pedidoValidator.getFailMap().get(failName)) {
					response.getMensajes().add(failName);
				}
			}
		}
		return response;
		
	}
	
	private Date obtenerFechaRetiroEnTienda(Pedido pedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pedido.getFechadespacho());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
}
