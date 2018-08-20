package pe.com.gesatepedws.notificacion.dao;

import pe.com.gesatepedws.model.extend.MensajeData;

public interface NotificacionDAO {

	public MensajeData obtenerMensajeData(String codigoHojaRuta, String codigoPedido);
	
	
}
