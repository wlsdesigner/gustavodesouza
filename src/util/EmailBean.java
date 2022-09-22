package  util;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EmailBean
{
	private Session session;
	private ArrayList to;
	private ArrayList cc;
	private InternetAddress from;
	
	public EmailBean( String toAddress, String ccAddress ) throws NamingException, AddressException
	{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		session = (Session) envCtx.lookup("mail/Session");
		to = new ArrayList();
		cc = new ArrayList();		
		to.add(new InternetAddress(toAddress));
		cc.add(new InternetAddress(ccAddress));	
		from =  new InternetAddress("cpd@cavalomangalarga.com.br");
	}
	
	public void envia(String subject, String content) throws MessagingException
	{
		InternetAddress[] dests = (InternetAddress[])to.toArray(new InternetAddress[to.size()]);
		InternetAddress[] ccs = (InternetAddress[])cc.toArray(new InternetAddress[cc.size()]);
		
		Message message = new MimeMessage(session);
		message.setFrom(from);
		message.setRecipients(Message.RecipientType.TO, dests);
		message.setRecipients(Message.RecipientType.CC, ccs);

		message.setSubject(subject);
		//message.setContent(content, "text/plain");
		message.setContent(content, "text/html");
		
		Transport.send(message);
	}
}