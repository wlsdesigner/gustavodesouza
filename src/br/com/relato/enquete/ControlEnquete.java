/*
 * Created on 25/11/2004
 */
package br.com.relato.enquete;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.relato.pool.PoolFilter;

import br.com.relato.ControlServlet;
import br.com.relato.InvalidRequestException;
import br.com.relato.util.SqlHelper;

/**
 * @author daniel
 */
public class ControlEnquete extends ControlServlet{
	public ControlEnquete(){
		setErrorPage("/enquete/gerenciadorenquete.jsp");
	}

	final String QUERY_INATIVO = " update enquete set opsativo = 'I' ";
	final String QUERY_ATIVO = " select idienquete from enquete where opsativo = 'A' ";
	final String QUERY_DEL_ENQUETE = " delete from enquete where idienquete = ?";
	final String QUERY_DEL_ENQUETE_VALOR = " delete from enquetevalor where idienquete = ?";
	
	
	final String QUERY_UPDATE_ENQUETE = " update enquete set cdspergunta = ?, " +
										" cdspergunta2 = ?," +
										" cdspergunta3 = ?," +
										" cdspergunta4 = ?," +
										" cdinumeroresposta = ?,  " +
										" opsativo = ? " +
									 	" where idienquete = ? ";
	
	final String QUERY_UPDATE_ENQUETE_VALOR = 	" update enquetevalor set " +
												" idienquete = ?, " +
												" resposta = ? ," +
												" resposta2 = ?, " +
												" resposta3 = ?, " +
												" resposta4 = ? " +
												" where idienquetevalor = ?";
	
	final String QUERY_INSERT_ENQUETE = " insert into enquete set " +
										" cdspergunta = ?, " +
										" cdspergunta2 = ?, " +
										" cdspergunta3 = ?, " +
										" cdspergunta4 = ?," +
										" cdinumeroresposta = ?,  " +
										" opsativo = ? ";
	
	final String QUERY_INSERT_ENQUETE_VALOR = 	" insert into enquetevalor set " +
												" idienquete = ?, " +
												" resposta = ?, " +
												" resposta2 = ?, " +
												" resposta3 = ?, " +
												" resposta4 = ?, " +
												" valor = 0";


	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = getParameter(request, "action");
		PreparedStatement pstmt = null;
		try{
			doInit(request, pstmt);

			if ( "add".equals(action) )
				add(request, pstmt);
			else if ( "modify".equals(action) )
				modify(request, pstmt);
			else if ( "delete".equals(action) )
				delete(request, pstmt);

			checkFinish(request, response);

		}catch(Exception e){
			e.printStackTrace();
			throw new InvalidRequestException("Algum erro aconteceu no processamento!!");
		}finally{
			if ( pstmt != null )
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
	}

	private void checkFinish(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		if ( SqlHelper.execute(QUERY_ATIVO, null).size() == 0 )
			response.sendRedirect("/enquete/gerenciadorenquete.jsp?msg=Nenhuma enquete esta ATIVA!!");
		else
			response.sendRedirect("/enquete/gerenciadorenquete.jsp");
	}

	private void doInit(HttpServletRequest request, PreparedStatement pstmt) throws SQLException {
		if ( "A".equals(getParameter(request, "ativa")) ){
			pstmt = getPrepare(QUERY_INATIVO);
			pstmt.execute();
		}
	}

	private PreparedStatement getPrepare(String query) throws SQLException {
		return PoolFilter.getConnection().prepareStatement(query);
	}

	private void delete(HttpServletRequest request, PreparedStatement pstmt) throws SQLException {
		pstmt = getPrepare(QUERY_DEL_ENQUETE);
		pstmt.setString(1, getParameter(request, "id"));
		pstmt.execute();

		pstmt = getPrepare(QUERY_DEL_ENQUETE_VALOR);
		pstmt.setString(1, getParameter(request, "id"));
		pstmt.execute();
	}

	private void modify(HttpServletRequest request, PreparedStatement pstmt) throws SQLException {
		String[] idiresp = getParameter(request, "idresposta").split("/");;

		pstmt = getPrepare(QUERY_UPDATE_ENQUETE);
		pstmt.setString(1, getParameter(request, "pergunta1"));
		pstmt.setString(2, getParameter(request, "pergunta2"));
		pstmt.setString(3, getParameter(request, "pergunta3"));
		pstmt.setString(4, getParameter(request, "pergunta4"));
		pstmt.setInt(5, Integer.parseInt(getParameter(request, "respostas")));
		pstmt.setString(6, getParameter(request, "ativa"));
		pstmt.setString(7, getParameter(request, "id"));
		pstmt.execute();

		pstmt = getPrepare(QUERY_UPDATE_ENQUETE_VALOR);
		for ( int i = 0; i < idiresp.length; i++ ){
			pstmt.setString(1, getParameter(request, "id"));
			pstmt.setString(2, checkNull(getParameter(request, "r1x" + (i + 1))));
			pstmt.setString(3, checkNull(getParameter(request, "r2x" + (i + 1))));
			pstmt.setString(4, checkNull(getParameter(request, "r3x" + (i + 1))));
			pstmt.setString(5, checkNull(getParameter(request, "r4x" + (i + 1))));
			pstmt.setString(6, idiresp[i]);
			pstmt.execute();
		}
	}

	private void add(HttpServletRequest request, PreparedStatement pstmt) throws SQLException {
		int respostas = Integer.parseInt(getParameter(request, "respostas"));
		pstmt = getPrepare(QUERY_INSERT_ENQUETE);
		pstmt.setString(1, getParameter(request, "pergunta"));
		pstmt.setString(2, getParameter(request, "pergunta2"));
		pstmt.setString(3, getParameter(request, "pergunta3"));
		pstmt.setString(4, getParameter(request, "pergunta4"));
		pstmt.setInt(5, respostas);
		pstmt.setString(6, getParameter(request, "ativa"));
		pstmt.execute();

		pstmt = getPrepare(QUERY_INSERT_ENQUETE_VALOR);
		for ( int i = 1; i <= respostas; i++ ){
			pstmt.setString(1, (String)SqlHelper.execute("select max(idienquete) from enquete", null).get(0));
			pstmt.setString(2, checkNull(request.getParameter("r1x" + i)));
			pstmt.setString(3, checkNull(request.getParameter("r2x" + i)));
			pstmt.setString(4, checkNull(request.getParameter("r3x" + i)));
			pstmt.setString(5, checkNull(request.getParameter("r4x" + i)));
			pstmt.execute();
		}
	}

	String checkNull(String valor){
		if ( StringUtils.isEmpty(valor) )
			return "";
		return valor;
	}
}
