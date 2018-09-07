package pe.com.gesatepedws.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class DetalleRuta {

	private Ruta hojaRuta;
	
	@JsonProperty("pedido")
	private Pedido pedido;
	private String ordenDespachoPedido;
	private String tiempoPromedioDespacho;
	private Date fechaEstimPartida;
	private String tiempoEstimadoLlegada;
	private int distanciaEstimada;
	private Date fechaEstimLlegada;
	
	@JsonProperty("fechaPactadaDespacho")
	private Date fechaPactadaDespacho;
	
	@JsonProperty("fechaNoCumplimientoDespacho")
	private Date fechaNoCumplimientoDespacho;
	
	@JsonProperty("latGPSDespachoPedido")
	private double latGPSDespachoPedido;
	
	@JsonProperty("longGPSDespachoPedido")
	private double longGPSDespachoPedido;
	
	@JsonProperty("fotoDespachoPedido")
	private String fotoDespachoPedido;
	
	private VentanaHoraria ventana;
	
	@JsonProperty("motivo")
	private MotivoPedido motivo;
	
	private String destinatario;
	private String domicilio;
	private String horario;
	private String estado;
	
	@JsonProperty("codigoHojaRuta")
	public void setCodigoHojaRuta(String codigo) {
		this.hojaRuta = new Ruta();
		this.hojaRuta.setCodigo(codigo);
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public String getOrdenDespachoPedido() {
		return ordenDespachoPedido;
	}
	public void setOrdenDespachoPedido(String ordenDespachoPedido) {
		this.ordenDespachoPedido = ordenDespachoPedido;
	}
	public String getTiempoPromedioDespacho() {
		return tiempoPromedioDespacho;
	}
	public void setTiempoPromedioDespacho(String tiempoPromedioDespacho) {
		this.tiempoPromedioDespacho = tiempoPromedioDespacho;
	}
	public Date getFechaEstimPartida() {
		return fechaEstimPartida;
	}
	public void setFechaEstimPartida(Date fechaEstimPartida) {
		this.fechaEstimPartida = fechaEstimPartida;
	}
	public String getTiempoEstimadoLlegada() {
		return tiempoEstimadoLlegada;
	}
	public void setTiempoEstimadoLlegada(String tiempoEstimadoLlegada) {
		this.tiempoEstimadoLlegada = tiempoEstimadoLlegada;
	}
	public int getDistanciaEstimada() {
		return distanciaEstimada;
	}
	public void setDistanciaEstimada(int distanciaEstimada) {
		this.distanciaEstimada = distanciaEstimada;
	}
	public Date getFechaEstimLlegada() {
		return fechaEstimLlegada;
	}
	public void setFechaEstimLlegada(Date fechaEstimLlegada) {
		this.fechaEstimLlegada = fechaEstimLlegada;
	}
	public Date getFechaPactadaDespacho() {
		return fechaPactadaDespacho;
	}
	public void setFechaPactadaDespacho(Date fechaPactadaDespacho) {
		this.fechaPactadaDespacho = fechaPactadaDespacho;
	}
	public Date getFechaNoCumplimientoDespacho() {
		return fechaNoCumplimientoDespacho;
	}
	public void setFechaNoCumplimientoDespacho(Date fechaNoCumplimientoDespacho) {
		this.fechaNoCumplimientoDespacho = fechaNoCumplimientoDespacho;
	}
	public double getLatGPSDespachoPedido() {
		return latGPSDespachoPedido;
	}
	public void setLatGPSDespachoPedido(double latGPSDespachoPedido) {
		this.latGPSDespachoPedido = latGPSDespachoPedido;
	}
	public double getLongGPSDespachoPedido() {
		return longGPSDespachoPedido;
	}
	public void setLongGPSDespachoPedido(double longGPSDespachoPedido) {
		this.longGPSDespachoPedido = longGPSDespachoPedido;
	}
	public String getFotoDespachoPedido() {
		return fotoDespachoPedido;
	}
	public void setFotoDespachoPedido(String fotoDespachoPedido) {
		this.fotoDespachoPedido = fotoDespachoPedido;
	}
	public VentanaHoraria getVentana() {
		return ventana;
	}
	public void setVentana(VentanaHoraria ventana) {
		this.ventana = ventana;
	}
	public MotivoPedido getMotivo() {
		return motivo;
	}
	public void setMotivo(MotivoPedido motivo) {
		this.motivo = motivo;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Ruta getHojaRuta() {
		return hojaRuta;
	}
	public void setHojaRuta(Ruta hojaRuta) {
		this.hojaRuta = hojaRuta;
	}
	
}
