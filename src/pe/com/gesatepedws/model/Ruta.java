package pe.com.gesatepedws.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ruta {

	private String codigo;
	private Date fechaGeneracion;
	private Date fechaDespacho;
	private Bodega bodega;
	private UnidadChofer unidadChofer;
	private List<DetalleRuta> detalles;
	
	public static class Builder {
		private final Date fechaDespacho;
		private Date fechaGeneracion;
		private Bodega bodega;
		private UnidadChofer unidadChofer;
		private List<DetalleRuta> detalles;
		
		public Builder(Date fechaDespacho) {
			this.fechaDespacho = fechaDespacho;
			this.detalles = new ArrayList<>();
		}
		
		public Builder addDetalle(DetalleRuta detalle) {
			detalles.add(detalle);
			return this;
		}
		
		public Builder withCodigoUnidadChofer(String codigo) {
			if(this.unidadChofer == null) {
				this.unidadChofer = new UnidadChofer();
			}
			this.unidadChofer.setCodigo(codigo);
			return this;
		}
		
		public Builder forChofer(String brevete) {
			this.unidadChofer = new UnidadChofer();
			Chofer chofer = new Chofer();
			chofer.setNumeroBrevete(brevete);
			this.unidadChofer.setChofer(chofer);
			return this;
		}
		
		public Builder forUnidad(Unidad unidad) {
			this.unidadChofer =  new UnidadChofer();
			this.unidadChofer.setUnidad(unidad);
			return this;
		}
		
		public Builder forBodega(Bodega bodega) {
			this.bodega = bodega;
			return this;
		}
		
		public Ruta build() {
			return new Ruta(this);
		}
	}
	
	public Ruta() {
	}
	
	public Ruta(Builder builder) {
		this.bodega = builder.bodega;
		this.detalles = builder.detalles;
		this.unidadChofer = builder.unidadChofer;
		this.fechaDespacho = builder.fechaDespacho;
		this.fechaGeneracion = builder.fechaGeneracion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public Bodega getBodega() {
		return bodega;
	}
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}
	public UnidadChofer getUnidadChofer() {
		return unidadChofer;
	}
	public void setUnidadChofer(UnidadChofer unidadChofer) {
		this.unidadChofer = unidadChofer;
	}
	public List<DetalleRuta> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleRuta> detalles) {
		this.detalles = detalles;
	}
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public Date getFechaDespacho() {
		return fechaDespacho;
	}
	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
}
