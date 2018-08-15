package pe.com.gesatepedws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class DetallePedido {

	private Pedido pedido;
	
	private Producto producto;
	
	@JsonProperty("cantidadProducto")
	private Integer cantidadProducto;
	private Integer cantidadProductoDefectuoso;
	private String observacionProductoDefectuoso;
	private Integer cantidadProductoNoUbicable;
	private Bodega bodega;
	
	@JsonProperty("codigoProducto")
	public void setCodigoProducto(String codigo) {
		this.producto = new Producto();
		this.producto.setCodigo(codigo);
	}
	
	@JsonProperty("codigoBodega")
	public void setCodigoBodega(String codigo) {
		this.bodega = new Bodega();
		this.bodega.setCodigo(codigo);
	}
	
	public static class Builder {
		private Pedido pedido;
		private Bodega bodega;
		
		public Builder(String codigoPedido) {
			this.pedido = new Pedido();
			this.pedido.setCodigo(codigoPedido);
		}
		
		public Builder forBodega(String codigoBodega) {
			this.bodega = new Bodega();
			this.bodega.setCodigo(codigoBodega);
			return this;
		}
		
		public DetallePedido build() {
			return new DetallePedido(this);
		}
	}
	
	public DetallePedido() {
	}
	
	
	public DetallePedido(Builder builder) {
		this.pedido = builder.pedido;
		this.bodega = builder.bodega;
	}



	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(Integer cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	public Integer getCantidadProductoDefectuoso() {
		return cantidadProductoDefectuoso;
	}
	public void setCantidadProductoDefectuoso(Integer cantidadProductoDefectuoso) {
		this.cantidadProductoDefectuoso = cantidadProductoDefectuoso;
	}
	public String getObservacionProductoDefectuoso() {
		return observacionProductoDefectuoso;
	}
	public void setObservacionProductoDefectuoso(String observacionProductoDefectuoso) {
		this.observacionProductoDefectuoso = observacionProductoDefectuoso;
	}
	public Integer getCantidadProductoNoUbicable() {
		return cantidadProductoNoUbicable;
	}
	public void setCantidadProductoNoUbicable(Integer cantidadProductoNoUbicable) {
		this.cantidadProductoNoUbicable = cantidadProductoNoUbicable;
	}
	public Bodega getBodega() {
		return bodega;
	}
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
	
}
