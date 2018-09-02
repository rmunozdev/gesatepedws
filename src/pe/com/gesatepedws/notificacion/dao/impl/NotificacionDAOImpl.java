package pe.com.gesatepedws.notificacion.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.gesatepedws.model.ControllerTienda;
import pe.com.gesatepedws.model.extend.MensajeData;
import pe.com.gesatepedws.notificacion.dao.NotificacionDAO;

@Repository
public class NotificacionDAOImpl implements NotificacionDAO {

	@Autowired
	private SqlSession gesatepedSession;
	
	@Override
	public MensajeData obtenerMensajeData(String codigoHojaRuta, String codigoPedido) {
		MensajeData mensajeData = new MensajeData();
		mensajeData.setCodigoHojaRuta(codigoHojaRuta);
		mensajeData.setCodigoPedido(codigoPedido);
		return this.gesatepedSession.selectOne("MensajeData.get",mensajeData);
	}

	@Override
	public List<MensajeData> obtenerMensajesParaTodasLasRutas(Date fecha) {
		return this.gesatepedSession.selectList("MensajeData.list",fecha);
	}

	@Override
	public boolean registrarNotificacion(MensajeData mensajeData) {
		int update = this.gesatepedSession.update("MensajeData.registrarNotificacion", mensajeData);
		if(update > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ControllerTienda getControllerTienda(String codigoTienda) {
		return this.gesatepedSession.selectOne("ControllerTienda.get", codigoTienda);
	}

}
