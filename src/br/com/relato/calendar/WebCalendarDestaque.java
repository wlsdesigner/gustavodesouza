package br.com.relato.calendar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.relato.EntryPoint;
import br.com.relato.util.Cast;
import br.com.relato.portal.GetNovidade;

public class WebCalendarDestaque extends HttpServlet {

	// these parameters may be changed to suit the owner/user:
  protected boolean viewOnly = false; // true to disallow changes (for public viewing only); false to allow users to add/modify/delete events
  protected String secretPassPhrase = "KilroyWasHere";
  protected String authMessage = "Please see the owner of this calendar to get the secret phrase.";
  protected int cookieMaxAge = 2; // sets lifetime of cookie in weeks
  protected String titleBar = "Calendário "; // label shown on the top bar of the Calendar
  protected String cTFontsColor = "#FFFFFF";  // yellow
  protected String cTopBarColor = "#3163A0";  // dark blue
  protected String cDayBarColor = "#3163A0";  // dark blue
  protected String cOutDayColor = "#DBE9FD";  // light blue
  protected String cTodaysColor = "#E8E8E8";  // light gray
  protected String cBGCellColor = "#FFFFFF";  // white
  protected String cDtFontColor = "#003366";  // black
  protected SimpleDateFormat dfTimeField = new SimpleDateFormat("HH:mm");   // or try "h:mma" for AM/PM format
  protected SimpleDateFormat dfDateField = new SimpleDateFormat("dd/MM/yyyy");  // or try "d-M-yyyy" or "dMMMyyyy"  
  protected String time0000 = dfTimeField.format(new java.util.Date(25200000));  // 00:00 (beginning of day in local time format)
  protected String time2359 = dfTimeField.format(new java.util.Date(111540000)); // 23:59 (end of day in local time format)
  Locale locale = new Locale("pt", "BR");
  protected SimpleDateFormat dfMonthYear = new SimpleDateFormat("MMMM yyyy", locale);  // used for the title bar
  protected SimpleDateFormat dfMonthName = new SimpleDateFormat("MMMM", locale);       // used for next/last month navigation

  // these parameters should not be changed, or things may break:
  String thisServletURI = null;
  protected String mySqlJdbcDriver = "org.gjt.mm.mysql.Driver"; // location of jdbc driver in the classpath
  protected SimpleDateFormat dfMySQLDate = new SimpleDateFormat("yyyy-MM-dd"); // matches format used by MySQL database
  protected SimpleDateFormat dfMySQLTime = new SimpleDateFormat("HH:mm");       //format used by MySQL database
  protected SimpleDateFormat dfDayOfMonth = new SimpleDateFormat("d");         // used for writing date numerals in the calendar
  boolean tem = false;
//*******************
//default entry point
//*******************

  public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    thisServletURI = request.getServletPath();

    Calendar thisMonth = Calendar.getInstance();
    java.util.Date thisDate = new java.util.Date();

    // look for an input date request (default = today)
    if (request.getParameter("date") != null) {
      try {
        thisDate = dfDateField.parse(request.getParameter("date"));
        thisMonth.setTime(thisDate);
      } catch (ParseException e) {
        out.println("Unable to parse date parameter. Using date=" + dfDateField.format(thisMonth.getTime()) + "<br>");
      }
    }

    // set the calendar to the first day of the month
    thisMonth.set(Calendar.DAY_OF_MONTH,1);

    // select the correct type of output and send it:
    out.println(htmlHeader());

