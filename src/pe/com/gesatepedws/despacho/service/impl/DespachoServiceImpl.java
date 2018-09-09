package pe.com.gesatepedws.despacho.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.despacho.dao.DespachoDAO;
import pe.com.gesatepedws.despacho.service.DespachoService;
import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;
import pe.com.gesatepedws.model.extend.DespachoResponse;
import pe.com.gesatepedws.validacion.service.impl.DespachoValidationCode;
import pe.com.gesatepedws.validacion.service.impl.DespachoValidator;

@Service
public class DespachoServiceImpl implements DespachoService {

	private static final int CODIGO_RESPUESTA_EXITO = 1;
	private static final int CODIGO_RESPUESTA_FALLO_VALIDACION = -1;
	private static final int CODIGO_RESPUESTA_FALLO_GENERAL = -2;
	private static final int CODIGO_RESPUESTA_NUMERO_VERIFICACION_INCORRECTO = -3;
	
	@Autowired
	private DespachoDAO despachoDAO;
	
	@Override
	public Ruta getRuta(String brevete) {
		return this.despachoDAO.getRuta(brevete, new Date());
	}

	@Override
	public List<DetallePedido> getDetalle(String codigoPedido, String codigoBodega) {
		return this.despachoDAO.getDetalle(codigoPedido, codigoBodega);
	}

	@Override
	public String getEstado(String ruta) {
		return this.despachoDAO.getEstado(ruta);
	}

	@Override
	public List<MotivoPedido> getMotivos() {
		return this.despachoDAO.getMotivos("NCUM");
	}

	@Override
	public DespachoResponse registrarAtencion(String codigoRuta, DetalleRuta detalle) {
		DespachoResponse response = new DespachoResponse();
		DespachoValidator validator = new DespachoValidator(detalle, despachoDAO)
				.conNumeroVerificacionObligatorio()
				.conNumeroVerificacionCorrecto()
		;
		
		if(validator.valid()) {
			boolean result = this.despachoDAO.registrarAtencion(codigoRuta, detalle);
			if(result) {
				response.setCodigo(CODIGO_RESPUESTA_EXITO);
				response.setMensaje("Atención para despacho de pedido " 
				+ detalle.getPedido().getCodigo() 
				+ " registrado correctamente");
			} else {
				response.setCodigo(CODIGO_RESPUESTA_FALLO_GENERAL);
				response.setMensaje("Ocurrió un problema inesperado, "
						+ "por favor comuníquese con el administrador del servicio");
			}
		} else if(validator.getFailMap().containsKey(DespachoValidationCode.NUMERO_VERIFICACION_INCORRECTO)
				&& validator.getFailMap().get(DespachoValidationCode.NUMERO_VERIFICACION_INCORRECTO)){
			response.setCodigo(CODIGO_RESPUESTA_NUMERO_VERIFICACION_INCORRECTO);
			response.setMensaje(DespachoValidationCode.NUMERO_VERIFICACION_INCORRECTO.getMensaje());
		} else {
			response.setCodigo(CODIGO_RESPUESTA_FALLO_VALIDACION);
			Map<DespachoValidationCode, Boolean> failMap = validator.getFailMap();
			for(DespachoValidationCode validation : failMap.keySet()) {
				if(failMap.get(validation)) {
					response.getMensajes().add(validation.getMensaje());
				}
			}
		}
		
		return response;
	}

	@Override
	public DespachoResponse registrarIncumplimiento(String codigoRuta, DetalleRuta detalle) {
		DespachoResponse response = new DespachoResponse();
		DespachoValidator validator = new DespachoValidator(detalle, despachoDAO)
				.conMotivoObligatorio()
		;
		
		if(validator.valid()) {
			boolean result = this.despachoDAO.registrarIncumplimiento(codigoRuta, detalle);
			if(result) {
				response.setCodigo(CODIGO_RESPUESTA_EXITO);
				response.setMensaje("Incumplimiento del despacho de pedido " 
				+ detalle.getPedido().getCodigo() 
				+ " registrado correctamente");
			} else {
				response.setCodigo(CODIGO_RESPUESTA_FALLO_GENERAL);
				response.setMensaje("Ocurrió un problema inesperado, "
						+ "por favor comuníquese con el administrador del servicio");
			}
		} else {
			response.setCodigo(CODIGO_RESPUESTA_FALLO_VALIDACION);
			Map<DespachoValidationCode, Boolean> failMap = validator.getFailMap();
			for(DespachoValidationCode validation : failMap.keySet()) {
				if(failMap.get(validation)) {
					response.getMensajes().add(validation.getMensaje());
				}
			}
		}
		
		
		return response;
	}

	@Override
	public List<Chofer> getChoferes() {
		return this.despachoDAO.getChoferes();
	}

	@Override
	public String getImagen(String codigoRuta, String codigoPedido) {
		return this.despachoDAO.getImagen(codigoRuta, codigoPedido);
	}

}
