package pe.com.gesatepedws.model;

public class TipoUnidad {

	private String codigo;
	private String nombre;
	private double pesoMaximoCarga;
	private double volumenMaxCarga;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPesoMaximoCarga() {
		return pesoMaximoCarga;
	}
	public void setPesoMaximoCarga(double pesoMaximoCarga) {
		this.pesoMaximoCarga = pesoMaximoCarga;
	}
	public double getVolumenMaxCarga() {
		return volumenMaxCarga;
	}
	public void setVolumenMaxCarga(double volumenMaxCarga) {
		this.volumenMaxCarga = volumenMaxCarga;
	}
	
	
	
}
