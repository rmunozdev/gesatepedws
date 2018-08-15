package pe.com.gesatepedws.reserva.dao;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.Pedido;

public interface ReservaDAO {

	public Cliente getCliente(String codigoCliente);
	public boolean isDNIDisponible(String dni);
	public boolean isNumeroReservaDisponible(Integer numeroReserva);
	public boolean existeProducto(String codigoProducto);
	public boolean existeBodega(String codigoBodega);
	public boolean existeDistrito(String codigoDistrito);
	public boolean existeTienda(String codigoTienda);
	public boolean reservarPedido(Pedido pedido);
}
