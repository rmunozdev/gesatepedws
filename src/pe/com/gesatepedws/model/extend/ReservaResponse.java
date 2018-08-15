package pe.com.gesatepedws.model.extend;

import java.util.ArrayList;
import java.util.List;

public class ReservaResponse {

	private Integer codigo;
	private List<String> mensajes;
	
	public ReservaResponse() {
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
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}
	
	
}
