package pe.com.gesatepedws.model.extend;

import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.Pedido;

public class DetalleRutaParam {

	private String codigoRuta;
	private DetalleRuta detalle;
	private Integer codigoRespuesta;
	private String mensajeRespuesta;
	
	public static class Builder {
		private String codigoRuta;
		private DetalleRuta detalle;
		
		public Builder(String codigoRuta) {
			this.codigoRuta = codigoRuta;
		}
		
		public Builder withPedido(String codigoPedido) {
			if(this.detalle == null) {
				this.detalle = new DetalleRuta();
			}
			Pedido pedido = new Pedido();
			pedido.setCodigo(codigoPedido);
			this.detalle.setPedido(pedido);
			return this;
		}
		
		public Builder withDetalle(DetalleRuta detalle) {
			this.detalle = detalle;
			return this;
		}
		
		public DetalleRutaParam build() {
			return new DetalleRutaParam(this);
		}
	}
	
	public DetalleRutaParam() {
	}
	
	public DetalleRutaParam(Builder builder) {
		this.codigoRuta = builder.codigoRuta;
		this.detalle = builder.detalle;
	}

	public String getCodigoRuta() {
		return codigoRuta;
	}
	public void setCodigoRuta(String codigoRuta) {
		this.codigoRuta = codigoRuta;
	}
	public DetalleRuta getDetalle() {
		return detalle;
	}
	public void setDetalle(DetalleRuta detalle) {
		this.detalle = detalle;
	}
	public Integer getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(Integer codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	
}
