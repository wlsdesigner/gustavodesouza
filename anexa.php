<?php 
 /* pasta que deseja salvar o arquivo*/
 
 /*uploadssite é o nome da pasta que ira ficar os arquivos */
 $uploaddir = 'uploadssite'
 
 /*arquivo é a tag name do form do html*/
 $uploadfile = $uploaddir. $_FILES['arquivoexcel']['name']
 
 if (move_uploaded_file($_FILES['arquivoexcel']['tmp_name'], $uploadfile)){
 echo "Arquivo enviado com sucesso!";}
 else {echo "Não foi possivel enviar o arquivo favor enviar um e-mail para";}
  
 
?>