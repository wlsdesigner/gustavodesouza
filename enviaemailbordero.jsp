<%@ page contentType="text/html; charset=windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Envio de E-mail</title>
<style type="text/css">
#botaolink {
	display: block; //
	padding: 10px 15px 10px;
	text-decoration: none;
	width: 100px;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.8);
	font-weight: bold;
	text-transform: uppercase;
	color: #FFF;
	font: bold .9em 'Lucida Grande', Arial;
	background: #768A8A;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-box-shadow: 2px 2px 3px #000;
	-webkit-box-shadow: 2px 2px 3px #000;
	box-shadow: 2px 2px 3px #000;
	/* For IE 8 */
	-ms-filter:
		"progid:DXImageTransform.Microsoft.Shadow(Strength=3, Direction=135, Color='#000000')";
	/* For IE 5.5 - 7 */
	filter: progid:DXImageTransform.Microsoft.Shadow(Strength=3, Direction=135,
		Color='#000000');
	border: 0px solid #28537d;
	margin: 0 10px 10px 0;
}
</style>
<%@ page import="com.relato.EmailHelper"%>
<%@ page import="java.util.*"%>
<%
	StringBuffer buff = new StringBuffer();
	TreeMap params = new TreeMap();
	String campo;
	String valor;

	String assunto = null != request.getParameter("Assunto") ? request
			.getParameter("Assunto").toString() : "";
	String clientenome = null != request.getParameter("Clientenome") ? request
			.getParameter("Clientenome").toString() : "";
	String clientetipo = null != request.getParameter("Clientetipo") ? request
			.getParameter("Clientetipo").toString() : "";
	String clientecnpjcpf = null != request
			.getParameter("Clientecnpjcpf") ? request.getParameter(
			"Clientecnpjcpf").toString() : "";
	String clienteierg = null != request.getParameter("Clienteierg") ? request
			.getParameter("Clienteierg").toString() : "";
	String clientecontato = null != request
			.getParameter("Clientecontato") ? request.getParameter(
			"Clientecontato").toString() : "";
	String clienteemail = null != request.getParameter("Clienteemail") ? request
			.getParameter("Clienteemail").toString() : "";
	String clisite = null != request.getParameter("Clisite") ? request
			.getParameter("Clisite").toString() : "";
	String clientefone = null != request.getParameter("Clientefone") ? request
			.getParameter("Clientefone").toString() : "";
	String clientefone2 = null != request.getParameter("Clientefone2") ? request
			.getParameter("Clientefone2").toString() : "";
	String clientecep = null != request.getParameter("Clientecep") ? request
			.getParameter("Clientecep").toString() : "";
	String clientelogradouro = null != request
			.getParameter("Clientelogradouro") ? request.getParameter(
			"Clientelogradouro").toString() : "";
	String clientebairro = null != request
			.getParameter("Clientebairro") ? request.getParameter(
			"Clientebairro").toString() : "";
	String clientecidade = null != request
			.getParameter("Clientecidade") ? request.getParameter(
			"Clientecidade").toString() : "";
	String clienteestado = null != request
			.getParameter("Clienteestado") ? request.getParameter(
			"Clienteestado").toString() : "";
	String clientenumero = null != request
			.getParameter("Clientenumero") ? request.getParameter(
			"Clientenumero").toString() : "";
	String clientecomplemento = null != request
			.getParameter("Clientecomplemento") ? request.getParameter(
			"Clientecomplemento").toString() : "";
	String bancosclonebancoid = null != request
			.getParameter("Bancosclonebancoid") ? request.getParameter(
			"Bancosclonebancoid").toString() : "";
	String banconew = null != request.getParameter("Banconew") ? request
			.getParameter("Banconew").toString() : "";
	String bancodeleted = null != request.getParameter("Bancodeleted") ? request
			.getParameter("Bancodeleted").toString() : "";
	String agencia = null != request.getParameter("Agencia") ? request
			.getParameter("Agencia").toString() : "";
	String conta = null != request.getParameter("Conta") ? request
			.getParameter("Conta").toString() : "";

	/*String do Devedor*/
	String devedornome = null != request.getParameter("Devedornome") ? request
			.getParameter("Devedornome").toString() : "";
	String devedortipo = null != request.getParameter("Devedortipo") ? request
			.getParameter("Devedortipo").toString() : "";
	String devedorcnpjcpf = null != request
			.getParameter("Devedorcnpjcpf") ? request.getParameter(
			"Devedorcnpjcpf").toString() : "";
	String devedorierg = null != request.getParameter("Devedorierg") ? request
			.getParameter("Devedorierg").toString() : "";
	String devedorcontato = null != request
			.getParameter("Devedorcontato") ? request.getParameter(
			"Devedorcontato").toString() : "";
	String devedoremail = null != request.getParameter("Devedoremail") ? request
			.getParameter("Devedoremail").toString() : "";
	String devedorsite = null != request.getParameter("Devedorsite") ? request
			.getParameter("Devedorsite").toString() : "";
	String devedorfone = null != request.getParameter("Devedorfone") ? request
			.getParameter("Devedorfone").toString() : "";
	String devedorfone2 = null != request.getParameter("Devedorfone2") ? request
			.getParameter("Devedorfone2").toString() : "";
	String devedorcep = null != request.getParameter("Devedorcep") ? request
			.getParameter("Devedorcep").toString() : "";
	String devedorlogradouro = null != request
			.getParameter("Devedorlogradouro") ? request.getParameter(
			"Devedorlogradouro").toString() : "";
	String devedorbairro = null != request
			.getParameter("Devedorbairro") ? request.getParameter(
			"Devedorbairro").toString() : "";
	String devedorcidade = null != request
			.getParameter("Devedorcidade") ? request.getParameter(
			"Devedorcidade").toString() : "";
	String devedorestado = null != request
			.getParameter("Devedorestado") ? request.getParameter(
			"Devedorestado").toString() : "";
	String devedornumero = null != request
			.getParameter("Devedornumero") ? request.getParameter(
			"Devedornumero").toString() : "";
	String devedorcomplemento = null != request
			.getParameter("Devedorcomplemento") ? request.getParameter(
			"Devedorcomplemento").toString() : "";
	String dataentrada = null != request.getParameter("Dataentrada") ? request
			.getParameter("Dataentrada").toString() : "";
	String datadevolucao = null != request
			.getParameter("Datadevolucao") ? request.getParameter(
			"Datadevolucao").toString() : "";
	String jurosmes = null != request.getParameter("Jurosmes") ? request
			.getParameter("Jurosmes").toString() : "";
	String honorarios = null != request.getParameter("Honorarios") ? request
			.getParameter("Honorarios").toString() : "";
	//String dividasnumerodocumento = null != request.getParameter("Dividasnumerodocumento") ? request.getParameter("Dividasnumerodocumento").toString() : "";

	String destform = null != request.getParameter("destform") ? request
			.getParameter("destform").toString() : "";
	//String destform2 = null != request.getParameter("destform2") ? request.getParameter("destform2").toString() : "";

	buff.append("<font color=111111><b>Assunto")
			.append(": </b></font>").append(assunto).append("<br>");
	buff.append("<font color=111111><b>Cliente Nome")
			.append(": </b></font>").append(clientenome).append("<br>");
	buff.append("<font color=111111><b>Cliente Tipo")
			.append(": </b></font>").append(clientetipo).append("<br>");
	buff.append("<font color=111111><b>Cliente Cnpj/Cpf")
			.append(": </b></font>").append(clientecnpjcpf)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente IE/RG")
			.append(": </b></font>").append(clienteierg).append("<br>");
	buff.append("<font color=111111><b>Cliente Contato")
			.append(": </b></font>").append(clientecontato)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Email")
			.append(": </b></font>").append(clienteemail)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Site")
			.append(": </b></font>").append(clisite).append("<br>");
	buff.append("<font color=111111><b>Cliente Fone")
			.append(": </b></font>").append(clientefone).append("<br>");
	buff.append("<font color=111111><b>Cliente Fone2")
			.append(": </b></font>").append(clientefone2)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Cep")
			.append(": </b></font>").append(clientecep).append("<br>");
	buff.append("<font color=111111><b>Cliente Logradouro")
			.append(": </b></font>").append(clientelogradouro)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Bairro")
			.append(": </b></font>").append(clientebairro)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Cidade")
			.append(": </b></font>").append(clientecidade)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Estado")
			.append(": </b></font>").append(clienteestado)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente Numero")
			.append(": </b></font>").append(clientenumero)
			.append("<br>");
	buff.append("<font color=111111><b>Cliente complemento")
			.append(": </b></font>").append(clientecomplemento)
			.append("<br><br>");
	//Wilson RAFAEL MEXEU AQUI...
	
	  
	Enumeration<String> en = request.getParameterNames();
	Map<String,String> camposBanco = new TreeMap<String,String>();
	Map<String,String> camposDividas = new TreeMap<String,String>();
	String campoParam = "";
	String valorParam = "";
	String idParam = "";
	Set<String> bancosId = new LinkedHashSet<String>();
	Set<String> dividasId = new LinkedHashSet<String>();
	
	//bancos_1_numero
    //bancos_1_agencia
    //bancos_1_conta
    
	//dividas_2_numerodocumento
	//dividas_2_datavencimentoauxiliar
	//dividas_2_valor
	//dividas_2_protesto
	
	while (en.hasMoreElements()) {
		campoParam = en.nextElement();
		if(campoParam.contains("bancos_")){
			valorParam = (request.getParameter(campoParam) != null ? request.getParameter(campoParam) : "");
			idParam = campoParam.substring(campoParam.indexOf("bancos_")+7,campoParam.length());
			if(idParam.contains("_")){
				idParam = idParam.substring(0,idParam.indexOf("_"));
				bancosId.add(idParam);
			}
			camposBanco.put(campoParam, valorParam);
			
		}else if(campoParam.contains("dividas_")){
			valorParam = (request.getParameter(campoParam) != null ? request.getParameter(campoParam) : "");
			idParam = campoParam.substring(campoParam.indexOf("dividas_")+"dividas_".length(),campoParam.length());
			if(idParam.contains("_")){
				idParam = idParam.substring(0,idParam.indexOf("_"));
				dividasId.add(idParam);
			}
			camposDividas.put(campoParam, valorParam);
		}
		//buff.append(campo).append(": ").append(valor).append('\n');
	}

	if(!bancosId.isEmpty()){
		buff.append("<font color=111111><b>-- Lista de Bancos --").append("</b></font>").append("<br>");
		Iterator<String> itBancos = bancosId.iterator();
		while(itBancos.hasNext()){
			String idBanco = itBancos.next();
			//bancos_1_numero
		    //bancos_1_agencia
		    //bancos_1_conta
		    
			String numBanco = camposBanco.containsKey("bancos_"+idBanco+"_numero")?camposBanco.get("bancos_"+idBanco+"_numero"):"";
			String numAgencia = camposBanco.containsKey("bancos_"+idBanco+"_agencia")?camposBanco.get("bancos_"+idBanco+"_agencia"):"";
			String numConta = camposBanco.containsKey("bancos_"+idBanco+"_conta")?camposBanco.get("bancos_"+idBanco+"_conta"):"";
			buff.append("<font color=111111><b>N. Banco")
			.append(": </b></font>").append(numBanco).append("<br>");
			buff.append("<font color=111111><b>Agencia ")
			.append(": </b></font>").append(numAgencia).append("<br>");
			buff.append("<font color=111111><b>Conta ").append(": </b></font>")
			.append(numConta).append("<br><br>");
		
		}
	}
	/*
	buff.append("<font color=111111><b>Bancos").append(": </b></font>")
			.append(bancosclonebancoid).append("<br>");
	buff.append("<font color=111111><b>Novos Bancos")
			.append(": </b></font>").append(banconew).append("<br>");
	buff.append("<font color=111111><b>Bancos deletados ")
			.append(": </b></font>").append(bancodeleted)
			.append("<br>");
	buff.append("<font color=111111><b>Agencia ")
			.append(": </b></font>").append(agencia).append("<br>");
	buff.append("<font color=111111><b>Conta ").append(": </b></font>")
			.append(conta).append("<br>");
	*/
	
	buff.append("<font color=111111><b>Devedor Nome")
			.append(": </b></font>").append(devedornome).append("<br>");
	buff.append("<font color=111111><b>Devedor Tipo")
			.append(": </b></font>").append(devedortipo).append("<br>");
	buff.append("<font color=111111><b>Devedor Cnpj/Cpf")
			.append(": </b></font>").append(devedorcnpjcpf)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor IE/RG")
			.append(": </b></font>").append(devedorierg).append("<br>");
	/*buff.append("<font color=111111><b>Devedor Contato").append(": </b></font>").append(devedorcontato).append("<br>");*/
	buff.append("<font color=111111><b>Devedor Email")
			.append(": </b></font>").append(devedoremail)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Site")
			.append(": </b></font>").append(devedorsite).append("<br>");
	buff.append("<font color=111111><b>Devedor Fone")
			.append(": </b></font>").append(devedorfone).append("<br>");
	buff.append("<font color=111111><b>Devedor Fone2")
			.append(": </b></font>").append(devedorfone2)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Cep")
			.append(": </b></font>").append(devedorcep).append("<br>");
	buff.append("<font color=111111><b>Devedor Logradouro")
			.append(": </b></font>").append(devedorlogradouro)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Bairro")
			.append(": </b></font>").append(devedorbairro)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Cidade")
			.append(": </b></font>").append(devedorcidade)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Estado")
			.append(": </b></font>").append(devedorestado)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Numero")
			.append(": </b></font>").append(devedornumero)
			.append("<br>");
	buff.append("<font color=111111><b>Devedor Complemento")
			.append(": </b></font>").append(devedorcomplemento)
			.append("<br><br>");
	buff.append("<font color=111111><b>Data de Entrada")
			.append(": </b></font>").append(dataentrada).append("<br>");
	buff.append("<font color=111111><b>Data de Devolucao")
			.append(": </b></font>").append(datadevolucao)
			.append("<br>");
	buff.append("<font color=111111><b>Juros Mes")
			.append(": </b></font>").append(jurosmes).append("<br>");
	buff.append("<font color=111111><b>Honorarios")
			.append(": </b></font>").append(honorarios).append("<br><br>");
	//buff.append("<font color=111111><b>Divida numero doc").append(": </b></font>").append(dividasnumerodocumento).append("<br>");

	
	//dividas_2_numerodocumento
	//dividas_2_datavencimentoauxiliar
	//dividas_2_valor
	//dividas_2_protesto
	if(!dividasId.isEmpty()){
		buff.append("<font color=111111><b>-- Lista de Dívidas --").append("</b></font>").append("<br>");
		Iterator<String> itDividas = dividasId.iterator();
		while(itDividas.hasNext()){
			String idDivida= itDividas.next();
			//bancos_1_numero
		    //bancos_1_agencia
		    //bancos_1_conta
		    
			String numDoc = camposDividas.containsKey("dividas_"+idDivida+"_numerodocumento")?camposDividas.get("dividas_"+idDivida+"_numerodocumento"):"";
			String dataVenc = camposDividas.containsKey("dividas_"+idDivida+"_datavencimentoauxiliar")?camposDividas.get("dividas_"+idDivida+"_datavencimentoauxiliar"):"";
			String valorDoc = camposDividas.containsKey("dividas_"+idDivida+"_valor")?camposDividas.get("dividas_"+idDivida+"_valor"):"";
			String protestoDoc = camposDividas.containsKey("dividas_"+idDivida+"_protesto")?camposDividas.get("dividas_"+idDivida+"_protesto"):"";
			buff.append("<font color=111111><b>N. Documento")
			.append(": </b></font>").append(numDoc).append("<br>");
			buff.append("<font color=111111><b>Vencimento ")
			.append(": </b></font>").append(dataVenc).append("<br>");
			buff.append("<font color=111111><b>Valor ").append(": </b></font>")
			.append(valorDoc).append("<br>");
			buff.append("<font color=111111><b>Protesto ").append(": </b></font>")
			.append(protestoDoc).append("<br>");
		
		}
	}
	
	String destino = destform;

	System.out.println("Destinatario: " + destino);

	if (buff.length() > 0) {

		EmailHelper em = new EmailHelper("borderoonline@dgsbrasil.com.br");
		//em.addDest(destino);
		em.addDest("borderoonline@dgsbrasil.com.br");
		em.addDest("wlsdesigner@gmail.com");
		em.addDest("cobranca02@dgsbrasil.com.br");
		//em.addDest("rafaeljplima@hotmail.com");
		
		em.envia("Formulario Borderô Online SITE - Mensagem Enviada",
				buff.toString());
	}
%>
</head>
<body>
	<br>
	<br>
	<br>
	<table width="770" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr valign="middle">
			<td height="144">
				<div align="center">
					<font size="4" face="Verdana, Arial, Helvetica, sans-serif"
						color="#000000">
						<center>
							Sua mensagem foi enviada com sucesso.<br>
							<br>Muito Obrigado!
						</center>
					</font> <br>
					<br>
					<a href="/" id="botaolink">Voltar</a>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>