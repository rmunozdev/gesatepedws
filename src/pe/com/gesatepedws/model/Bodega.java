package pe.com.gesatepedws.model;

public class Bodega {

	private String codigo;
	private String nombre;
	private String direccion;
	private Distrito distrito;
	
	public Bodega() {
	}
	
	public Bodega(String codigo) {
		this.codigo = codigo;
	}
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
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	@Override
	public int hashCode() {
		return this.codigo.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Bodega)) {
			return false;
		}
		
		Bodega bodega = (Bodega) obj;
		
		return this.codigo.equals(bodega.codigo);
		
	}
}
