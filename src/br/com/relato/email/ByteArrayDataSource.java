/*
 * Created on 19/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.relato.email;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource{
byte[] bytes;
String contentType, name;

public ByteArrayDataSource(byte[] bytes,
                    String contentType,
                    String name){
   this.bytes = bytes;
   if(contentType == null)
      this.contentType = "application/octet-stream";
   else
      this.contentType = contentType;
   this.name = name;
}

public String getContentType(){
   return contentType;
}

public InputStream getInputStream(){
   // remove the final CR/LF
   return new ByteArrayInputStream(
      bytes,0,bytes.length - 2);
}

public String getName(){
   return name;
}

public OutputStream getOutputStream()
   throws IOException{
   throw new FileNotFoundException();
}
}