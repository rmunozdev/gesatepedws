package pe.com.gesatepedws.notificacion.service;

import java.util.List;

import pe.com.gesatepedws.model.Kardex;

public interface NotificacionService {

	public boolean notificarAlertaStockMinimo(List<Kardex> kardex);
	
	/**
	 * Genera la notificación de ventanas horarias para todos los pedidos
	 * a la fecha establecida
	 * @return
	 */
	public boolean notificarTodo();
	public boolean notificarVentanaHoraria(String codigoHojaRuta, String codigoPedido);
}
