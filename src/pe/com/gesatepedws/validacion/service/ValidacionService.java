package pe.com.gesatepedws.validacion.service;

import pe.com.gesatepedws.model.Pedido;

public interface ValidacionService {

	public boolean validarPedido(Pedido pedido, boolean nullAceptable);
}
