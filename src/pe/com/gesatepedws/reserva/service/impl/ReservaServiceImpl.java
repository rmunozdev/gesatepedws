package pe.com.gesatepedws.reserva.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.model.DetallePedido;
import pe.com.gesatepedws.model.Pedido;
import pe.com.gesatepedws.model.extend.ReservaResponse;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;
import pe.com.gesatepedws.reserva.service.ReservaService;
import pe.com.gesatepedws.validacion.service.impl.PedidoValidator;

@Service
public class ReservaServiceImpl implements ReservaService {

	
	
	private static final int CODIGO_RESPUESTA_FALLO_VALIDACION = -1;
	private static final int CODIGO_RESPUESTA_FALLA_DAO = -2;
	private static final int CODIGO_RESPUESTA_EXITO = 0;
	@Autowired
	private ReservaDAO reservaDAO;
	
	@Override
	public ReservaResponse reservarPedido(Pedido pedido) {
		ReservaResponse response = new ReservaResponse();
		Cliente cliente = this.reservaDAO.getCliente(pedido.getCliente().getCodigo());;
		
		PedidoValidator pedidoValidator = new PedidoValidator(pedido, reservaDAO)
				.conCodigoDeClienteObligatorio()
				.conNumeroReservaNoReutilizado()
				.conFechaSolicitudObligatoria()
				.conFechaVentaObligatoria()
				.conFechaDespachoObligatoria()
				.conDireccionYDitritoDespachoObligatoria()
				.conTiendaDespachoYFechaRetiroCoherente()
				.conDatosDeClienteAdecuados()
				.conAlMenosUnDetalle()
				.conDetallesAdecuados()
				.conCodigoDistritoExistente()
				.conCodigoTiendaExistente()
		;
		
		if(cliente == null) {
			pedidoValidator
			.conDatosDeClienteObligatorios()
			.conDNIClienteNoReutilizado();
		}
		
		if(pedidoValidator.valid()) {
			
			//Fill cod_ped on detalles
			for(DetallePedido detalle : pedido.getDetalles()) {
				detalle.setPedido(pedido);
			}
			
			if(this.reservaDAO.reservarPedido(pedido)) {
				response.setCodigo(CODIGO_RESPUESTA_EXITO);
				response.getMensajes().add("La reserva de pedido fue realizada con éxito");
			} else {
				response.setCodigo(CODIGO_RESPUESTA_FALLA_DAO);
			}
			
		} else {
			response.setCodigo(CODIGO_RESPUESTA_FALLO_VALIDACION);
			for(String failName : pedidoValidator.getFailMap().keySet()) {
				if(pedidoValidator.getFailMap().get(failName)) {
					response.getMensajes().add(failName);
				}
			}
		}
		return response;
		
	}
}
