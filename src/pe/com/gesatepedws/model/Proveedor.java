package pe.com.gesatepedws.model;

public class Proveedor {

	private String codigo;
	private String razonSocial;
	private String numeroRUC;
	private String telefono;
	private String email;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNumeroRUC() {
		return numeroRUC;
	}
	public void setNumeroRUC(String numeroRUC) {
		this.numeroRUC = numeroRUC;
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
}
