package pe.com.gesatepedws.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Chofer {

	private String numeroBrevete;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String email;
	private String flagActividad;
	private Proveedor proveedor;
	
	public String getNumeroBrevete() {
		return numeroBrevete;
	}
	public void setNumeroBrevete(String numeroBrevete) {
		this.numeroBrevete = numeroBrevete;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFlagActividad() {
		return flagActividad;
	}
	public void setFlagActividad(String flagActividad) {
		this.flagActividad = flagActividad;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	
}
