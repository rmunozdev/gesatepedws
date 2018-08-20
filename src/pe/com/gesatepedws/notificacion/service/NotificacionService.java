package pe.com.gesatepedws.notificacion.service;

import java.util.List;

import pe.com.gesatepedws.model.Kardex;

public interface NotificacionService {

	public boolean notificarAlertaStockMinimo(List<Kardex> kardex);
	public boolean notificarVentanaHoraria(String codigoHojaRuta, String codigoPedido);
}
