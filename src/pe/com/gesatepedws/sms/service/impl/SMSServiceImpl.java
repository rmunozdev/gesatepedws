package pe.com.gesatepedws.sms.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pe.com.gesatepedws.parametro.service.ParametroService;
import pe.com.gesatepedws.sms.service.SMSService;

@Service
public class SMSServiceImpl implements SMSService {

	private static final Logger logger = Logger.getLogger(SMSServiceImpl.class);
	
	@Autowired
	private ParametroService parametroService;
	
	@Override
	public Integer sendSMS(String mensaje, String numero) {
		String ws_url = this.parametroService.getSMSServer();
		try {
			String apiKey = "apiKey=" + this.parametroService.getSMSAPIKey();
			String message = "&message=" + URLEncoder.encode(mensaje, "UTF-8");
			String sender = "&sender=" + this.parametroService.getSMSRemitente();
			String numbers = "&numbers=" + this.parametroService.getSMSCodigoPaisDestino() + numero;
			String test = "&test=" + this.parametroService.getSMSActivarModoPrueba();
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL(ws_url).openConnection();
			String data = apiKey + numbers + message + sender + test;
			
			logger.info("Contenido a  envíar: " + data);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			logger.info("SMS Enviado, response: ");
			logger.info(stringBuffer.toString());
			JsonElement jsonElement = new JsonParser().parse(stringBuffer.toString());
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement status = jsonObject.get("status");
			if("success".equals(status.getAsString())) {
				return SMSResponseCodes.SUCESS;
			} else {
				return SMSResponseCodes.FAIL;
			}
		} catch(UnknownHostException exception) {
			logger.error("Falla grave al establecer comunicacion con " + ws_url,exception);
			throw new IllegalStateException("No se pudo establecer comunicacion", exception);
		} catch(Exception exception) {
			logger.error("Excepción al enviar SMS al " + numero, exception);
			return SMSResponseCodes.FAIL;
		}
	}

}
