package pe.com.gesatepedws.model.extend;

import java.util.Date;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.Tienda;
import pe.com.gesatepedws.model.Unidad;

public class MensajeData {

	private String codigoHojaRuta;
	private String codigoPedido;
	
	private String destinatario;
	private String domicilio;
	private String numeroReserva;
	private String numeroVerificacion;
	private Date fechaDespacho;
	private String rangoHorario;
	private String numero;
	
	private Tienda tiendaDespacho;
	private Unidad unidad;
	private Cliente cliente;
	
	
	public String getNumeroReserva() {
		return numeroReserva;
	}
	public void setNumeroReserva(String numeroReserva) {
		this.numeroReserva = numeroReserva;
	}
	public String getNumeroVerificacion() {
		return numeroVerificacion;
	}
	public void setNumeroVerificacion(String numeroVerificacion) {
		this.numeroVerificacion = numeroVerificacion;
	}
	public Date getFechaDespacho() {
		return fechaDespacho;
	}
	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	public String getRangoHorario() {
		return rangoHorario;
	}
	public void setRangoHorario(String rangoHorario) {
		this.rangoHorario = rangoHorario;
	}
	public String getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getCodigoHojaRuta() {
		return codigoHojaRuta;
	}
	public void setCodigoHojaRuta(String codigoHojaRuta) {
		this.codigoHojaRuta = codigoHojaRuta;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Tienda getTiendaDespacho() {
		return tiendaDespacho;
	}
	public void setTiendaDespacho(Tienda tiendaDespacho) {
		this.tiendaDespacho = tiendaDespacho;
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
