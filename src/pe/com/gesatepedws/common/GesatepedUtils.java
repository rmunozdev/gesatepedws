package pe.com.gesatepedws.common;

import java.util.Calendar;
import java.util.Date;

public class GesatepedUtils {
	
	
	public static Date getDiaSiguiente() {
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE, 1);
		return instance.getTime();
	}

}
