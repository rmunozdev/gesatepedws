package pe.com.gesatepedws.validacion.service.impl;

public enum DespachoValidationCode {

	NUMERO_VERIFICACION_OBLIGATORIO("Debe ingresar n�mero de verificaci�n"),
	NUMERO_VERIFICACION_INCORRECTO("El n�mero de verificaci�n es incorrecto"),
	MOTIVO_INCUMPLIMIENTO_OBLIGATORIO("Debe elegir un motivo para el incumplimiento");
	
	private String mensaje;
	
	private DespachoValidationCode(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}
