package pe.com.gesatepedws.reserva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.extend.ReservaResponse;
import pe.com.gesatepedws.reserva.service.ReservaService;

@RestController
@RequestMapping("/reservar")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;
	
	@PostMapping
	public ResponseEntity<ReservaResponse> reservar(@RequestBody Pedido pedido) {
		ReservaResponse response = this.reservaService.reservarPedido(pedido);
		if(response.getCodigo() == 0) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
		}
	}
}
