package pe.com.gesatepedws.validacion.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GoogleMapsValidatorUtils {
	
	private static final Logger logger = Logger.getLogger(GoogleMapsValidatorUtils.class);

	private static String MAPS_API_KEY;
	
	public static void setAPIKey(String apiKey) {
		MAPS_API_KEY = apiKey;
	}
	
	public static boolean validarDireccionCompleta(String direccion) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(MAPS_API_KEY).build();
		try {
			GeocodingResult[] results = GeocodingApi.geocode(context, direccion).await();
			if(results.length == 1) {
				logger.info("GOOGLE results for " + direccion);
				logger.info("Place id: " + results[0].placeId);
				logger.info("Formated address: " + results[0].formattedAddress);
				logger.info("Lat/lng: " + results[0].geometry.location);
				logger.info("Partial match: " + results[0].partialMatch);
				
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
