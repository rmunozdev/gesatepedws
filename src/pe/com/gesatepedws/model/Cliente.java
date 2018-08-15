package pe.com.gesatepedws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class Cliente {

	@JsonProperty("codigoCliente")
	private String codigo;
	
	@JsonProperty("nombreCliente")
	private String nombres;
	
	@JsonProperty("apellidoCliente")
	private String apellidos;
	
	@JsonProperty("numeroDNI")
	private String numeroDNI;
	
	@JsonProperty("telefonoCliente")
	private String telefono;
	
	@JsonProperty("emailCliente")
	private String email;
	
	@JsonProperty("direccionCliente")
	private String direccion;
	
	
	private Distrito distrito;
	
	@JsonProperty("codigoDistritoCliente")
	public void setCodigoDistrito(String codigo) {
		this.distrito = new Distrito();
		this.distrito.setCodigo(codigo);
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getNumeroDNI() {
		return numeroDNI;
	}
	public void setNumeroDNI(String numeroDNI) {
		this.numeroDNI = numeroDNI;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
}
