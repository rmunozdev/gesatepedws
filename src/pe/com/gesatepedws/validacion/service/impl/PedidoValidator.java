package pe.com.gesatepedws.validacion.service.impl;

import java.util.HashMap;
import java.util.Map;

import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;

public class PedidoValidator {

	private final Pedido pedido;
	private ReservaDAO datasource;
	private ClienteValidator clienteValidator;
	private Map<String,Boolean> failMap;
	
	public PedidoValidator(Pedido pedido, ReservaDAO datasource) {
		this.pedido = pedido;
		this.datasource = datasource;
		this.failMap = new HashMap<>();
		this.clienteValidator = new ClienteValidator(this.pedido.getCliente(), 
				datasource);
	}
	
	public PedidoValidator conDatosDeClienteObligatorios() {
		this.clienteValidator.conTodoObligatorio();
		return this;
	}
	
	public PedidoValidator conDatosDeClienteAdecuados() {
		this.clienteValidator.conDatosAdecuados();
		return this;
	}
	
	public PedidoValidator conCodigoDeClienteObligatorio() {
		this.failMap.put("Codigo de cliente requerido", 
				this.pedido.getCliente() == null || 
				this.pedido.getCliente().getCodigo() == null);
		return this;
	}
	
	public PedidoValidator conDNIClienteNoReutilizado() {
		this.clienteValidator.conDNINoReutilizado();
		return this;
	}
	
	public PedidoValidator conNumeroReservaNoReutilizado() {
		this.failMap.put("Número de reserva ya usado",
				!this.datasource.isNumeroReservaDisponible(
						this.pedido.getNumeroReserva())
				);
		return this;
	}
	
	public PedidoValidator conFechaSolicitudObligatoria() {
		this.failMap.put("Fecha de Solicitud requerida", 
				this.pedido.getFechaSolicitud() == null);
		return this;
	}
	
	public PedidoValidator conFechaVentaObligatoria() {
		this.failMap.put("Fecha de Venta requerida", 
				this.pedido.getFechaVenta() == null);
		return this;
	}
	
	public PedidoValidator conFechaDespachoObligatoria() {
		this.failMap.put("Fecha de despacho requerida", 
				this.pedido.getFechadespacho() == null);
		return this;
	}
	
	public PedidoValidator conDireccionYDitritoDespachoObligatoria() {
		this.failMap.put("Direccion y Distrito de despacho requerida", 
				this.pedido.getDireccionDespachoPedido() == null
				|| this.pedido.getDireccionDespachoPedido().isEmpty()
				|| this.pedido.getDistritoDespacho() == null
				|| this.pedido.getDistritoDespacho().getCodigo() == null
				|| this.pedido.getDistritoDespacho().getCodigo().isEmpty()
				);
		return this;
	}
	
	public PedidoValidator conTiendaDespachoYFechaRetiroCoherente() {
		this.failMap.put("Fecha de retiro requerida", 
				this.pedido.getTiendaDespacho() != null
				&& (this.pedido.getFechaRetiroTienda() == null)
		);
		
		this.failMap.put("Tienda de despacho requerida", 
				this.pedido.getFechaRetiroTienda() != null 
				&& (this.pedido.getTiendaDespacho() == null 
					|| this.pedido.getTiendaDespacho().getCodigo() == null
					|| this.pedido.getTiendaDespacho().getCodigo().isEmpty()
				));
		return this;
	}
	
	public PedidoValidator conCodigoDistritoExistente() {
		if( this.pedido.getDistritoDespacho() != null
				&& this.pedido.getDistritoDespacho().getCodigo() != null
				&& !this.pedido.getDistritoDespacho().getCodigo().isEmpty()) {
			this.failMap.put("Codigo de distrito " + 
					this.pedido.getDistritoDespacho().getCodigo() + " no existe", 
					!this.datasource.existeDistrito(
							this.pedido.getDistritoDespacho().getCodigo()));
		}
		return this;
	}
	
	public PedidoValidator conCodigoTiendaExistente() {
		if(this.pedido.getTiendaDespacho() != null 
					&& this.pedido.getTiendaDespacho().getCodigo() != null
					&& !this.pedido.getTiendaDespacho().getCodigo().isEmpty()) {
			this.failMap.put("Codigo de tienda " 
					+ this.pedido.getTiendaDespacho().getCodigo() 
					+ " no existe", 
					!this.datasource.existeTienda(
							this.pedido.getTiendaDespacho().getCodigo()));
		}
		return this;
	}
	
	public PedidoValidator conAlMenosUnDetalle() {
		System.out.println("Check detalles");
		this.failMap.put("Se requiere al menos un detalle",
				this.pedido.getDetalles() == null
				|| this.pedido.getDetalles().isEmpty());
		return this;
	}
	
	public PedidoValidator conDetallesAdecuados() {
		if(this.pedido.getDetalles()!=null) {
			for(DetallePedido detalle : this.pedido.getDetalles()) {
				DetallePedidoValidator detallePedidoValidator = new DetallePedidoValidator(detalle, this.datasource);
				detallePedidoValidator.conDetalleAdecuado();
				if(!detallePedidoValidator.valid()) {
					//Se asegura de no dejar pasar falla por reescritura
					failMap.putAll(detallePedidoValidator.getFailMap());
				}
			}
		}
		return this;
	}
	
	public boolean valid() {
		failMap.putAll(this.clienteValidator.getFailMap());
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
