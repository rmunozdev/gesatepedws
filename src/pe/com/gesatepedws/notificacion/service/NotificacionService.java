package pe.com.gesatepedws.notificacion.service;

import java.util.List;

import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.model.extend.NotificacionResponse;

public interface NotificacionService {

	public boolean notificarAlertaStockMinimo(List<Kardex> kardex);
	
	/**
	 * Genera la notificación de ventanas horarias para todos los pedidos
	 * a la fecha establecida
	 * @return
	 */
	public List<NotificacionResponse> notificarTodo();
	public NotificacionResponse notificarVentanaHoraria(String codigoHojaRuta, String codigoPedido);
}
