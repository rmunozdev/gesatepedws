package pe.com.gesatepedws.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MotivoPedido {

	@JsonProperty("codigo")
	private String codigo;
	private String descripcion;
	private String categoria;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
