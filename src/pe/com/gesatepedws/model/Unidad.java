package pe.com.gesatepedws.model;

public class Unidad {

	private String numeroPlaca;
	private String modelo;
	private String marca;
	private String numeroSoat;
	private Integer flagActividad;
	private TipoUnidad tipoUnidad;
	private Proveedor proovedor;
	
	public static class Builder {
		private String numeroPlaca;
		private TipoUnidad tipoUnidad;
		private double pesoCarga;
		private double volumenCarga;
		
		public Builder() {
			
		}
		
		public Builder(String placa) {
			this.numeroPlaca = placa;
		}
		
		public Builder withPlaca(String placa) {
			this.numeroPlaca = placa;
			return this;
		}
		
		public Builder withPesoVolumenCarga(double peso, double volumen) {
			this.pesoCarga = peso;
			this.volumenCarga = volumen;
			return this;
		}
		
		public Unidad build() {
			if(this.numeroPlaca == null) {
				throw new IllegalStateException("Require numero placa");
			}
			this.tipoUnidad = new TipoUnidad();
			this.tipoUnidad.setPesoMaximoCarga(pesoCarga);
			this.tipoUnidad.setVolumenMaxCarga(volumenCarga);
			return new Unidad(this);
		}
	}
	
	public Unidad() {
	}
	
	public Unidad(Builder builder) {
		this.numeroPlaca = builder.numeroPlaca;
		this.tipoUnidad = builder.tipoUnidad;
	}
	
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getNumeroSoat() {
		return numeroSoat;
	}
	public void setNumeroSoat(String numeroSoat) {
		this.numeroSoat = numeroSoat;
	}
	public Integer getFlagActividad() {
		return flagActividad;
	}
	public void setFlagActividad(Integer flagActividad) {
		this.flagActividad = flagActividad;
	}
	public TipoUnidad getTipoUnidad() {
		return tipoUnidad;
	}
	public void setTipoUnidad(TipoUnidad tipoUnidad) {
		this.tipoUnidad = tipoUnidad;
	}
	public Proveedor getProovedor() {
		return proovedor;
	}
	public void setProovedor(Proveedor proovedor) {
		this.proovedor = proovedor;
	}
	
	@Override
	public int hashCode() {
		return this.numeroPlaca.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Unidad)) {
			return false;
		}
		
		Unidad unidad = (Unidad) obj;
		return this.numeroPlaca.equals(unidad.numeroPlaca);
	}
	
	@Override
	public String toString() {
		return this.numeroPlaca;
	}
}
