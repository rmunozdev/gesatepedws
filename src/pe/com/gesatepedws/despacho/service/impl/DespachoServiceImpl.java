package pe.com.gesatepedws.despacho.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.despacho.dao.DespachoDAO;
import pe.com.gesatepedws.despacho.service.DespachoService;
import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;

@Service
public class DespachoServiceImpl implements DespachoService {

	@Autowired
	private DespachoDAO despachoDAO;
	
	@Override
	public Ruta getRuta(String brevete) {
		return this.despachoDAO.getRuta(brevete, new Date());
	}

	@Override
	public List<DetallePedido> getDetalle(String codigoPedido, String codigoBodega) {
		return this.despachoDAO.getDetalle(codigoPedido, codigoBodega);
	}

	@Override
	public String getEstado(String ruta) {
		return this.despachoDAO.getEstado(ruta);
	}

	@Override
	public List<MotivoPedido> getMotivos() {
		return this.despachoDAO.getMotivos("NCUM");
	}

	@Override
	public boolean registrarAtencion(String codigoRuta, DetalleRuta detalle) {
		return this.despachoDAO.registrarAtencion(codigoRuta, detalle);
	}

	@Override
	public boolean registrarIncumplimiento(String codigoRuta, DetalleRuta detalle) {
		return this.despachoDAO.registrarIncumplimiento(codigoRuta, detalle);
	}

	@Override
	public List<Chofer> getChoferes() {
		return this.despachoDAO.getChoferes();
	}

	@Override
	public String getImagen(String codigoRuta, String codigoPedido) {
		return this.despachoDAO.getImagen(codigoRuta, codigoPedido);
	}

}
