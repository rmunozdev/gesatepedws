package pe.com.gesatepedws.reserva.dao;

import java.util.List;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.model.Pedido;

public interface ReservaDAO {

	public Cliente getCliente(String codigoCliente);
	public boolean isDNIDisponible(String dni);
	public boolean isNumeroReservaDisponible(Integer numeroReserva);
	public boolean isCodigoPedidoDisponible(String codigoPedido);
	public boolean existeProducto(String codigoProducto);
	public boolean existeBodega(String codigoBodega);
	public boolean existeDistrito(String codigoDistrito);
	public boolean existeTienda(String codigoTienda);
	public boolean conflictoEmail(String email, String codigoCliente);
	public boolean conflictoTelefono(String telefono, String codigoCliente);
	public boolean reservarPedido(Pedido pedido);
	public List<Kardex> listarKardex(String codigoPedido);
	public Integer obtenerStock(DetallePedido pedido);
}
