package pe.com.gesatepedws.despacho.dao;

import java.util.Date;
import java.util.List;

import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;

public interface DespachoDAO {

	public Ruta getRuta(String brevete, Date despacho);
	public List<DetallePedido> getDetalle(String codigoPedido, String codigoBodega);
	public String getEstado(String ruta);
	public List<MotivoPedido> getMotivos(String categoria);
	public List<Chofer> getChoferes();
	public boolean registrarAtencion(String codigoRuta, DetalleRuta detalle);
	public boolean registrarIncumplimiento(String codigoRuta, DetalleRuta detalle);
	public String getImagen(String codigoRuta, String codigoPedido);
	public Integer getNumeroVerificacion(String codigoPedido);
}
