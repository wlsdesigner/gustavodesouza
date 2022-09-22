package br.com.relato.xls.servlet;

import java.io.*; 

import javax.servlet.*; 
import javax.servlet.http.*; 
import java.util.zip.*; 
import java.util.Date; 
import com.hlt.util.Formatos;
import com.hlt.sql.Query;
import br.com.neorelato.sql.Table;

import java.util.Locale ;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import com.hlt.util.Formatos;
import br.com.relato.util.SqlHelper;
import br.com.relato.extranet.prospects.InsertRecords;

public class XlsServlet extends HttpServlet { 

	private String opcao = "";
	private String resposta = "";
	
	protected void doGet (HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException  { 
		
		this.opcao = (null != req.getParameter("opcao") ? req.getParameter("opcao") : "" );
		if(!"".equals(opcao)){
			if(Option.INCLUIREXCEL.equals(opcao)){
				String filepath = (null != req.getParameter("path") ? req.getParameter("path") : "" );
				//System.out.println("DOGET Tentando inserir records no path === "+filepath);
				InsertRecords pros = new InsertRecords(filepath);
				//System.out.println("DOGET dpois de inserir records no path === "+filepath);
			}
		}
		

	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		this.opcao = (null != req.getParameter("opcao") ? req.getParameter("opcao") : "" );
		this.resposta = (null != req.getParameter("resposta") ? req.getParameter("resposta") : "" );
		if(!"".equals(opcao)){
			if(Option.INCLUIREXCEL.equals(opcao)){
				String filepath = (null != req.getParameter("path") ? req.getParameter("path") : "" );
				System.out.println("DOPOST Tentando inserir records no path === "+filepath);
				InsertRecords pros = new InsertRecords(filepath);
				System.out.println("DOPOST dpois de inserir records no path === "+filepath);
				if(!"".equals(resposta)){
					System.out.println("Response === "+resposta);

					res.sendRedirect(resposta);
					
					System.out.println("Fui === "+resposta);
				}
			}
		}

	}
       
}

