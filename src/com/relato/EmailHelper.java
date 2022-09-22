package  com.relato;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHelper {

	private Session session;
    private ArrayList dests = new ArrayList();
    private ArrayList destscc = new ArrayList();
	private InternetAddress from;
	private String sender = "borderoonline@dgsbrasil.com.br";

	public EmailHelper(String parametro) throws Exception
	{
		Properties prop = new Properties();
		session = Session.getInstance(getPropriedades(),getAuthenticator());
		from = new InternetAddress(sender);
	}

	public EmailHelper() throws Exception
	{
		Properties prop = new Properties();
		session = Session.getInstance(getPropriedades(),getAuthenticator());
		from = new InternetAddress(sender);
	}

	//Método que retorna a autenticação de sua conta de email
	public static Authenticator getAuthenticator(){

		Authenticator autenticacao = new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				//Preencha com seu email e com sua senha
				return new PasswordAuthentication("borderoonline@dgsbrasil.com.br", "Wls130881");
	      }
		};

		return autenticacao;
	}

	//Método que retorna as propriedades de configuração do servidor de email
	public static Properties getPropriedades(){

		Properties props = new Properties();
		props.put("mail.debug", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "smtp.terra.com.br"); //SMTP do seu servidor de email
		props.put("mail.smtp.auth", "true"); //ativa autenticacao
		props.put("mail.smtp.port", "587"); //Porta do seu servidor smtp
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", "587"); //Porta do servidor smtp
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //Define a conexão do tipo SSL
		props.put("mail.host", "smtp.terra.com.br"); //SMTP do seu servidor de email
		props.put("mail.user", "borderoonline@dgsbrasil.com.br");//put your username with domain exp.. asdf@gmail.com
		props.put("mail.password", "Wls130881");//put your password
		props.put("mail.domain", "@dgsbrasil.com.br");
		props.put("mail.smtp.domain", "@dgsbrasil.com.br");
		props.put("mail.smtp.user", "borderoonline@dgsbrasil.com.br");//put your username with domain exp.. asdf@gmail.com
		props.put("mail.smtp.password", "Wls130881");//put your password
		props.put("mail.smtp.localhost", "127.0.0.1");
		return props;
	}


	public void addDest(String dd) throws MessagingException
	{
		dests.add(new InternetAddress(dd));
	}

	public void addDestCc(String dd) throws MessagingException
	{
		destscc.add(new InternetAddress(dd));
	}
	public void envia(String subject, String content) throws MessagingException
	{
            envia(subject, content, "text/plain");
	}
	public void enviaHTML(String subject, String content) throws MessagingException
	{
            envia(subject, content, "text/html");
	}
	public void envia(String subject, String content, String contentType) throws MessagingException
	{

		try{
			Transport tr;
			tr = session.getTransport("smtp");
			Message message = new MimeMessage(session);
            message.setFrom(from);
            InternetAddress[] dest = (InternetAddress[])dests.toArray(new  InternetAddress[dests.size()]);
            message.setRecipients(Message.RecipientType.TO, dest);
            if(!destscc.isEmpty()){
	            InternetAddress[] destcc = (InternetAddress[])destscc.toArray(new  InternetAddress[destscc.size()]);
	            message.setRecipients(Message.RecipientType.CC, destcc);
            }
            message.setSubject(subject);
            message.setSentDate(new Date());
			message.setContent(content, contentType);
		    tr.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		System.out.println("Testes de envio.");
		EmailHelper eh;
		try {
			eh = new EmailHelper();
			eh.addDest("rafael.lima@r1ti.com.br");
			eh.envia("Testes de envio EMAIL", "BLABLABLA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Finalizado.");
	}

}