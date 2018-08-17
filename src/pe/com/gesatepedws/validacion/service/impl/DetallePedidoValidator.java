package pe.com.gesatepedws.validacion.service.impl;

import java.util.HashMap;
import java.util.Map;

import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;

public class DetallePedidoValidator {

	private DetallePedido detalle;
	private ReservaDAO datasource;
	private Map<String, Boolean> failMap;
	
	public DetallePedidoValidator(DetallePedido detalle, ReservaDAO datasource) {
		this.detalle = detalle;
		this.datasource = datasource;
		this.failMap = new HashMap<>();
	}
	
	public DetallePedidoValidator conDetalleAdecuado() {
		this
		.conCodigoProductoObligatorio()
		.conCodigoBodegaObligatorio()
		.conCantidadProductoObligatorio()
		.conCodigoProductoExistente()
		.conCodigoBodegaExistente()
		.conCantidadProductoMinima()
		;
		return this;
	}
	
	public DetallePedidoValidator conCodigoProductoObligatorio() {
		failMap.put("Debe ingresar el codigo de Producto", 
				this.detalle.getProducto() == null
				|| this.detalle.getProducto().getCodigo() == null
				|| this.detalle.getProducto().getCodigo().isEmpty());
		return this;
	}
	
	public DetallePedidoValidator conCantidadProductoObligatorio() {
		failMap.put("Debe ingresar la cantidad de producto", 
				this.detalle.getCantidadProducto() == null);
		return this;
	}
	
	public DetallePedidoValidator conCodigoProductoExistente() {
		if(this.detalle.getProducto() != null 
				&& this.detalle.getProducto().getCodigo() != null
				&& !this.detalle.getProducto().getCodigo().isEmpty()) {
			failMap.put("Codigo de producto "+ this.detalle.getProducto().getCodigo() + " no existe", 
					!datasource.existeProducto(this.detalle.getProducto().getCodigo()));	
		}
		return this;
	}
	
	public DetallePedidoValidator conCantidadProductoMinima() {
		if(this.detalle.getCantidadProducto() != null) {
			failMap.put("Cantidad de producto debe ser mayor a cero", this.detalle.getCantidadProducto() < 1);
		}
		return this;
	}
	
	public DetallePedidoValidator conCodigoBodegaObligatorio() {
		failMap.put("Debe ingresar el código de bodega", 
				this.detalle.getBodega() == null 
				|| this.detalle.getBodega().getCodigo() == null 
				|| this.detalle.getBodega().getCodigo().isEmpty());
		return this;
	}
	
	public DetallePedidoValidator conCodigoBodegaExistente() {
		if(this.detalle.getBodega() != null 
				&& this.detalle.getBodega().getCodigo() != null 
				&& !this.detalle.getBodega().getCodigo().isEmpty()) {
			failMap.put("La bodega o nodo " + this.detalle.getBodega().getCodigo() + " no se encuentra registrada", 
					!this.datasource.existeBodega(
							this.detalle.getBodega().getCodigo()));
		}
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
	
	public Map<String,Boolean> getFailMap() {
		return this.failMap;
	}
	
}
