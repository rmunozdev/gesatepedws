package pe.com.gesatepedws.notificacion.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
