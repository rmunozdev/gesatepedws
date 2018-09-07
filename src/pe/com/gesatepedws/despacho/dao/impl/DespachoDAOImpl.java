package pe.com.gesatepedws.despacho.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.gesatepedws.despacho.dao.DespachoDAO;
import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;
import pe.com.gesatepedws.model.extend.DetalleRutaParam;
import pe.com.gesatepedws.model.extend.EstadoPedido;
import pe.com.gesatepedws.model.extend.EstadoPedidoParam;

@Repository
public class DespachoDAOImpl implements DespachoDAO {

	
	@Autowired
    protected SqlSession gesatepedSession;
	
	@Override
	public Ruta getRuta(String brevete, Date despacho) {
		Ruta param = new Ruta.Builder(new Date())
				.forChofer(brevete)
				.build();
		Ruta ruta = this.gesatepedSession.selectOne("Ruta.getRuta", param);
		if(ruta != null) {
			List<DetalleRuta> detalles = this.gesatepedSession.selectList("Ruta.detallesRuta",ruta.getCodigo());
			ruta.setDetalles(detalles);
		} else {
			ruta = new Ruta();
		}
		return ruta;
	}

	@Override
	public List<DetallePedido> getDetalle(String codigoPedido, String codigoBodega) {
		DetallePedido param = new DetallePedido.Builder(codigoPedido)
				.forBodega(codigoBodega)
				.build();
		return this.gesatepedSession.selectList("Ruta.detallesPedido",param);
	}

	@Override
	public String getEstado(String ruta) {
		EstadoPedidoParam estadoPedidoParam = new EstadoPedidoParam(ruta);
		
		List<EstadoPedido> estados = this.gesatepedSession.selectList("Ruta.estado",estadoPedidoParam);
		
		StringBuilder builder = new StringBuilder();
		for (EstadoPedido estadoPedido : estados) {
			builder.append(estadoPedido.getPorcentaje());
			builder.append(".");
		}
		return builder.toString();
	}

	@Override
	public List<MotivoPedido> getMotivos(String categoria) {
		return this.gesatepedSession.selectList("Ruta.motivos",categoria);
	}

	@Override
	public boolean registrarAtencion(String codigoRuta, DetalleRuta detalle) {
		try {
			DetalleRutaParam param = new DetalleRutaParam.Builder(codigoRuta).withDetalle(detalle).build();
			System.out.println("Insertando para:");
			System.out.println("Codigo hoja ruta: " + codigoRuta);
			System.out.println("Codigo de pedido: " + detalle.getPedido().getCodigo());
			this.gesatepedSession.insert("Ruta.atencion", param);
			System.out.println("Resultado:");
			System.out.println("Codigo: " + param.getCodigoRespuesta());
			System.out.println("Mensaje:" + param.getMensajeRespuesta());
			return true;
		} catch(Exception exception) {
			return false;
		}
	}

	@Override
	public boolean registrarIncumplimiento(String codigoRuta, DetalleRuta detalle) {
		try {
			DetalleRutaParam param = new DetalleRutaParam.Builder(codigoRuta).withDetalle(detalle).build();
			this.gesatepedSession.insert("Ruta.incumplimiento", param);
			return true;
		} catch(Exception exception) {
			return false;
		}
	}

	@Override
	public List<Chofer> getChoferes() {
		return this.gesatepedSession.selectList("Ruta.choferes");
	}

	@Override
	public String getImagen(String codigoRuta, String codigoPedido) {
		DetalleRutaParam param = new DetalleRutaParam.Builder(codigoRuta)
				.withPedido(codigoPedido)
				.build();
		return this.gesatepedSession.selectOne("Ruta.imagen", param);
	}
	
	@Override
	public Integer getNumeroVerificacion(String codigoPedido) {
		return this.gesatepedSession.selectOne("Pedido.obtenerNumeroVerificacion",codigoPedido);
	}

}
