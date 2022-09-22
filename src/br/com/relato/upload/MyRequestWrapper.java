/*
 * Created on 28/06/2004
 *
 */
package br.com.relato.upload;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Administrador
 *
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {
	private ServletInputStream in;
	private static final int LIMITE = 15000000;

	static class MyServletInputStream extends ServletInputStream {
		
		private ServletInputStream in;
		private int trans;
		public int read() throws IOException {
			if(trans > LIMITE)
				throw new IOException("Limite excedido. Arquivo é muito grande para ser enviado!");
			++trans;
			return in.read();
		}
		public MyServletInputStream(ServletInputStream in) {
			this.in = in;
		}
	}

	public MyRequestWrapper(HttpServletRequest arg0) {
		super(arg0);
	}


	public ServletInputStream getInputStream() throws IOException {
		if(in == null)
			in = new MyServletInputStream(super.getInputStream());
		return in;
	}

}
