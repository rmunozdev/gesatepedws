package pe.com.gesatepedws.validacion.service.impl;

import org.springframework.stereotype.Service;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.Distrito;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.Tienda;
import pe.com.gesatepedws.validacion.service.ValidacionService;

@Service
public class ValidacionServiceImpl implements ValidacionService {
	

	@Override
	public boolean validarPedido(Pedido pedido, boolean nullAceptable) {
		if(pedido.getCliente() == null 
				|| pedido.getFechaSolicitud() == null
				|| pedido.getNumeroReserva() == null
				|| pedido.getFechaVenta() == null
				|| pedido.getFechadespacho() == null
				
				|| pedido.getDireccionDespachoPedido() == null
				|| pedido.getDistritoDespacho() == null
				
				//Los campos fecha ret y tienda no son obligatorios.
				) {
			return false;
		} else {
			Cliente cliente = pedido.getCliente();
			if( valid(cliente.getCodigo(), nullAceptable)
					|| valid(cliente.getNombres(), nullAceptable)
					|| valid(cliente.getApellidos(), nullAceptable)
					|| valid(cliente.getNumeroDNI(), nullAceptable)
					|| valid(cliente.getEmail(), nullAceptable)
					|| valid(cliente.getTelefono(), nullAceptable)
					|| valid(cliente.getDireccion(), nullAceptable)
					|| valid(cliente.getDistrito(), nullAceptable)
					) {
				if(pedido.getFechaRetiroTienda() != null) {
					Tienda tienda = pedido.getTiendaDespacho();
					if(tienda == null || tienda.getCodigo() == null) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	}
	
	private static boolean valid(String value, boolean nullAceptable) {
		if(value == null) {
			return nullAceptable;
		} else if(value.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private static boolean valid(Distrito distrito, boolean nullAceptable) {
		if(distrito == null || distrito.getCodigo() == null) {
			return nullAceptable;
		} else if (distrito.getCodigo().isEmpty()) {
			return false;
		}
		return true;
	}

}
