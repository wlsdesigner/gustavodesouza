package br.com.relato.email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.*;

import org.apache.commons.lang.StringUtils;

import br.com.relato.criteria.SearchCriteria;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.relato.ControlServlet;
import br.com.relato.InvalidRequestException;

import br.com.relato.EntryPoint;

import br.com.relato.ConstantsApp;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;

public class EnviaEmailBean extends ControlServlet{ 

	private static final long serialVersionUID = 1L;

	public EnviaEmailBean(){
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

		// substitui o e-mail
		final String procurar = "<%=email%>";
	 
		Pattern p = Pattern.compile(procurar);
	 	Matcher m = p.matcher(content);
	 	content = m.replaceAll(dest.getAddress());

		message.setSubject(subject);
		
		message.setContent(content, "text/html");

		Transport.send(message);

	}

	public void envia(String from,
			String subject,
			String content,
			Session session,
			InternetAddress dest,String file) throws MessagingException {

		Message message= new MimeMessage(session);

		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, dest);

		// substitui o e-mail
		final String procurar = "<%=email%>";
	 
		Pattern p = Pattern.compile(procurar);
	 	Matcher m = p.matcher(content);
	 	content = m.replaceAll(dest.getAddress());
	 	
		message.setSubject(subject);

		BodyPart messageBodyPart = new MimeBodyPart();
		
		//System.out.println("CONTEUDO BEFORE\n\n"+content);
		
		//Replace the keyword _I_M_G_ with the img tag
		content = content.replaceAll("_I_M_G_","<img src=\"cid:EEEI01\">");
		
		//System.out.println("\n\n\nCONTEUDO AFTER\n\n"+content);
		
		messageBodyPart.setContent(content, "text/html");
		
		//Create a related multi-part to combine the parts
		MimeMultipart multipart = new MimeMultipart("related");
		
		// Add body part to multipart
		multipart.addBodyPart(messageBodyPart);
		
		// Create part for the image
		messageBodyPart = new MimeBodyPart();
		
		// Fetch the image and associate to part
		DataSource fds = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(fds));
		
		// Add a header to connect to the HTML
		messageBodyPart.setHeader("Content-ID","<EEEI01>");
		
		// Add part to multi-part
		multipart.addBodyPart(messageBodyPart);
		
		// Associate multi-part with message
		message.setContent(multipart);

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
			prop.put("mail.smtp.host", "smtp.terra.com.br");

			Session session = Session.getInstance(prop);

			String actionToDo = getParameter(request, "action");
			if("test".equalsIgnoreCase(actionToDo)) {
				List address = getAddress(request);
				
				String dir_path = ConstantsApp.getParametro("email.documentos");
				String arquivo = (null != getParameter(request, "filename") ? getParameter(request, "filename").trim() : "" );
				//System.out.println("ARQUIVO ==== "+arquivo);
				if ( !StringUtils.isEmpty(arquivo) ) {
					arquivo = dir_path.trim()+arquivo.trim();
					System.out.println("ENVIANDO EMAIL TESTE COM ARQUIVO === "+arquivo);
				}
				Iterator i = address.iterator();
				if("".equals(arquivo)){
					while ( i.hasNext() ){
						envia(getParameter(request, "from"),
								getParameter(request, "subject"),
								getParameter(request, "content"),
								session,
								(InternetAddress)i.next());
					}
				}else{
					while ( i.hasNext() ){
						envia(getParameter(request, "from"),
								getParameter(request, "subject"),
								getParameter(request, "content"),
								session,
								(InternetAddress)i.next(),arquivo);
					}
				}
			} else
				processEmail(request, response, session);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void processEmail(HttpServletRequest request, HttpServletResponse response, Session session) throws InterruptedException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addField("distinct(cdsemail)");
		criteria.addTable("prospect");
		criteria.addFreeExpression("cdsemail is not null and cdsemail != '' and cdsemail like '%@%' and upper(opsativo) = 'S'");
		
		Connection con = getConnection();
		
		try{
			PrintWriter pw = response.getWriter(); // pega o 'escritor' da resposta
			pw.println("<HTML>");
			pw.println("<TITLE>Enviando e-mails</TITLE>");
			pw.println("<BODY>");
			
	
			ResultSet rs = execute(criteria.getQuery(), con);
			try {
				if ( rs.next() ){
					String dir_path = ConstantsApp.getParametro("email.documentos");
					String arquivo = (null != getParameter(request, "filename") ? getParameter(request, "filename").trim() : "" );
					//System.out.println("ARQUIVO ==== "+arquivo);
					if ( !StringUtils.isEmpty(arquivo) ) {
						arquivo = dir_path.trim()+arquivo.trim();
						System.out.println("ENVIANDO EMAIL COM ARQUIVO ==== "+arquivo);
					}
					
					do{
						String email = rs.getString(1);
						try{
							if ( !StringUtils.isEmpty(email) ) {
								if("".equals(arquivo)){
									envia(getParameter(request, "from"),
											getParameter(request, "subject"),
											getParameter(request, "content"),
											session,
											new InternetAddress(email));
									pw.println(email+" OK<BR>");
									Thread.sleep(1000);
								}else{
									envia(getParameter(request, "from"),
											getParameter(request, "subject"),
											getParameter(request, "content"),
											session,
											new InternetAddress(email),arquivo);
									pw.println(email+" OK<BR>");
									Thread.sleep(1000);
								}
							}
						}catch(AddressException e){
							System.out.println("-------- Erro ao inserir email: " + email);
						
						}catch(SendFailedException e){
							System.out.println("-------- Falhou ao enviar email: " + email);
						}
					}while( rs.next() );
				} else {
					System.out.println("-------- ResultSet nao foi aberto: " + criteria.getQuery());
				}
			} finally {	
				pw.println("</BODY>");
				pw.println("</HTML>");				
				//closeConnection(con);
			}
		}catch(SQLException e){
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	ResultSet execute(String query, Connection con ){
		if ( con != null ){
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	Connection getConnection(){
		try {
			/*Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql:///siteap", 
					"root", "netsucesso");
					*/
			Connection con = EntryPoint.getConnection();
			return con;
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/ catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void closeConnection(Connection con){
		if( con != null )
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}