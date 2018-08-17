package pe.com.gesatepedws.notificacion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pe.com.gesatepedws.model.Kardex;
import pe.com.gesatepedws.notificacion.service.NotificacionService;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Override
	public boolean notificarAlertaStockMinimo(List<Kardex> kardexs) {
		List<Kardex> notificables = new ArrayList<>();
		for (Kardex kardex : kardexs) {
			if(kardex.getStockMinimo() > kardex.getStockActual()) {
				notificables.add(kardex);
			}
		}
		
		System.out.println("Notificacion:");
		for (Kardex kardex : notificables) {
			System.out.println(">>" + kardex.getProducto().getNombre());
		}
		
		return false;
	}

}
