package pe.com.gesatepedws.mail.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.gesatepedws.mail.service.MailService;
import pe.com.gesatepedws.parametro.service.ParametroService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private ParametroService parametroService;
	
	@Override
	public Integer sendEmail(String mensaje, String destinatario) {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.EnableSSL.enable", "true");
		properties.put("mail.smtp.host", this.parametroService.getMailServer());
		
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");   
		properties.setProperty("mail.smtp.port", this.parametroService.getMailPort());   
		properties.setProperty("mail.smtp.socketFactory.port", this.parametroService.getMailPort());
		
		
		final String username = this.parametroService.getMailUsername();
		final String password = this.parametroService.getMailPassword();
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(this.parametroService.getMailUsername()));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
			//TODO Consultar que va en el asunto
			mimeMessage.setSubject("Correo de prueba");
			mimeMessage.setContent(mensaje, "text/html; charset=utf-8");
			
			Transport.send(mimeMessage);
			System.out.println("done");
		} catch(MessagingException exception) {
			exception.printStackTrace();
			return -1;
		}
		return 1;
	}

}
