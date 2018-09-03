package pe.com.gesatepedws.reserva.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.extend.ReservaResponse;
import pe.com.gesatepedws.reserva.service.ReservaService;
import pe.com.gesatepedws.validacion.service.impl.PedidoJacksonValidationUtils;

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
	
	@ExceptionHandler
	public ResponseEntity<ReservaResponse> handleException(HttpServletRequest request, Throwable ex) {
		ex.printStackTrace();
		ReservaResponse response = new ReservaResponse();
		response.setCodigo(-1);
		if(ex instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException exception = (HttpMessageNotReadableException) ex;
			Throwable cause = exception.getCause();
			if(cause instanceof JsonMappingException) {
				JsonMappingException jsonException = (JsonMappingException) cause;
				response.addMensaje(PedidoJacksonValidationUtils.obtenerMensajeDesdeException(jsonException));
			} else if (cause instanceof JsonParseException) {
				JsonParseException parseException = (JsonParseException) cause;
				response.addMensaje(parseException.getMessage());
			}
		}
		
		return new ResponseEntity<ReservaResponse>(response,HttpStatus.PRECONDITION_FAILED);
	}
}
