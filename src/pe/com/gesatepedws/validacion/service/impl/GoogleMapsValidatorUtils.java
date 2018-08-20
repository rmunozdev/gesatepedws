package pe.com.gesatepedws.validacion.service.impl;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GoogleMapsValidatorUtils {

	//TODO Mover key a tabla en BD
	private static final String MAPS_API_KEY = "AIzaSyC5zx6JgWVPftfjOPJybTKhKUwhN5zVxJI";
	
	public static boolean validarDireccionCompleta(String direccion) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(MAPS_API_KEY).build();
		try {
			GeocodingResult[] results = GeocodingApi.geocode(context, direccion).await();
			if(results.length == 1) {
				return true;
			} else {
				System.out.println("Results: " + results.length);
			}
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
