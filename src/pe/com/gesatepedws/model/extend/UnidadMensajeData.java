package pe.com.gesatepedws.model.extend;

import java.util.ArrayList;
import java.util.List;

import pe.com.gesatepedws.model.Unidad;

public class UnidadMensajeData {

	private Unidad unidad;
	private String rangoHorario;
	private List<MensajeData> mensajes;

	public UnidadMensajeData(Unidad unidad, String rangoHorario) {
		this.unidad = unidad;
		this.rangoHorario = rangoHorario;
		this.mensajes = new ArrayList<>();
	}
	
	public void addMensajeData(MensajeData mensaje) {
		this.mensajes.add(mensaje);
	}
	
	public List<MensajeData> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeData> mensajes) {
		this.mensajes = mensajes;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public String getRangoHorario() {
		return rangoHorario;
	}

	public void setRangoHorario(String rangoHorario) {
		this.rangoHorario = rangoHorario;
	}
	
	
}
