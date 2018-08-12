package pe.com.gesatepedws.model;

public class Tienda {

	private String codigo;
	private String nombre;
	private String direccion;
	private ControllerTienda controllerTienda;
	private Distrito distrito;
	
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public ControllerTienda getControllerTienda() {
		return controllerTienda;
	}
	public void setControllerTienda(ControllerTienda controllerTienda) {
		this.controllerTienda = controllerTienda;
	}
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
}
