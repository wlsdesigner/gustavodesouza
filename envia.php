<?php

//Pega os dados postados pelo formulário HTML e os coloca em variaveis
if (preg_match('/tempsite.ws$|gustavodesouza.adv.br$|hospedagemdesites.ws$|websiteseguro.com$/', $_SERVER[HTTP_HOST])) {
//substitua na linha acima a aprte locaweb.com.br por seu domínio.
$email_from='wilson.batista@r1ti.com.br';	// Substitua essa linha pelo seu e-mail@seudominio
}else {
$email_from = "cobranca2@gustavodesouza.adv.br" . $_SERVER[HTTP_HOST];
//    Na linha acima estamos forçando que o remetente seja 'webmaster@',
// você pode alterar para que o remetente seja, por exemplo, 'contato@'.
}


if( PATH_SEPARATOR ==';'){ $quebra_linha="\r\n";

} elseif (PATH_SEPARATOR==':'){ $quebra_linha="\n";

} elseif ( PATH_SEPARATOR!=';' and PATH_SEPARATOR!=':' )  {echo ('Esse script não funcionará corretamente neste servidor, a função PATH_SEPARATOR não retornou o parâmetro esperado.');

}

//pego os dados enviados pelo formulário
$nome = $_POST["nome"];
$email = $_POST["email"];
$mensagem = $_POST["mensagem"];
$assunto = $_POST["assunto"];
//formato o campo da mensagem
$mensagem = wordwrap( $mensagem, 50, "<br>", 1);

//valido os emails
if (!preg_match("^([0-9,a-z,A-Z]+)([.,_]([0-9,a-z,A-Z]+))*[@]([0-9,a-z,A-Z]+)([.,_,-]([0-9,a-z,A-Z]+))*[.]([0-9,a-z,A-Z]){2}([0-9,a-z,A-Z])?$^", $email)){

echo"<center>Digite um email valido</center>";
echo "<center><a href=\"javascript:history.go(-1)\">Voltar</center></a>";
exit;

}

$arquivo = isset($_FILES["arquivo"]) ? $_FILES["arquivo"] : FALSE;

if(file_exists($arquivo["tmp_name"]) and !empty($arquivo)){

$fp = fopen($_FILES["arquivo"]["tmp_name"],"rb");
$anexo = fread($fp,filesize($_FILES["arquivo"]["tmp_name"]));
$anexo = base64_encode($anexo);

fclose($fp);

$anexo = chunk_split($anexo);


$boundary = "XYZ-" . date("dmYis") . "-ZYX";

$mens = "--$boundary" . $quebra_linha . "";
$mens .= "Content-Transfer-Encoding: 8bits" . $quebra_linha . "";
$mens .= "Content-Type: text/html; charset=\"UTF-8\"" . $quebra_linha . "" . $quebra_linha . ""; //plain
$mens .= "$mensagem" . $quebra_linha . "";
$mens .= "--$boundary" . $quebra_linha . "";
$mens .= "Content-Type: ".$arquivo["type"]."" . $quebra_linha . "";
$mens .= "Content-Disposition: attachment; filename=\"".$arquivo["name"]."\"" . $quebra_linha . "";
$mens .= "Content-Transfer-Encoding: base64" . $quebra_linha . "" . $quebra_linha . "";
$mens .= "$anexo" . $quebra_linha . "";
$mens .= "--$boundary--" . $quebra_linha . "";

$headers = "MIME-Version: 1.0" . $quebra_linha . "";
$headers .= "From: $email_from " . $quebra_linha . "";
$headers .= "Return-Path: $email_from " . $quebra_linha . "";
$headers .= "Content-type: multipart/mixed; boundary=\"$boundary\"" . $quebra_linha . "";
$headers .= "$boundary" . $quebra_linha . "";


//envio o email com o anexo
mail($email,$assunto,$mens,$headers, "-r".$email_from);

echo"Email enviado com Sucesso!!";

}

//se nao tiver anexo
else{

$headers = "MIME-Version: 1.0" . $quebra_linha . "";
$headers .= "Content-type: text/html; charset=iso-8859-1" . $quebra_linha . "";
$headers .= "From: $email_from " . $quebra_linha . "";
$headers .= "Return-Path: $email_from " . $quebra_linha . "";

//envia o email sem anexo
mail($email,$assunto,$mensagem, $headers, "-r".$email_from);


echo"Email enviado com Sucesso!";
}

?>
