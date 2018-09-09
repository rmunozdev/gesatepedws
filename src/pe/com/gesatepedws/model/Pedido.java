package pe.com.gesatepedws.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class Pedido {

	@JsonProperty("codigoPedido")
	private String codigo;
	
	@JsonProperty("cliente")
	private Cliente cliente;
	
	@JsonProperty("numeroReserva")
	private Integer numeroReserva;
	
	@JsonProperty("numeroVerificacion")
	private Integer numeroVerificacion;
	
	@JsonProperty("fechaSolicitud")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="GMT-5:00")
	private Date fechaSolicitud;
	
	@JsonProperty("fechaVentaPedido")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="GMT-5:00")
	private Date fechaVenta;
	
	@JsonProperty("fechaDespacho")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-5:00")
	private Date fechadespacho;
	
	
	private Date fechaRetiroTienda;
	
	
	private Date fechaRecojoTienda;
	private Date fechaReprogramacion;
	private Date fechaCancelacion;
	private Date fechaDevolucionPed;
	private String codigoMotivo;
	
	@JsonProperty("direccionDespacho")
	private String direccionDespachoPedido;
	
	private Distrito distritoDespacho;
	
	@JsonProperty("codigoDistritoDespacho")
	public void setCodigoDistrito(String codigo) {
		this.distritoDespacho = new Distrito();
		this.distritoDespacho.setCodigo(codigo);
	}
	
	@JsonProperty("tienda")
	private Tienda tiendaDespacho;
	
	private Tienda tiendaDevolucion;
	
	@JsonProperty("listaDetallePedido")
	private List<DetallePedido> detalles;
	
	
	private DetallePedido detalle;
	
	public static class Builder {
		private final String codigo;
		private Cliente cliente;
		private Tienda tiendaDespacho;
		private Date fechaDevolucionPed;
		private Tienda tiendaDevolucion;
		private Date fechaRetiroTienda;
		
		public Builder(String codigo) {
			this.codigo = codigo;
		}
		
		public Builder withCliente(String codigo) {
			this.cliente = new Cliente();
			this.cliente.setCodigo(codigo);
			return this;
		}
		
		public Builder forDespacho(String codigoTienda) {
			this.tiendaDespacho = new Tienda();
			this.tiendaDespacho.setCodigo(codigoTienda);
			return this;
		}
		
		public Builder withFechaDevolucion(Date fecha) {
			this.fechaDevolucionPed = fecha;
			return this;
		}
		
		public Builder withFechaRetiro(Date fecha) {
			this.fechaRetiroTienda = fecha;
			return this;
		}
		
		public Builder forDevolucionEn(String codigoTienda) {
			this.tiendaDevolucion = new Tienda();
			this.tiendaDevolucion.setCodigo(codigoTienda);
			return this;
		}
		
		public Pedido build() {
			return new Pedido(this);
		}
		
	}
	
	
	public Pedido() {
	}
	
	public Pedido(Builder builder) {
		this.codigo = builder.codigo;
		this.cliente = builder.cliente;
		this.tiendaDespacho = builder.tiendaDespacho;
		this.tiendaDevolucion = builder.tiendaDevolucion;
		this.fechaDevolucionPed = builder.fechaDevolucionPed;
		this.fechaRetiroTienda = builder.fechaRetiroTienda;
	}
	
	public Pedido(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Tienda getTiendaDespacho() {
		return tiendaDespacho;
	}

	public void setTiendaDespacho(Tienda tiendaDespacho) {
		this.tiendaDespacho = tiendaDespacho;
	}

	public Tienda getTiendaDevolucion() {
		return tiendaDevolucion;
	}

	public void setTiendaDevolucion(Tienda tiendaDevolucion) {
		this.tiendaDevolucion = tiendaDevolucion;
	}

	public Integer getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(Integer numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

	public Integer getNumeroVerificacion() {
		return numeroVerificacion;
	}

	public void setNumeroVerificacion(Integer numeroVerificacion) {
		this.numeroVerificacion = numeroVerificacion;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Date getFechadespacho() {
		return fechadespacho;
	}

	public void setFechadespacho(Date fechadespacho) {
		this.fechadespacho = fechadespacho;
	}

	public Date getFechaRetiroTienda() {
		return fechaRetiroTienda;
	}

	public void setFechaRetiroTienda(Date fechaRetiroTienda) {
		this.fechaRetiroTienda = fechaRetiroTienda;
	}

	public Date getFechaRecojoTienda() {
		return fechaRecojoTienda;
	}

	public void setFechaRecojoTienda(Date fechaRecojoTienda) {
		this.fechaRecojoTienda = fechaRecojoTienda;
	}

	public Date getFechaReprogramacion() {
		return fechaReprogramacion;
	}

	public void setFechaReprogramacion(Date fechaReprogramacion) {
		this.fechaReprogramacion = fechaReprogramacion;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Date getFechaDevolucionPed() {
		return fechaDevolucionPed;
	}

	public void setFechaDevolucionPed(Date fechaDevolucionPed) {
		this.fechaDevolucionPed = fechaDevolucionPed;
	}

	public String getCodigoMotivo() {
		return codigoMotivo;
	}

	public void setCodigoMotivo(String codigoMotivo) {
		this.codigoMotivo = codigoMotivo;
	}
	
	@Override
	public String toString() {
		return this.codigo;
	}
	
	@Override
	public int hashCode() {
		return this.codigo.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Pedido)) {
			return false;
		}
		
		Pedido pedido = (Pedido) obj;
		
		return this.codigo.equals(pedido.codigo);
	}

	public DetallePedido getDetalle() {
		return detalle;
	}

	public void setDetalle(DetallePedido detalle) {
		this.detalle = detalle;
	}

	public Distrito getDistritoDespacho() {
		return distritoDespacho;
	}

	public void setDistritoDespacho(Distrito distritoDespacho) {
		this.distritoDespacho = distritoDespacho;
	}

	public String getDireccionDespachoPedido() {
		return direccionDespachoPedido;
	}

	public void setDireccionDespachoPedido(String direccionDespachoPedido) {
		this.direccionDespachoPedido = direccionDespachoPedido;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}
}
