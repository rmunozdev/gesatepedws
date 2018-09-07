package pe.com.gesatepedws.mail.service;

public interface MailService {

	public Integer sendEmail(String asunto, String mensaje, String destinatario);
}
