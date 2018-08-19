package pe.com.gesatepedws.despacho.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.gesatepedws.despacho.service.DespachoService;
import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;

@RestController
@RequestMapping("/despachos")
public class DespachoController {

	private static final String BODEGA_EN_SESION = "BODEGA_EN_SESION";
	private static final String ESTADO_EN_SESION = "ESTADO_SESION";
	private static final String RUTA_EN_SESION = "RUTA_SESION";

	@Autowired
	private DespachoService despachoService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping
	public ResponseEntity<String> login() {
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	//ACCESO LIBRE
	@GetMapping(path="/choferes")
	public ResponseEntity<List<Chofer>> listChoferes() {
		return new ResponseEntity<>(despachoService.getChoferes(),HttpStatus.OK);
	}
	

	
	//ACCESO RESTRINGIDO POR SESION
	@GetMapping(path="/ruta/{breveteChofer}")
	public ResponseEntity<Ruta> getRuta(@PathVariable("breveteChofer") String brevete) {
		Ruta ruta = despachoService.getRuta(brevete);
		if(ruta.getCodigo() != null) {
			session.setAttribute(RUTA_EN_SESION, ruta.getCodigo());
			session.setAttribute(BODEGA_EN_SESION, ruta.getBodega().getCodigo());
			session.setAttribute(ESTADO_EN_SESION, this.despachoService.getEstado(ruta.getCodigo()));
		}
		return new ResponseEntity<>(ruta, HttpStatus.OK);
	}
	
	@GetMapping(path="/ruta/pedido/{codigoBodega}/{codigoPedido}")
	public ResponseEntity<List<DetallePedido>> getDetallePedidos(
			@PathVariable("codigoPedido") String codigoPedido, @PathVariable("codigoBodega") String codigoBodega) {
		System.out.println(">>Bodega: " + codigoBodega);
		List<DetallePedido> detalles = despachoService.getDetalle(codigoPedido, codigoBodega);
		return new ResponseEntity<>(detalles,HttpStatus.OK);
	}
	
	@GetMapping("isModified")
	public ResponseEntity<Boolean> isModified() {
		String estado = String.valueOf(session.getAttribute(ESTADO_EN_SESION));
		String ruta = String.valueOf(session.getAttribute(RUTA_EN_SESION));
		String nuevoEstado = this.despachoService.getEstado(ruta);
		boolean isModified = !estado.equals(nuevoEstado);
		
		if(isModified) {
			session.setAttribute(ESTADO_EN_SESION, nuevoEstado);
		}
		return new ResponseEntity<>(isModified,HttpStatus.OK);
	}
	
	@GetMapping("motivos")
	public ResponseEntity<List<MotivoPedido>> getMotivos() {
		return new ResponseEntity<>(this.despachoService.getMotivos(),HttpStatus.OK);
	}
	
	@PostMapping("atencion")
	public ResponseEntity<Boolean> registrarAtencion(@RequestBody DetalleRuta detalle) {
		boolean resultado = this.despachoService.registrarAtencion(
				detalle.getHojaRuta().getCodigo(), 
				detalle);
		return new ResponseEntity<>(resultado,HttpStatus.OK);
	}
	
	@PostMapping("incumplimiento")
	public ResponseEntity<Boolean> registrarIncumplimiento(@RequestBody DetalleRuta detalle) {
		boolean resultado = this.despachoService.registrarIncumplimiento(
				detalle.getHojaRuta().getCodigo(), 
				detalle);
		return new ResponseEntity<>(resultado,HttpStatus.OK);
	}
	
	@GetMapping("image/{codigoPedido}")
	public ResponseEntity<String> getImagen(@PathVariable("codigoPedido") String codigoPedido) {
		String codigoRuta = String.valueOf(session.getAttribute(RUTA_EN_SESION));
		System.out.println(">>>>Ruta: " +codigoRuta);
		return new ResponseEntity<>(this.despachoService.getImagen(codigoRuta, codigoPedido),HttpStatus.OK);
	}
	
}
