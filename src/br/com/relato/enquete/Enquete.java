package br.com.relato.enquete;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Pie3DPlot;
import org.jfree.data.JDBCPieDataset;

import com.relato.pool.PoolFilter;

/**
 *
 * @author  Daniel
 * @version
 */
public class Enquete extends HttpServlet{
	final String QUERY_DEFAULT = "select resposta, valor " +
		" from enquetevalor, enquete " +
		" where " +
		" enquetevalor.idienquete = enquete.idienquete " +
		" and opsativo = 'A'";
	
	String getQuery(String idienquete){
		return "select resposta, valor " +
			" from enquetevalor, enquete " +
			" where " +
			" enquetevalor.idienquete = enquete.idienquete " +
			" and enquete.idienquete = " + idienquete;	
	}
	
	public JFreeChart crearChart(String query) throws SQLException{	
		Connection con = PoolFilter.getConnection();
		JDBCPieDataset dataset = new JDBCPieDataset(con, query);
				
		JFreeChart chart = ChartFactory.createPie3DChart(
			"",  // chart title
			dataset,                // data
			true,                   // include legend
			true,
			false
		);
		
		chart.setBackgroundPaint(Color.white);
		Pie3DPlot plot = (Pie3DPlot) chart.getPlot();
		plot.setStartAngle(270);
		NumberFormat f = (NumberFormat) NumberFormat.getPercentInstance().clone();
		f.setMinimumFractionDigits(1);
		f.setMaximumFractionDigits(2);
		plot.setPercentFormat(f);
		plot.setSectionLabelType(Pie3DPlot.NAME_AND_PERCENT_LABELS);
		plot.setForegroundAlpha(0.5f);
		
		plot.setRadius(0.70);
		plot.setNoDataMessage("No data to display");
		
		return chart;
	}
    
    
	int getParamEntero(HttpServletRequest request,String pNombre, int pDefecto){
		String param = request.getParameter(pNombre);
		if (param == null || param.compareTo("") == 0)
			return pDefecto;

		return Integer.parseInt(param);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("image/jpeg");
						
		OutputStream saida = response.getOutputStream();
		JFreeChart grafica = null;
		try{
			if ( StringUtils.isEmpty(request.getParameter("idienquete")) )
				grafica = crearChart(QUERY_DEFAULT);
			else
				grafica = crearChart(getQuery(request.getParameter("idienquete")));
		}catch(SQLException e){
			e.printStackTrace();
		}

		int largura = getParamEntero(request,"largura",500);
		int altura = getParamEntero(request,"altura",300);
        
		ChartUtilities.writeChartAsJPEG(saida,grafica,largura,altura);
     
		saida.close();
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}
}