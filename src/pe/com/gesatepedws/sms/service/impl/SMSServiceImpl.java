package pe.com.gesatepedws.sms.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import pe.com.gesatepedws.sms.service.SMSService;

@Service
public class SMSServiceImpl implements SMSService {

	@Override
	public boolean sendSMS(String mensaje, String numero) {
		try {
			//TODO Mover API Key
			String apiKey = "apiKey=" + "jiTXgvNzM2I-dNxGxOJlfngifDQlDe828vW0yfEPOo";
			String message = "&message=" + URLEncoder.encode(mensaje, "UTF-8");
			String sender = "&sender=" + "Sodimac";
			String numbers = "&numbers=51" + numero;
			String test = "&test=true";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender + test;
			
			System.out.println(">>>>data" + data);
			
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
			System.out.println("SMS Enviado, response: ");
			System.out.println(stringBuffer);
			return true;
		} catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

}
