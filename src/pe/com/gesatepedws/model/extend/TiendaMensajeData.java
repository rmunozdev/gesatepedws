package pe.com.gesatepedws.model.extend;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.com.gesatepedws.model.Tienda;

public class TiendaMensajeData {

	private String numeroVerificacion;
	private Date fechaDespacho;
	private Tienda tienda;
	private List<UnidadMensajeData> unidades;
	
	public TiendaMensajeData(
			String numeroVerificacion, 
			Date fechaDespacho, 
			Tienda tienda) {
		this.numeroVerificacion = numeroVerificacion;
		this.fechaDespacho = fechaDespacho;
		this.tienda = tienda;
		this.unidades = new ArrayList<>();
	}
	
	public void addMensaje(MensajeData mensaje) {
		UnidadMensajeData unidadMensajeData = null;
		for (UnidadMensajeData current : unidades) {
			if(mensaje.getUnidad().equals(current.getUnidad())
					&& mensaje.getRangoHorario().equals(current.getRangoHorario())) {
				unidadMensajeData = current;
			}
		}
		if(unidadMensajeData == null) {
			unidadMensajeData = new UnidadMensajeData(mensaje.getUnidad(), 
					mensaje.getRangoHorario());
			this.unidades.add(unidadMensajeData);
		}
		
		unidadMensajeData.addMensajeData(mensaje);
	}
	
	
	//GET & SET
	public String getNumeroVerificacion() {
		return numeroVerificacion;
	}
	public void setNumeroVerificacion(String numeroVerificacion) {
		this.numeroVerificacion = numeroVerificacion;
	}
	public Date getFechaDespacho() {
		return fechaDespacho;
	}
	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	public Tienda getTienda() {
		return tienda;
	}
	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}
	public List<UnidadMensajeData> getUnidades() {
		return unidades;
	}
	public void setUnidades(List<UnidadMensajeData> unidades) {
		this.unidades = unidades;
	}
	
	public String getFechaDespachoText() {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, new Locale("es","PE"));
		return dateFormat.format(this.fechaDespacho);
	}
}
