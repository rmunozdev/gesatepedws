package pe.com.gesatepedws.despacho.service;

import java.util.List;

import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;

public interface DespachoService {

	public Ruta getRuta(String brevete);
	public List<DetallePedido> getDetalle(String codigoPedido, String codigoBodega);
	public String getEstado(String ruta);
	public List<MotivoPedido> getMotivos();
	public List<Chofer> getChoferes();
	public boolean registrarAtencion(String codigoRuta, DetalleRuta detalle);
	public boolean registrarIncumplimiento(String codigoRuta, DetalleRuta detalle);
	public String getImagen(String codigoRuta, String codigoPedido);
}
