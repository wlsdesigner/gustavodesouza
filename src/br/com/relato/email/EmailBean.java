package br.com.relato.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.relato.ControlServlet;
import br.com.relato.InvalidRequestException;

public class EmailBean extends ControlServlet{

	public EmailBean(){
		setErrorPage("/email/emailresponse.jsp");
		setSucessPage("/email/emailresponse.jsp");
	}

	public void envia(String from,
			String subject,
			String content,
			Session session,
			InternetAddress dest) throws MessagingException {

		Message message= new MimeMessage(session);

		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, dest);

		message.setSubject(subject);
		message.setContent(content, "text/html");

		Transport.send(message);
	}

	private List getAddress(HttpServletRequest request) throws AddressException{
		List address = new ArrayList();
		address.add(new InternetAddress(getParameter(request, "to")));
		return address;
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if ( StringUtils.isEmpty(getParameter(request, "action")) )
				throw new InvalidRequestException("Error, request without action!!");

			Properties prop= new Properties();
			prop.put("mail.smtp.host", "smtp.relato.com.br");

			Session session = Session.getInstance(prop);

			List address = getAddress(request);
			Iterator i = address.iterator();
			while ( i.hasNext() ){
				envia(getParameter(request, "from"),
						getParameter(request, "subject"),
						getParameter(request, "content"),
						session,
						(InternetAddress)i.next());
			}
		} catch (MessagingException e) {
			throw new InvalidRequestException("Algum erro aconteceu ao enviar o email!!");
		}
	}
}