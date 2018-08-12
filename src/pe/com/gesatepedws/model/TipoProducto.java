package pe.com.gesatepedws.model;

public class TipoProducto {

	private String codigo;
	private String nombre;
	private FamiliaProducto familiaProducto;
	
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
	public FamiliaProducto getFamiliaProducto() {
		return familiaProducto;
	}
	public void setFamiliaProducto(FamiliaProducto familiaProducto) {
		this.familiaProducto = familiaProducto;
	}
}