    if (request.getParameter("form") == null) {
      out.println(tableHeader(thisMonth));
      out.println(tableCells(thisMonth));
      out.println(tableFooter(thisMonth));
    }
    else if (request.getParameter("form").equals("help")) {
      out.println(instructions());
    }
    else if (viewOnly) {
      out.println(declineRequest());
    }
    else if(allowEdit(request,response)) {
      if (request.getParameter("form").equals("new")) {
        out.println(newEventForm(request.getParameter("value")));
      }
      else if (request.getParameter("form").equals("revise")) {
        out.println(reviseEventForm(request.getParameter("value")));
      }
    }
    out.println(htmlFooter());
    return;
  }

  String htmlHeader() {
    return "<HTML>"
     + "<head>"
     + "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"
     + "<title>Calendário</title>"
     + "<SCRIPT LANGUAGE=JavaScript>"
	 + " function PopupWindow(url,form,value){"
	 + " 	window.open(url+'?form='+form+'&value='+value,'EventControlPanel',"
	 + " 		'width=520,height=380,dependent,resizable');"
	 + " }"
	 + " "
	 + " function checkNull(value){"
	 + " 	if ( value != \"\" && value.length != 0 )"
	 + " 	  return false;"
	 + " 	return true;"
	 + " }"
	 + " "
	 + " function checkForm(){"
	 + " 	if ( checkNull(document.getElementById(\"novidade\").value) ){"
	 + " 		alert('Mensagem vazia!');"
	 + " 		return false;"
	 + " 	}"
	 + " "
	 + " return true;"
	 + " }"
	+"</script>"
	 + "<style type=\"text/css\">"
	 +"* {font-family: Tahoma, Verdana, Arial;}"	 
	 +"input.botao1 {	border: 1px solid white;"
		+"background-color: #003366;	color: white;"
		+"	font-weight: bolder;"
		+"	font-family: Verdana, Tahoma, Arial, sans-serif;"
		+"	font-size: 10px;"
		+"	height: 20px;"
		+"	width: 100px;"
		+"	text-align: center;"	
		+"	}"
		+"input.botaoover1 {"
		+"	border: 1px solid #003366;"	
		+"	background-color: white;"	
		+"	color: #003366;"
		+"	font-weight: bolder;"
		+"	font-family: Verdana, Tahoma, Arial, sans-serif;"
		+"	font-size: 10px;"
		+"	height: 20px;"
		+"	width: 100px;"
		+"	text-align: center;"
		+"	cursor: pointer;"
		+"	}"
		+"input.botao { border: 1px solid white; background-color: #003366; color: white; font-weight: bolder; font-family: Tahoma, Verdana, Arial, sans-serif; font-size: 10px; width: 80px; text-align: center; } "
		+"input.botaoover { border: 1px solid #003366; background-color: white; color: #003366; font-weight: bolder; font-family: Tahoma, Verdana, Arial, sans-serif; font-size: 10px; width: 80px; text-align: center; cursor: pointer; }" 
		+".borda{ padding: 3px; border: 1px solid #3163A0;}"
		+"td {padding: 5px; font-size: 12px;}"
		+".naover {"
		+"	display: none;"
		+"	border: none; "
		+"	color: white; "
		+"	background-color: #3163A0;"
		+"	text-align: left;"
		+"	padding: 3px;"
		+"	width: 480;"
		+"	}"
		+".ver {"
		+"	display: inline;" 
		+"	border: none;" 
		+"	color: white; "
		+"	background-color: #3163A0;"
		+"	text-align: left;"
		+"	padding: 3px;"
		+"	width: 480;"
		+"	}"
	 + "</style>"
     + "</head>";
  }

  String instructions() {
    StringBuffer rv = new StringBuffer("<body bgcolor=" + cTopBarColor + " style=\"color: " + cTFontsColor + "; font-size: 12px; border: 6px solid "+ cDtFontColor +";\">");
    rv.append("<h3>Calendário de Novidades</h3>"
     + "<FONT SIZE=-1 font-face=tahoma><OL><LI>Para cadastrar uma novidade em um dia, clique no link do dia que está localizado " +
     "no canto superior esquerdo."
     + "<LI>Digite a mensagem. "
     + "<LI>E clique em 'Inserir'."
     + "</OL><HR style=\"border: 1px solid "+ cTFontsColor +";\">"
     + "<a style=\"color: " + cDtFontColor + ";\" href='http://www.relato.com.br' onClick=javascript:opener.document.location='http://www.relato.com.br';window.close();>Relato</a><BR>"
     + "</FONT>");
    return rv.toString();
  }

  String declineRequest() {
  StringBuffer rv = new StringBuffer("<body>");
  rv.append("<h3>Desculpe!</h3>"
   + "Você não está autorizado a alterar o calendário!!");
   rv.append("<FORM><INPUT TYPE=BUTTON VALUE='  OK  ' onClick=javascript:window.close();></FORM>");   
   return rv.toString();
  }

  String tableHeader(Calendar thisMonth) {
    Calendar nextMonth = (Calendar)thisMonth.clone(); nextMonth.add(Calendar.MONTH,+1);
    Calendar lastMonth = (Calendar)thisMonth.clone(); lastMonth.add(Calendar.MONTH,-1);
    return "<body style=\"font-family: Tahoma, Verdana, Arial\" BGCOLOR=" + cDtFontColor + ">"
     
    + "<TABLE CELLSPACING=0 CELLPADDING=0 WIDTH=100% style=\"border: 1px solid white; border-collapse: collapse;\">"
    + "  <TR>"
    + "		<TD class=\"borda\" BGCOLOR=" + cTopBarColor + " ALIGN=CENTER COLSPAN=7>"
    + "			<TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0>"
    + "				<TR ALIGN=CENTER>"
    + "					<TD class=\"borda\"><A HREF=" + thisServletURI + "?date=" + dfDateField.format(lastMonth.getTime()) + "><FONT SIZE=-1 COLOR=" + cTFontsColor + "><&#151;&nbsp;" + dfMonthName.format(lastMonth.getTime()) + "</FONT></A></TD>"
    + "					<TD class=\"borda\"><FONT SIZE=+1 COLOR=" + cTFontsColor + "><B>" + titleBar + dfMonthYear.format(thisMonth.getTime()) + "</B></FONT></TD>"
    + "					<TD class=\"borda\"><A HREF=" + thisServletURI + "?date=" + dfDateField.format(nextMonth.getTime()) + "><FONT SIZE=-1 COLOR=" + cTFontsColor + ">" + dfMonthName.format(nextMonth.getTime()) + "&nbsp;&#151;></FONT></A></TD>"
    + "				</TR>"
    + "			</TABLE>"
    + "		</TD>"
    + "	</TR>"
   	 + "  <TR ALIGN=CENTER BGCOLOR=" + cDayBarColor + ">"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Domingo</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Segunda</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Ter&ccedil;a</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Quarta</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Quinta</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>Sexta</B></FONT></TD>"
     + "  <TD WIDTH=14% class=\"borda\"><FONT COLOR=" + cTFontsColor + "><B>S&aacute;bado</B></FONT></TD>"
     + "  </TR>";
  }

  String tableCells(Calendar thisMonth) {
    StringBuffer returnValue = new StringBuffer();

    // record the current value of the field Calendar.MONTH
    int iThisMonth = thisMonth.get(Calendar.MONTH);
    boolean firstCell = true;

    Calendar today = Calendar.getInstance(); //used to highlight today's date cell in the calendar
    Calendar endOfMonth = (Calendar)thisMonth.clone();
    endOfMonth.set(Calendar.DATE,endOfMonth.getActualMaximum(Calendar.DATE));
    endOfMonth.add(Calendar.DATE,7-endOfMonth.get(Calendar.DAY_OF_WEEK));

    // set calendar to first day of the first week (may move back 1 month)
    thisMonth.add(Calendar.DATE,1-thisMonth.get(Calendar.DAY_OF_WEEK));

    // get a recordset of this month's events, including extra days at beginning and end
    String startDate = dfMySQLDate.format(thisMonth.getTime());
    String endDate = dfMySQLDate.format(endOfMonth.getTime());
    ResultSet rsEvents = null;

    try {
      Connection conn = EntryPoint.getConnection();
      Statement stmt = conn.createStatement();

      // select only events for this calendar display:
      String sqlQueryString = "SELECT * FROM novidade "
       + "WHERE TO_DAYS(Sdate) >= TO_DAYS('" + startDate + "')"
       + "  AND TO_DAYS(Sdate) <= TO_DAYS('" + endDate + "')"
       + " ORDER BY Sdate";
      rsEvents = stmt.executeQuery(sqlQueryString);
      if (!rsEvents.next()) rsEvents = null;     // no events in the whole month's display
    } catch(Exception e) {
      return e.getMessage();
    }

    do {
      returnValue.append("<TR BORDER=1 VALIGN=TOP>");
      for (int i=0; i < 7; i++) {
        returnValue.append("<TD class=\"borda\" HEIGHT=100 WIDTH=\"14%\"");
        if (thisMonth.get(Calendar.MONTH) != iThisMonth) returnValue.append(" BGCOLOR=" + cOutDayColor);  // dark background for out-of-month days
        else if (dfDateField.format(thisMonth.getTime()).equals(dfDateField.format(today.getTime()))) returnValue.append(" BGCOLOR=" + cTodaysColor); // grey background for today's date
        else returnValue.append(" BGCOLOR=" + cBGCellColor);  // default white background
        
        returnValue.append(">");
        returnValue.append(todaysEvents(rsEvents,dfMySQLDate.format(thisMonth.getTime()),dfDayOfMonth.format(thisMonth.getTime()))); // write out the cell contents
        
        if ( !tem )
        	returnValue.append("<A HREF=# onClick=javascript:PopupWindow('" + thisServletURI + "','new','" + dfDateField.format(thisMonth.getTime()) + "');><FONT COLOR=" + cDtFontColor + " SIZE=-1><B>" + dfDayOfMonth.format(thisMonth.getTime()) + "</B></FONT></A>");
        tem = false;
        if (firstCell) {
            returnValue.append("<CENTER><FORM><INPUT TYPE=BUTTON VALUE='Ajuda' class=\"botao\" onMouseOver=\"this.className='botaoover';\" onMouseOut=\"this.className='botao';\" onClick=javascript:PopupWindow('" + thisServletURI + "','help','yes');></FORM></CENTER>");
          firstCell = false;
        }
        returnValue.append("</TD>");
        thisMonth.add(Calendar.DATE,1);
      }
      returnValue.append("</TR>");
    } while(thisMonth.getTime().before(endOfMonth.getTime()));

    // restore date to first of this month:
    thisMonth.add(Calendar.MONTH,-1);
    thisMonth.set(Calendar.DATE,1);

    return returnValue.toString();
  }

  String todaysEvents(ResultSet rsEvents, String today, String dia) {
    try {
      if (rsEvents==null || rsEvents.isAfterLast()) return "";   // no more events this month or empty ResultSet
      StringBuffer returnValue = new StringBuffer("<font size=-2>");
      while (dfMySQLDate.format(rsEvents.getDate("Sdate")).equals(today)) {
        java.util.Date start = rsEvents.getTime("Sdate");
        String timeSpan = null;
        if (dfTimeField.format(start).equals(time0000) ) timeSpan = "TODAY:";
        else if (dfTimeField.format(start).equals(time0000) ) timeSpan = "ALL DAY:";
        else timeSpan = dfTimeField.format(rsEvents.getTime("Sdate"));
        
        returnValue.append("<A HREF=# onClick=javascript:PopupWindow('" + thisServletURI + "','revise','" + rsEvents.getString("EventID") + "');><FONT COLOR=" + cDtFontColor + " SIZE=-1><B>" + dia + "</B></FONT></A><br>");
        tem = true;
        if(rsEvents.getBoolean("Flagged")) returnValue.append("<FONT COLOR=#FF0000>"); // write Desription field in red if flagged
        returnValue.append(rsEvents.getString("Description"));
        if(rsEvents.getBoolean("Flagged")) returnValue.append("</FONT>"); // returns red font color to default
        if(!rsEvents.next()) return returnValue.append("</font>").toString(); // encountered end of events
      }
      return returnValue.append("</font>").toString();
    }
    catch (SQLException e) {
      return e.getMessage();
    }
  }

  String tableFooter(Calendar thisMonth) {
    Calendar nextMonth = (Calendar)thisMonth.clone(); nextMonth.add(Calendar.MONTH,+1);
    Calendar lastMonth = (Calendar)thisMonth.clone(); lastMonth.add(Calendar.MONTH,-1);

    return "<TR>"
     + "  <TD class=\"borda\" BGCOLOR=" + cTopBarColor + " ALIGN=CENTER COLSPAN=7>"
     + "    <TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0>"
     + "    <TR ALIGN=CENTER>"
     + "      <TD width=\"15%\" class=\"borda\"><A HREF=" + thisServletURI + "?date=" + dfDateField.format(lastMonth.getTime()) + "><FONT SIZE=-1 COLOR=" + cTFontsColor + "><&#151;&nbsp;" + dfMonthName.format(lastMonth.getTime()) + "</FONT></A></TD>"
     + "      <TD width=\"15%\" class=\"borda\"><A HREF=" + thisServletURI + "><FONT SIZE=-1 COLOR=" + cTFontsColor + "><B>Ir para o Mês Atual</B></FONT></A></TD>"
     + "      <TD width=\"15%\" class=\"borda\"><A HREF=" + thisServletURI + "?date=" + dfDateField.format(nextMonth.getTime()) + "><FONT SIZE=-1 COLOR=" + cTFontsColor + ">" + dfMonthName.format(nextMonth.getTime()) + "&nbsp;&#151;></FONT></A></TD>"
     + "      <TD align=\"right\" class=\"borda\"><INPUT TYPE=BUTTON VALUE='Menu' class=\"botao1\" onMouseOver=\"this.className='botaoover1';\" onMouseOut=\"this.className='botao1';\" onClick=\"window.location.href='/siteadmin/home.jsp'\"></TD>"
     + "    </TR>"
     + "    </TABLE>"
     + "  </TD>"  
     + "</TR>"
     + "<TR>"
     + "</TABLE>";
  }

  String htmlFooter() {
    return "</body></HTML>";
  }

  boolean allowEdit(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);

    return true;
  }

  String newEventForm(String date) {
    StringBuffer returnValue = new StringBuffer();

    // print blank popup form for entering a new event:
    returnValue.append("<body onLoad=window.focus() bgcolor=" + cDtFontColor + " style=\"color: " + cTFontsColor + "; font-size: 12px;\">"
    		+ "<form method=post action='" + thisServletURI + "' onSubmit=\"return checkForm()\">"
			+ "<input type=hidden name='UserRequest' value='New'>"
			+ "<table width=\"489\" BORDER=0 cellpadding=\"0\" CELLSPACING=0 bgcolor=" + cTopBarColor + " style=\"border: 1px solid " + cTFontsColor + "; border-collapse: collapse;\">"
			+ " <tr>"
			+ "		<td colspan=\"2\" style=\"padding: 10px; border-bottom: 1px solid white;\" align=\"center\">"
			+ "			<span style=\"font-size: 18px; font-face: Verdana\">Novidades</span>"
			+ "		</td>"
			+ "	</tr>"
			+ "	<tr>"
			+ "		<td width=\"135\" align=\"right\" style=\"padding-top: 10px;\">"
			+ "			Data:"
			+ "		</td>"
			+ "		<td width=\"352\" style=\"padding-top: 10px;\">"
			+ "			<input style=\"border: none; font-size: 12px; width: 100px; margin-left: 5px;\" TYPE=TEXT SIZE=8 NAME=EventDate VALUE=" + date + ">"
			+ "		</td>"
			+ "	</tr>"
			+ "	<tr>"
			+ "		<td valign=top align=\"right\">"
			+ "			Título:"
			+ "		</td>"
			+ "		<td>"
			+ "			<input id=\"titulo\" style=\"border: none; font-size: 12px; width: 200px; margin-left: 5px;\" type=\"text\" name=\"titulo\">"
			+ "		</td>"
			+ "	<tr>"
			+ "		<td colspan=2  align=\"center\">"
			+ "			<table width=\"223\" height=\"147\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
			+ "			  <tr>"
			+ "				<td height=\"147\" background=\"/img/bg_news.jpg\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
			+ "				<table width=\"140\" height=\"130\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">"
			+ "					<tr>"
			+ "					  <td height=\"100\" valign=\"top\" style=\"font-size: 12px; font-face: Verdana, Tahoma, Arial, Helvetica, sans-serif\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">"
			+ "						  <textarea id='novidade' name='novidade' style=\"text-align: center; height: 112px; width:187px ; background-color:#F7F7FF; overflow:hidden; font-size: 12px; font-face: Verdana; border: 0px\"></textarea>"
			+ "						</td>"
			+ "					</tr>"
			+ "				  </table></td>"
			+ "			  </tr>"
			+ "			</table>"
			+ "		</td>"
			+ "	<tr>"
			+ "	<tr>"
			+ "		<td colspan=2 align=\"center\">"
			+ "			&nbsp; <input class=\"botao\" type=\"submit\" name=\"submit\" value=\"Inserir\" onMouseOver=\"this.className='botaoover';\" onMouseOut=\"this.className='botao';\">"
			+ "		</td>"
			+ "	</tr>"
			+ "</table>"
			+ " "
			+ "</form>");

    return returnValue.toString();
  }

	static String conv(String str) {
		if ( str == null )
			return "";
			
		char[] data = str.toCharArray();
		for(int i = 0; i < data.length; ++i) {
			switch(data[i]) {
				case 'á': case 'à': case 'â': case 'ã': data[i] = 'a'; break;
				case 'Á': case 'À': case 'Â': case 'Ã': data[i] = 'A'; break;
				
				case 'é': case 'è': case 'ê': data[i] = 'e'; break;
				case 'É': case 'È': case 'Ê': data[i] = 'E'; break;

				case 'í': case 'ì': case 'î': data[i] = 'i'; break;
				case 'Í': case 'Ì': case 'Î': data[i] = 'I'; break;

				case 'ó': case 'ò': case 'ô': case 'õ': data[i] = 'o'; break;
				case 'Ó': case 'Ò': case 'Ô': case 'Õ': data[i] = 'O'; break;

				case 'ú': case 'ù': case 'û': case 'ü': data[i] = 'u'; break;
				case 'Ú': case 'Ù': case 'Û': case 'Ü': data[i] = 'U'; break;
				
				case 'ç': data[i] = 'c'; break;
				case 'Ç': data[i] = 'C'; break;
				
				case 'º': data[i] = 'o'; break;
				default:
					if(data[i] >= 127)
						data[i] = '?';
			}
		}
		return new String(data);
	}
  
  String reviseEventForm(String eventID) {
    String livro = null;
    String capitulo = null;
    String versiculo = null;

    List list = new ArrayList();
    try {
      list = GetNovidade.getAll(eventID);
      if ( list == null )
      	return "Nenhum registro encontrado!!";
    } catch (Exception e) {
      return e.getMessage();
    }
    List row = (List)list.get(0);

    StringBuffer returnValue = new StringBuffer();

    returnValue.append("<body onLoad=window.focus() bgcolor=" + cDtFontColor + " style=\"color: " + cTFontsColor + "; font-size: 12px;\">"
    		+ "<FORM METHOD=POST ACTION='" + thisServletURI + "' onSubmit=\"return checkForm()\">"
		    + "<INPUT TYPE=HIDDEN NAME='UserRequest' VALUE='Revise'>"
		    + "<INPUT TYPE=HIDDEN NAME='EventID' VALUE=" + eventID + ">"
			+ "<table width=\"489\" BORDER=0 cellpadding=\"0\" CELLSPACING=0 bgcolor=" + cTopBarColor + " style=\"border: 1px solid " + cTFontsColor + "; border-collapse: collapse;\">"
			+ " <tr>"
			+ "		<td colspan=\"2\" style=\"padding: 10px; border-bottom: 1px solid white;\" align=\"center\">"
			+ "			<span style=\"font-size: 18px; font-face: Verdana\">Novidades</span>"
			+ "		</td>"
			+ "	</tr>"
			+ "	<tr>"
			+ "		<td width=\"135\" align=\"right\" style=\"padding-top: 10px;\">"
			+ "			Data:"
			+ "		</td>"
			+ "		<td width=\"352\" style=\"padding-top: 10px;\">"
			+ "			<INPUT style=\"border: none; font-size: 12px; width: 100px; margin-left: 5px;\" TYPE=TEXT SIZE=8 NAME=EventDate VALUE=" + dfDateField.format(Cast.toDate(row.get(1))) + ">"
			+ "		</td>"
			+ "	</tr>"
			+ "	<tr>"
			+ "		<td valign=top align=\"right\">"
			+ "			Título:"
			+ "		</td>"
			+ "		<td>"
			+ "			<input id=\"titulo\" style=\"border: none; font-size: 12px; width: 200px; margin-left: 5px;\" type=\"text\" name=\"titulo\" value="+row.get(2)+">"
			+ "		</td>"
			+ "	<tr>"
			+ "		<td colspan=2  align=\"center\">"
			+ "			<table width=\"223\" height=\"147\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
			+ "			  <tr>"
			+ "				<td height=\"147\" background=\"/img/bg_news.jpg\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
			+ "				<table width=\"140\" height=\"130\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">"
			+ "					<tr>"
			+ "					  <td height=\"100\" valign=\"top\" style=\"font-size: 12px; font-face: Verdana, Tahoma, Arial, Helvetica, sans-serif\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">"
			+ "						  <textarea name='novidade' id='novidade' style=\"text-align: center; height: 112px; width:187px ; background-color:#F7F7FF; overflow:hidden; font-size: 12px; font-face: Verdana; border: 0px\">" + row.get(0)+ "</textarea>"
			+ "						</td>"
			+ "					</tr>"
			+ "				  </table></td>"
			+ "			  </tr>"
			+ "			</table>"
			+ "		</td>"
			+ "	<tr>"
			+ "	<tr>"
			+ "		<td colspan=2 align=\"center\">"
			+ "       <input class=\"botao\" type=\"submit\" name=\"submit\" value=\"Modificar\" onMouseOver=\"this.className='botaoover';\" onMouseOut=\"this.className='botao';\">"
			+ "      &nbsp; <input class=\"botao\" type=\"submit\" name=\"submit\" OnClick=UserRequest.value='Delete' value=\"Deletar\" onMouseOver=\"this.className='botaoover';\" onMouseOut=\"this.className='botao';\">"
			+ "		</td>"
			+ "	</tr>"
			+ "</table>"
			+ " "
			+ "</form>");
    
    return returnValue.toString();
  }

