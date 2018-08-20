package pe.com.gesatepedws.validacion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Distrito;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;

public class PedidoValidator {

	private static final int DURACION_24_HRS = 24 * 60 * 60 * 1000;
	private static final int DURACION_48_HRS = 48 * 60 * 60 * 1000;
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
	
	public PedidoValidator conCodigoPedidoOobligatorio() {
		this.failMap.put("Debe ingresar el codigo de pedido", 
				this.pedido.getCodigo() == null
				|| this.pedido.getCodigo().isEmpty());
		return this;
	}
	
	public PedidoValidator conCodigoPedidoNoReutilizado() {
		this.failMap.put("El pedido " + this.pedido.getCodigo() + " ya ha sido registrado previamente", 
				!this.datasource.isCodigoPedidoDisponible(this.pedido.getCodigo()));
		return this;
	}
	
	public PedidoValidator conDatosDeClienteObligatorios() {
		this.clienteValidator.conTodoObligatorio();
		return this;
	}
	
	public PedidoValidator conNumeroDNIClienteAdecuado() {
		this.clienteValidator.conNumeroDNIAdecuado();
		return this;
	}
	
	public PedidoValidator conNombresYApellidosClienteAdecuados() {
		this.clienteValidator.conNombreAdecuado();
		this.clienteValidator.conApellidoAdecuado();
		return this;
	}
	
	public PedidoValidator conEmailClienteAdecuado() {
		this.clienteValidator.conEmailAdecuado();
		return this;
	}
	
	public PedidoValidator sinConflictoEmailCliente() {
		this.clienteValidator.sinConflictoEmail();
		return this;
	}
	
	public PedidoValidator sinConflictoTelefonoCliente() {
		this.clienteValidator.sinConflictoTelefono();
		return this;
	}
	
	public PedidoValidator conTelefonoClienteAdecuado() {
		this.clienteValidator.conTelefonoAdecuado();
		return this;
	}
	
