package pe.com.gesatepedws.notificacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.gesatepedws.notificacion.service.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

	@Autowired
	private NotificacionService notificacionService;
	
	@GetMapping(path="/sms/{codigoHojaRuta}/{codigoPedido}")
	public ResponseEntity<String> notificarSMS(
			@PathVariable("codigoHojaRuta") String codigoHojaRuta, 
			@PathVariable("codigoPedido") String codigoPedido) {
		this.notificacionService.notificarVentanaHoraria(codigoHojaRuta, codigoPedido);
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
}
