package pe.com.gesatepedws.validacion.service.impl;

public enum DespachoValidationCode {

	NUMERO_VERIFICACION_OBLIGATORIO("Debe ingresar número de verificación"),
	NUMERO_VERIFICACION_INCORRECTO("El número de verificación es incorrecto"),
	MOTIVO_INCUMPLIMIENTO_OBLIGATORIO("Debe elegir un motivo para el incumplimiento");
	
	private String mensaje;
	
	private DespachoValidationCode(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}
