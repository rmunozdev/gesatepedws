package pe.com.gesatepedws.reserva.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;

@Repository
public class ReservaDAOImpl implements ReservaDAO {
	
	@Autowired
    protected SqlSession gesatepedSession;

	@Override
	public Cliente getCliente(String codigoCliente) {
		return this.gesatepedSession.selectOne("Cliente.buscar", codigoCliente);
	}

	@Override
	public boolean reservarPedido(Pedido pedido) {
		this.gesatepedSession.insert("Pedido.reservar", pedido);
		for(DetallePedido detalle : pedido.getDetalles()) {
			this.gesatepedSession.insert("Pedido.agregarDetallesPedido",detalle);
		}
		return true;
	}

	@Override
	public boolean isDNIDisponible(String dni) {
		return this.gesatepedSession.selectList("Cliente.buscarPorDNI", dni).isEmpty();
	}

	@Override
	public boolean isNumeroReservaDisponible(Integer numeroReserva) {
		return this.gesatepedSession.selectList("Pedido.buscarPorNumeroReserva", numeroReserva).isEmpty();
	}

	@Override
	public boolean existeProducto(String codigoProducto) {
		return !this.gesatepedSession.selectList("Producto.buscar",codigoProducto).isEmpty();
	}

	@Override
	public boolean existeBodega(String codigoBodega) {
		return !this.gesatepedSession.selectList("Bodega.buscar",codigoBodega).isEmpty();
	}

	@Override
	public boolean existeDistrito(String codigoDistrito) {
		return !this.gesatepedSession.selectList("Distrito.buscar",codigoDistrito).isEmpty();
	}

	@Override
	public boolean existeTienda(String codigoTienda) {
		return !this.gesatepedSession.selectList("Tienda.buscar",codigoTienda).isEmpty();
	}

	@Override
	public boolean conflictoEmail(String email, String codigoCliente) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("codigoCliente", codigoCliente);
		return !this.gesatepedSession.selectList("Cliente.buscarPorEmail",params).isEmpty();
	}

	@Override
	public boolean conflictoTelefono(String telefono, String codigoCliente) {
		Map<String, Object> params = new HashMap<>();
		params.put("telefono", telefono);
		params.put("codigoCliente", codigoCliente);
		return !this.gesatepedSession.selectList("Cliente.buscarPorTelefono",params).isEmpty();
	}

	@Override
	public boolean isCodigoPedidoDisponible(String codigoPedido) {
		return this.gesatepedSession.selectList("Pedido.buscar", codigoPedido).isEmpty();
	}

	@Override
	public List<Kardex> listarKardex(String codigoPedido) {
		return this.gesatepedSession.selectList("Pedido.obtenerKardex",codigoPedido);
	}

	@Override
	public Integer obtenerStock(DetallePedido detalle) {
		return this.gesatepedSession.selectOne("Bodega.stock",detalle);
	}

}
