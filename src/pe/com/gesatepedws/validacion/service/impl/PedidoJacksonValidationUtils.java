package pe.com.gesatepedws.validacion.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;

public class PedidoJacksonValidationUtils {
	
	public static String obtenerMensajeDesdeException(JsonMappingException jsonException) {
		System.out.println(jsonException.getLocalizedMessage());
		System.out.println("Path reference: " + jsonException.getPathReference());
		System.out.println("Elements in path: " + jsonException.getPath().size());
		
		String fieldName = jsonException.getPath().get(jsonException.getPath().size()-1).getFieldName();
		switch(fieldName) {
		case "fechaSolicitud":
			return "Formato de fecha de solicitud: dd/MM/yyyy HH:mm:ss";
		case "fechaVentaPedido":
			return"Formato de fecha de venta de pedido: dd/MM/yyyy HH:mm:ss";
		case "fechaDespacho":
			return "Formato de fecha de despacho: dd/MM/yyyy";
		case "cantidadProducto":
			return "La cantidad de producto debe ser un número";
		default:
			return "Formato de " + fieldName + " incorrecto.";
		}
	}

}
