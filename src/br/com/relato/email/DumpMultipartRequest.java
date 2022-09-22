/*
 * Created on 19/01/2005
 */
package br.com.relato.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daniel
 */
public class DumpMultipartRequest {

	public static Dictionary doUpload(HttpServletRequest request,
            HttpServletResponse response)throws IOException, MessagingException{
		
		String boundary = request.getHeader("Content-Type");
		int pos = boundary.indexOf('=');
		boundary = boundary.substring(pos + 1);
		boundary = "--" + boundary;
		ServletInputStream in = request.getInputStream();
		byte[] bytes = new byte[512];
		int state = 0;
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		String name = null, value = null, filename = null, contentType = null;
		Dictionary fields = new Hashtable();
	
		int i = in.readLine(bytes,0,512);
		while(-1 != i){
			String st = new String(bytes,0,i);
			if(st.startsWith(boundary)){
			state = 0;
			if(null != name){
				if(value != null)
					fields.put(name, value.substring(0, value.length() - 2));
				else if(buffer.size() > 2){
					InternetHeaders headers = new InternetHeaders();
					BodyPart bodyPart = new MimeBodyPart();
					DataSource ds = new ByteArrayDataSource( buffer.toByteArray(), 
							contentType, filename);
					bodyPart.setDataHandler( new DataHandler(ds) );
					bodyPart.setDisposition( "attachment; filename=\"" +
								filename + "\"");
					bodyPart.setFileName(filename);
					fields.put(name,bodyPart);
				}
	
				name = null;
				value = null;
				filename = null;
				contentType = null;
				buffer = new ByteArrayOutputStream();
				}
			}
		    else if(st.startsWith(
            "Content-Disposition: form-data") &&
            state == 0)
         {
            StringTokenizer tokenizer =
               new StringTokenizer(st,";=\"");
            while(tokenizer.hasMoreTokens())
            {
               String token = tokenizer.nextToken();
               if(token.startsWith(" name"))
               {
                  name = tokenizer.nextToken();
                  state = 2;
               }
               else if(token.startsWith(" filename"))
               {
                  filename = tokenizer.nextToken();
                  StringTokenizer ftokenizer =
                     new StringTokenizer(filename,"\\/:");
                  filename = ftokenizer.nextToken();
                  while(ftokenizer.hasMoreTokens())
                     filename = ftokenizer.nextToken();
                  state = 1;
                  break;
               }
            }
         }
         else if(st.startsWith("Content-Type") &&
                 state == 1)
         {
            pos = st.indexOf(":");
            // + 2 to remove the space
            // - 2 to remove CR/LF
            contentType =
               st.substring(pos + 2,st.length() - 2);
         }
         else if(st.equals("\r\n") && state == 1)
            state = 3;
         else if(st.equals("\r\n") && state == 2)
            state = 4;
         else if(state == 4)
            value = value == null ? st : value + st;
         else if(state == 3)
            buffer.write(bytes,0,i);
         i = in.readLine(bytes,0,512);
      }
		
		return fields;
	}
}
