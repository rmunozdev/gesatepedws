package pe.com.gesatepedws.reserva.service;

import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.extend.ReservaResponse;

public interface ReservaService {
	
	public ReservaResponse reservarPedido(Pedido pedido);
	
}
