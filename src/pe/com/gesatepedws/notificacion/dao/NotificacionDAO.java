package pe.com.gesatepedws.notificacion.dao;

import java.util.Date;
import java.util.List;

import pe.com.gesatepedws.model.extend.MensajeData;

public interface NotificacionDAO {

	public MensajeData obtenerMensajeData(String codigoHojaRuta, String codigoPedido);
	public List<MensajeData> obtenerMensajesParaTodasLasRutas(Date fecha);
	
}
