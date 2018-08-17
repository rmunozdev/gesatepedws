package pe.com.gesatepedws.model;

import java.util.Date;

public class Kardex {
	
	private Bodega bodega;
	private Producto producto;
	private Integer stockMinimo;
	private Integer stockActual;
	private Date fechaActualizacionRegistro;
	
	public Kardex() {
	}
	
	public Kardex(String codigoProducto, String codigoBodega) {
		this.bodega = new Bodega();
		this.producto = new Producto();
		
		this.bodega.setCodigo(codigoBodega);
		this.producto.setCodigo(codigoProducto);
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(Integer stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public Integer getStockActual() {
		return stockActual;
	}
	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
	}
	public Date getFechaActualizacionRegistro() {
		return fechaActualizacionRegistro;
	}
	public void setFechaActualizacionRegistro(Date fechaActualizacionRegistro) {
		this.fechaActualizacionRegistro = fechaActualizacionRegistro;
	}
	public Bodega getBodega() {
		return bodega;
	}
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

}
