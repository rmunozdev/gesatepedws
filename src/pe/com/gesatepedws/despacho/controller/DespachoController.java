package pe.com.gesatepedws.despacho.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.gesatepedws.despacho.service.DespachoService;
import pe.com.gesatepedws.model.Chofer;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.DetalleRuta;
import pe.com.gesatepedws.model.MotivoPedido;
import pe.com.gesatepedws.model.Ruta;
import pe.com.gesatepedws.model.extend.DespachoResponse;

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
			System.out.println("Sesion: " + this.session.getId());
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
	
	@GetMapping("isModified/{codigoRuta}")
	public ResponseEntity<Boolean> isModified(@PathVariable("codigoRuta") String codigoRuta) {
		String estado = String.valueOf(session.getAttribute(ESTADO_EN_SESION));
		if(estado != null) {
			System.out.println("Session found!! : ");
			System.out.println("Sesion: " + this.session.getId());
			String nuevoEstado = this.despachoService.getEstado(codigoRuta);
			boolean isModified = !estado.equals(nuevoEstado);
			
			if(isModified) {
				session.setAttribute(ESTADO_EN_SESION, nuevoEstado);
			}
			return new ResponseEntity<>(isModified,HttpStatus.OK);
		}
		return new ResponseEntity<>(false,HttpStatus.OK);
	}
	
	@GetMapping("motivos")
	public ResponseEntity<List<MotivoPedido>> getMotivos() {
		return new ResponseEntity<>(this.despachoService.getMotivos(),HttpStatus.OK);
	}
	
	@PostMapping("atencion")
	public ResponseEntity<DespachoResponse> registrarAtencion(@RequestBody DetalleRuta detalle) {
		DespachoResponse resultado = this.despachoService.registrarAtencion(
				detalle.getHojaRuta().getCodigo(), 
				detalle);
		return new ResponseEntity<>(resultado,HttpStatus.OK);
	}
	
	@PostMapping("incumplimiento")
	public ResponseEntity<DespachoResponse> registrarIncumplimiento(@RequestBody DetalleRuta detalle) {
		DespachoResponse resultado = this.despachoService.registrarIncumplimiento(
				detalle.getHojaRuta().getCodigo(), 
				detalle);
		return new ResponseEntity<>(resultado,HttpStatus.OK);
	}
	
	@GetMapping("image/{codigoRuta}/{codigoPedido}")
	public ResponseEntity<String> getImagen(
			@PathVariable("codigoPedido") String codigoPedido,
			@PathVariable("codigoRuta") String codigoRuta
			) {
		System.out.println(">>>>Ruta: " +codigoRuta);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<>(this.despachoService.getImagen(codigoRuta, codigoPedido),responseHeaders,HttpStatus.OK);
	}
	
	@GetMapping(path="image64/{codigoRuta}/{codigoPedido}", produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String getBase64Image(
			@PathVariable("codigoPedido") String codigoPedido,
			@PathVariable("codigoRuta") String codigoRuta
			) {
		return this.despachoService.getImagen(codigoRuta, codigoPedido);
	}
	
}
