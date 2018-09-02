package pe.com.gesatepedws.notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.gesatepedws.model.extend.NotificacionResponse;
import pe.com.gesatepedws.notificacion.service.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

	@Autowired
	private NotificacionService notificacionService;
	
	@GetMapping(path="/sms/{codigoHojaRuta}/{codigoPedido}")
	public ResponseEntity<NotificacionResponse> notificarSMS(
			@PathVariable("codigoHojaRuta") String codigoHojaRuta, 
			@PathVariable("codigoPedido") String codigoPedido) {
		
		NotificacionResponse response = this.notificacionService.notificarVentanaHoraria(codigoHojaRuta, codigoPedido);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path="/ejecutar")
	public ResponseEntity<List<NotificacionResponse>> ejecutarNotificacion() {
		List<NotificacionResponse> responses = this.notificacionService.notificarTodo();
		return new ResponseEntity<>(responses,HttpStatus.OK);
	}
}
