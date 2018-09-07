package pe.com.gesatepedws.validacion.service.impl;

import java.util.HashMap;
import java.util.Map;

import pe.com.gesatepedws.despacho.dao.DespachoDAO;
import pe.com.gesatepedws.model.DetalleRuta;

public class DespachoValidator {

	private DetalleRuta detalleRuta;
	private DespachoDAO datasource;
	
	private Map<String,Boolean> failMap;
	
	public DespachoValidator(DetalleRuta detalleRuta, DespachoDAO datasource) {
		this.failMap = new HashMap<>();
		this.detalleRuta = detalleRuta;
		this.datasource = datasource;
	}
	
	public DespachoValidator conNumeroVerificacionObligatorio() {
		this.failMap.put("Debe ingresar número de verificación", 
				this.detalleRuta.getPedido() == null || 
				this.detalleRuta.getPedido().getNumeroVerificacion() == null);
		return this;
	}
	
	public DespachoValidator conMotivoObligatorio() {
		this.failMap.put("Debe elegir un motivo para el incumplimiento", 
				this.detalleRuta.getPedido() == null || 
				this.detalleRuta.getMotivo() == null ||
				this.detalleRuta.getMotivo().getCodigo() == null ||
				this.detalleRuta.getMotivo().getCodigo().isEmpty());
		return this;
	}
	
	public DespachoValidator conNumeroVerificacionCorrecto() {
		if(this.detalleRuta.getPedido() == null || 
				this.detalleRuta.getPedido().getNumeroVerificacion() == null) {
			return this;
		}
		Integer codigo = this.datasource.getNumeroVerificacion(detalleRuta.getPedido().getCodigo());
		this.failMap.put("El número de verificación es incorrecto", 
				codigo.intValue() != this.detalleRuta.getPedido().getNumeroVerificacion().intValue());
		return this;
	}
	
	public boolean valid() {
		for(Boolean fail: failMap.values()) {
			if(fail) {
				return false;
			}
		}
		return true;
	}

	public Map<String, Boolean> getFailMap() {
		return this.failMap;
	}
	
	
}
