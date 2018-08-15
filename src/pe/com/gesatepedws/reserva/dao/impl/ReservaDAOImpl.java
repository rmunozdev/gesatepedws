package pe.com.gesatepedws.reserva.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.gesatepedws.model.Cliente;
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
		this.gesatepedSession.insert("Pedido.agregarDetallesPedido",pedido.getDetalles());
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

}