//***********************************
//entry point for changes to Calendar
//***********************************

  public void doPost(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    thisServletURI = request.getServletPath();

    out.println("<HTML><head><title>Control Panel</title>");
    out.println("<SCRIPT Language=JavaScript>");
    out.println("function finish(how,date) {");
    out.println("  if (how == 'OK') {");
    out.println("    opener.document.location = '" + thisServletURI + "?date='+date;");
    out.println("    window.close();");
    out.println("  } else if (how == 'conflict') {");
    out.println("    alert('Warning: this event conflicts with another event.');");
    out.println("    opener.document.location = '" + thisServletURI + "?date='+date;");
    out.println("    window.close();");
    out.println("  } else if (how == 'dbError') {");
    out.println("    alert('An unexpected database error was encountered.');");
    out.println("    opener.document.location = '" + thisServletURI + "?date='+date;");
    out.println("    history.go(-1);");
    out.println("  } else if (how == 'bad') {");
    out.println("    alert('Warning: some data values were missing or formatted incorrectly. Please try again.');");
    out.println("    history.go(-1);");
    out.println("  }");
    out.println("}");
    out.println("</SCRIPT></head>");

    String userRequest = request.getParameter("UserRequest");
    String eventID = request.getParameter("EventID");
    String sqlQuery = null;
    
    if (userRequest.equals("Delete"))
      sqlQuery = "DELETE FROM novidade WHERE EventID=" + eventID;
    else {
      String description = request.getParameter("novidade");
      String titulo = request.getParameter("titulo");
      String date = "";
		try {
			date = dfMySQLDate.format(dfDateField.parse(request.getParameter("EventDate")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	
      if (userRequest.equals("New"))
        sqlQuery = "INSERT INTO novidade set Sdate = '"+date+"', " +
        		" Description = '"+description+"', nmstitulo= '" +titulo+"'";
      else if (userRequest.equals("Revise"))
        sqlQuery = "UPDATE novidade SET "
         + "Sdate='" + date + "',Description='" + description
         + "', nmstitulo= '" +titulo+"' WHERE EventID=" + eventID;
    }

    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ aki: " + userRequest + " - " + sqlQuery + " - " + eventID);
    try {
      Connection conn = EntryPoint.getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(sqlQuery);
      out.println("<body onLoad=finish('OK','" + request.getParameter("EventDate") + "');>");

      out.println("</body></html>");
      stmt.close();
    }catch (Exception e) { // SqlExceptions caught here
      out.println("<body onLoad=finish('dbError','" + request.getParameter("EventDate") + "');>");
      out.println(e.getMessage());
      out.println("</body></html>");
    }
  }
 
  String removeSingleQuotes(String inString) {
    int i = inString.indexOf('\'',0);
    return i<0?inString:removeSingleQuotes(new StringBuffer(inString).insert(i,'\\').toString(),i+2);
  }

  String removeSingleQuotes(String inString,int fromIndex) {
    int i = inString.indexOf('\'',fromIndex);
    return i<0?inString:removeSingleQuotes(new StringBuffer(inString).insert(i,'\\').toString(),i+2);
  }

  String removeDoubleQuotes(String inString) {
    int i = inString.indexOf('\"',0);
    return i<0?inString:removeDoubleQuotes(new StringBuffer(inString).replace(i,i+1,"&quot;").toString(),i+5);
  }

  String removeDoubleQuotes(String inString,int fromIndex) {
    int i = inString.indexOf('\"',fromIndex);
    return i<0?inString:removeDoubleQuotes(new StringBuffer(inString).replace(i,i+1,"&quot;").toString(),i+5);
  }
}
