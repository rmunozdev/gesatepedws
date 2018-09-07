package pe.com.gesatepedws.common;

public class GesatepedConstants {

	//Su pedido PED0000001 con número de verificación 12345 llegará el 14/06/2018 de 08:00 am a 12:00 pm.
	public static final String SMS_MESSAGE_TEMPLATE = 
			"Estimado Cliente:\n"
			+ "Su pedido %s "
			+ "con número de verificación %s "
			+ "llegará el %s "
			+ " %s.\n"
			+ "Atentamente,\n"
			+ "Sodimac."
			;
}
