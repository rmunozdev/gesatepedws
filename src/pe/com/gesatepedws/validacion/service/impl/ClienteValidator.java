package pe.com.gesatepedws.validacion.service.impl;

import java.util.HashMap;
import java.util.Map;

import pe.com.gesatepedws.model.Cliente;
import pe.com.gesatepedws.reserva.dao.ReservaDAO;

public class ClienteValidator {
	
	private final Cliente cliente;
	private final ReservaDAO datasource;
	private Map<String,Boolean> failMap;

	public ClienteValidator(Cliente cliente, ReservaDAO datasource) {
		this.cliente = cliente;
		this.datasource = datasource;
		this.failMap = new HashMap<>();
	}
	
	public ClienteValidator conTodosLosDatosAdecuados() {
		this
			.conNombreAdecuado()
			.conApellidoAdecuado()
			.conNumeroDNIAdecuado()
			.conEmailAdecuado()
			.conTelefonoAdecuado()
			.conDireccionAdecuada()
			.conCodigoDistritoExistente()
			;
		return this;
	}
	
	public ClienteValidator conTodoObligatorio() {
		this
		.conNombreObligatorio()
		.conApellidoObligatorio()
		.conDNIObligatorio()
		.conEmailObligatorio()
		.conTelefonoObligatorio()
		.conDireccionYDistritoObligatorio()
		;
	return this;
	}
	
	public ClienteValidator conDNIObligatorio() {
		this.failMap.put("Debe ingresar DNI de cliente", 
				this.cliente.getNumeroDNI() == null 
				|| this.cliente.getNumeroDNI().isEmpty());
		return this;
	}
	
	public ClienteValidator conNombreObligatorio() {
		this.failMap.put("Debe ingresar Nombre de cliente", 
				this.cliente.getNombres() == null 
				|| this.cliente.getNombres().isEmpty());
		return this;
	}
	
	public ClienteValidator conApellidoObligatorio() {
		this.failMap.put("Debe ingresar apellido de cliente", 
				this.cliente.getApellidos() == null 
				|| this.cliente.getApellidos().isEmpty());
		return this;
	}
	
	public ClienteValidator conEmailObligatorio() {
		this.failMap.put("Debe ingresar email de cliente", 
				this.cliente.getEmail() == null 
				|| this.cliente.getEmail().isEmpty());
		return this;
	}
	
	public ClienteValidator conTelefonoObligatorio() {
		this.failMap.put("Debe ingresar teléfono de cliente", 
				this.cliente.getTelefono() == null 
				|| this.cliente.getTelefono().isEmpty());
		return this;
	}
	
	public ClienteValidator conDireccionYDistritoObligatorio() {
		this.failMap.put("Debe ingresar la direccion y distrito de cliente", 
				this.cliente.getDireccion() == null
				|| this.cliente.getDireccion().isEmpty() 
				|| this.cliente.getDistrito() == null
				|| this.cliente.getDistrito().getCodigo() == null
				|| this.cliente.getDistrito().getCodigo().isEmpty());
		return this;
	}
	
	public ClienteValidator conDNINoReutilizado() {
		this.failMap.put("DNI ya usado", 
				!this.datasource.isDNIDisponible(this.cliente.getNumeroDNI()));
		return this;
	}
	
	public ClienteValidator conNombreAdecuado() {
		this.failMap.put("Nombre minimo 1 caracter", 
				this.cliente.getNombres() != null && 
				( this.cliente.getNombres().length() < 1)
				);
		return this;
	}
	
	public ClienteValidator conApellidoAdecuado() {
		this.failMap.put("Apellido minimo 1 caracter", 
				this.cliente.getApellidos() != null && 
				( this.cliente.getApellidos().length() < 1)
				);
		return this;
	}
	
	public ClienteValidator conNumeroDNIAdecuado() {
		this.failMap.put("DNI minimo 8 caracteres", 
				this.cliente.getNumeroDNI() != null && 
				( this.cliente.getNumeroDNI().length() < 8)
				);
		return this;
	}
	
	public ClienteValidator conTelefonoAdecuado() {
		this.failMap.put("Teléfono minimo 1 caracter", 
				this.cliente.getTelefono() != null && 
				( this.cliente.getTelefono().length() < 1
				));
		return this;
	}
	
	public ClienteValidator conEmailAdecuado() {
		this.failMap.put("Email minimo 1 caracter", 
				this.cliente.getEmail() != null && 
				( this.cliente.getEmail().length() < 1
				));
		return this;
	}
	
	public ClienteValidator conDireccionAdecuada() {
		this.failMap.put("Email minimo 1 caracter", 
				this.cliente.getDireccion() != null && 
				( this.cliente.getDireccion().length() < 1
				));
		return this;
	}
	
	public ClienteValidator conCodigoDistritoExistente() {
		if(this.cliente.getDistrito() != null
				&& this.cliente.getDistrito().getCodigo() != null
				& !this.cliente.getDistrito().getCodigo().isEmpty()) {
			this.failMap.put("El distrito de domicilio del cliente no se encuentra registrado",  
				!this.datasource.existeDistrito(
						this.cliente.getDistrito().getCodigo()));
		}
		return this;
	}
	
	public ClienteValidator sinConflictoEmail() {
		if(this.cliente.getEmail()!= null 
				&& !this.cliente.getEmail().isEmpty()) {
			this.failMap.put("Email " 
				+ this.cliente.getEmail() 
				+ " está siendo usado por otro usuario", 
				this.datasource.conflictoEmail(this.cliente.getEmail(), this.cliente.getCodigo()));
		}
		return this;
	}
	
	public ClienteValidator sinConflictoTelefono() {
		if(this.cliente.getTelefono()!= null 
				&& !this.cliente.getTelefono().isEmpty()) {
			this.failMap.put("Telefono " 
				+ this.cliente.getTelefono() 
				+ " está siendo usado por otro usuario", 
				this.datasource.conflictoTelefono(this.cliente.getTelefono(), this.cliente.getCodigo()));
		}
		return this;
	}
	
	public boolean valid() {
		for (Boolean fail : this.failMap.values()) {
			if(fail) {
				return false;
			}
		}
		return true;
	}
	
	public Map<String,Boolean> getFailMap() {
		return this.failMap;
	}
}
