package pe.com.gesatepedws.parametro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.common.ParametrosDBKeys;
import pe.com.gesatepedws.model.Parametro;
import pe.com.gesatepedws.parametro.dao.ParametroDAO;
import pe.com.gesatepedws.parametro.service.ParametroService;

@Service
public class ParametroServiceImpl implements ParametroService {

	private Map<String,Parametro> parametrosMap;
	
	public ParametroServiceImpl(@Autowired ParametroDAO parametroDAO) {
		this.parametrosMap = new HashMap<>();
		
		List<Parametro> parametros = parametroDAO.listar();
		for (Parametro parametro : parametros) {
			this.parametrosMap.put(parametro.getCodigo(), parametro);
		}
		
	}
	
	
	@Override
	public String getMailUsername() {
		return this.parametrosMap.get(
				ParametrosDBKeys.FROM_EMAIL.getKey())
				.getValor();
	}

	@Override
	public String getMailPassword() {
		return this.parametrosMap.get(
				ParametrosDBKeys.PASSWORD_EMAIL.getKey())
				.getValor();
	}

	@Override
	public String getMailServer() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMTP_SERVER.getKey())
				.getValor();
	}

	@Override
	public String getMailPort() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMTP_PORT.getKey())
				.getValor();
	}


	@Override
	public String getGoogleMapAPIKey() {
		return this.parametrosMap.get(
				ParametrosDBKeys.KEY_API_DIRECTIONS.getKey())
				.getValor();
	}


	@Override
	public String getSMSAPIKey() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMS_API_KEY.getKey())
				.getValor();
	}


	@Override
	public String getSMSRemitente() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMS_SENDER.getKey())
				.getValor();
	}


	@Override
	public String getSMSCodigoPaisDestino() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMS_COD_PAIS_DEST.getKey())
				.getValor();
	}


	@Override
	public String getSMSActivarModoPrueba() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMS_TEST_MODE.getKey())
				.getValor();
	}


	@Override
	public String getSMSServer() {
		return this.parametrosMap.get(
				ParametrosDBKeys.SMS_WS_PROVIDER_URL.getKey())
				.getValor();
	}
	
	@Override
	public String getEmailSubject() {
		return this.parametrosMap.get(
				ParametrosDBKeys.EMAIL_SUBJECT.getKey())
				.getValor();
	}

}
