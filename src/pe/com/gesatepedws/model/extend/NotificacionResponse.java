package pe.com.gesatepedws.model.extend;

import java.util.ArrayList;
import java.util.List;

public class NotificacionResponse {

	private Integer codigo;
	private List<String> mensajes;
	
	public NotificacionResponse() {
		this.mensajes = new ArrayList<>();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public List<String> getMensajes() {
		return mensajes;
	}
	
	public void addMensaje(String mensaje) {
		this.mensajes.add(mensaje);
	}
	
}
