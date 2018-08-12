package pe.com.gesatepedws.model.extend;

public class EstadoPedidoParam {

	private String codigoRuta;
	private Integer codigoRespuesta;
	private String mensajeRespuesta;
	
	public EstadoPedidoParam(String codigoRuta) {
		this.codigoRuta = codigoRuta;
	}
	
	public String getCodigoRuta() {
		return codigoRuta;
	}
	public void setCodigoRuta(String codigoRuta) {
		this.codigoRuta = codigoRuta;
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