	public PedidoValidator conDireccionDeClienteAdecuada() {
		this.clienteValidator.conDireccionAdecuada();
		this.clienteValidator.conCodigoDistritoExistente();
		this.clienteValidator.conDireccionMapeable();
		return this;
	}
	

	
	public PedidoValidator conCodigoDeClienteObligatorio() {
		this.failMap.put("Debe ingresar el codigo de cliente", 
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
		this.failMap.put("Debe ingresar la fecha de Solicitud", 
				this.pedido.getFechaSolicitud() == null);
		return this;
	}
	
	public PedidoValidator conFechaVentaObligatoria() {
		this.failMap.put("Debe ingresar la fecha de Venta", 
				this.pedido.getFechaVenta() == null);
		return this;
	}
	
	public PedidoValidator conFechaDespachoObligatoria() {
		this.failMap.put("Debe ingresar la fecha de despacho", 
				this.pedido.getFechadespacho() == null);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public PedidoValidator conFechaDespachoAdecuada() {
		if(this.pedido.getFechadespacho() != null) {
			if(this.pedido.getFechaSolicitud() != null) {
				
				int hora = this.pedido.getFechaSolicitud().getHours();
				int minutos = this.pedido.getFechaSolicitud().getMinutes();
				
				if((hora * 60) + minutos <= 16 * 60) {
					this.failMap.put("Fecha de despacho debe ser minimo 24 horas después de la fecha de solicitud del pedido", 
							(this.pedido.getFechadespacho().getTime() 
									- this.pedido.getFechaSolicitud().getTime()) 
							< DURACION_24_HRS );
				} else {
					this.failMap.put("Fecha de despacho debe ser minimo 48 horas después de la fecha de solicitud del pedido", 
							(this.pedido.getFechadespacho().getTime() 
									- this.pedido.getFechaSolicitud().getTime()) 
							< DURACION_48_HRS );
				}
			} 
			
			if(this.pedido.getFechaVenta() != null) {
				this.failMap.put("Fecha de despacho de pedido debe ser mayor a la fecha de venta del pedido", 
						(this.pedido.getFechadespacho().before(this.pedido.getFechaVenta())));
			}
			
			this.failMap.put("Fecha de despacho debe ser posterior a la fecha actual", 
					this.pedido.getFechadespacho().before(new Date()));
		}
		return this;
	}
	
	public PedidoValidator conTiendaDespachoYFechaRetiroCoherente() {
		this.failMap.put("Debe ingresar la fecha de retiro", 
				this.pedido.getTiendaDespacho() != null
				&& (this.pedido.getFechaRetiroTienda() == null)
		);
		
		this.failMap.put("Debe ingresar la tienda de despacho", 
				this.pedido.getFechaRetiroTienda() != null 
				&& (this.pedido.getTiendaDespacho() == null 
					|| this.pedido.getTiendaDespacho().getCodigo() == null
					|| this.pedido.getTiendaDespacho().getCodigo().isEmpty()
				));
		return this;
	}
	
	public PedidoValidator conDireccionYDistritoDespachoAdecuados() {
		//Se ignora validacion si es retiro en tiend
		if(this.pedido.getFechaRetiroTienda()!=null) {
			return this;
		}
		
		if(this.pedido.getDireccionDespachoPedido() != null) {
			this.failMap.put("Direccion de despacho mínimo un caracter", 
					this.pedido.getDireccionDespachoPedido().isEmpty());
			
			this.failMap.put("Debe ingresar el distrito de despacho", 
					this.pedido.getDistritoDespacho() == null 
					|| this.pedido.getDistritoDespacho().getCodigo() == null
					|| this.pedido.getDistritoDespacho().getCodigo().isEmpty());
			
			//Si todo es valido hasta ahora, usar servicio google para verificar direccion
			if(this.valid()) {
				//Se obtiene distrito de datasource
				Distrito distrito = this.datasource.obtenerDistrito(
						this.pedido.getDistritoDespacho().getCodigo());
				
				boolean mapeoValido = GoogleMapsValidatorUtils.
						validarDireccionCompleta(
								this.pedido.getDireccionDespachoPedido() 
								+ " " 
								+ distrito.getNombre());
				
				this.failMap.put("La dirección " 
				+ this.pedido.getDireccionDespachoPedido() 
				+ " en el distrito " 
				+ this.pedido.getDistritoDespacho().getCodigo() 
				+ " no pudo ser encontrada en el mapa", 
				!mapeoValido);
			}
		}
		
		this.failMap.put("Debe ingresar la direccion de despacho", 
				this.pedido.getDistritoDespacho() != null 
				&& (this.pedido.getDireccionDespachoPedido() == null
					|| this.pedido.getDireccionDespachoPedido().isEmpty()
						));
		return this;
	}
	
	public PedidoValidator conCodigoDistritoExistente() {
		if( this.pedido.getDistritoDespacho() != null
				&& this.pedido.getDistritoDespacho().getCodigo() != null
				&& !this.pedido.getDistritoDespacho().getCodigo().isEmpty()) {
			this.failMap.put("El distrito de despacho del pedido " + 
					this.pedido.getDistritoDespacho().getCodigo() + " no se encuentra registrado", 
					!this.datasource.existeDistrito(
							this.pedido.getDistritoDespacho().getCodigo()));
		}
		return this;
	}
	
	public PedidoValidator conCodigoTiendaExistente() {
		if(this.pedido.getTiendaDespacho() != null 
					&& this.pedido.getTiendaDespacho().getCodigo() != null
					&& !this.pedido.getTiendaDespacho().getCodigo().isEmpty()) {
			this.failMap.put("La tienda " 
					+ this.pedido.getTiendaDespacho().getCodigo() 
					+ " no se encuentra registrada.", 
					!this.datasource.existeTienda(
							this.pedido.getTiendaDespacho().getCodigo()));
		}
		return this;
	}
	
	public PedidoValidator conAlMenosUnDetalle() {
		System.out.println("Check detalles");
		this.failMap.put("Debe ingresar al menos un detalle",
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
