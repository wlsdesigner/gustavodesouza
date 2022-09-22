package system;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.Map;

public class HEUtil {

	private final boolean debug = true;

	private String[] scvsts = {
            "&lt;", "&gt;", "&amp;", "&quot;", "&copy;", "&reg;", "&Agrave;", "&Aacute;", "&Acirc;", "&Atilde;",
            "&Auml;", "&Egrave;", "&Eacute;", "&Ecirc;", "&Euml;", "&Igrave;", "&Iacute;", "&Icirc;", "&Iuml;",
            "&Ograve;", "&Oacute;", "&Ocirc;", "&Otilde;", "&Ouml;", "&Ugrave;", "&Uacute;", "&Ucirc;",
            "&Uuml;", "&Ccedil;", "&agrave;", "&aacute;", "&acirc;", "&atilde;", "&auml;", "&egrave;",
            "&eacute;", "&ecirc;", "&euml;", "&igrave;", "&iacute;", "&icirc;", "&iuml;", "&ograve;",
            "&oacute;", "&ocirc;", "&otilde;", "&ouml;", "&ugrave;", "&uacute;", "&ucirc;", "&uuml;",
            "&ccedil;"
            };

    private char[]   ccvsts = {
            '<', '>', '&', '"', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
            '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
            '�', '�', '�', '�', '�', '�', '�', '�'
            };

	private Map paginasInc;
	private Map mensagens;
	private File incFolder;
	private String pathToIncFiles;

	public HEUtil() {
		paginasInc = new HashMap();
		mensagens = new HashMap();
	}

	private void loadMessages() {
		mensagens.put("sTxtOK", "OK");
		mensagens.put("sTxtCancel", "Cancelar");
		mensagens.put("sTxtCloseWin", "Clique no bot�o Cancelar para fechar esta janela.");
		mensagens.put("sTxtReturn", "Clique em 'Cancelar' para retornar � tela anterior.");
		mensagens.put("sTxtName", "Nome");
		mensagens.put("sTxtBorder", "Borda");
		mensagens.put("sTxtBgColor", "Cor de Fundo");
		mensagens.put("sTxtAction", "A��o");
		mensagens.put("sTxtRename", "Renomear");
		mensagens.put("sTxtBytes", "Bytes");
		mensagens.put("sTxtFile", "Arquivo");
		mensagens.put("sTxtTextAreaError", "Seu navegador deve ser IE5.5 ou vers�o superior para exibir o Editor de HTML. Em seu lugar ser� exibida uma caixa de texto simples.");
		mensagens.put("sTxtContextMenuWidth", "225");
		mensagens.put("sTxtColors", "Cores");
		mensagens.put("sTxtColorsInst", "Selecione a cor desejada e clique 'OK' para usar a cor selecionada.");
		mensagens.put("sTxtFindError", "Por favor, digite o texto no campo 'Localizar:'.");
		mensagens.put("sTxtFindNotFound", "Sua palavra n�o foi localizada.Voc� gostaria de iniciar novamente pelo topo?");
		mensagens.put("sTxtFindNotReplaced", "Palavra n�o localizada. Nada foi substitu�do.");
		mensagens.put("sTxtFindReplaced", " palavra(s) foi(foram) substitu�da(s).");
		mensagens.put("sTxtFindFindWhat", "Localizar:");
		mensagens.put("sTxtFindReplaceWith", "Substituir por:");
		mensagens.put("sTxtFindMatchCase", "Diferenciar mai�scula/min�scula");
		mensagens.put("sTxtFindMatchWord", "Coincidir apenas palavra inteira");
		mensagens.put("sTxtFindNext", "Localizar Pr�xima");
		mensagens.put("sTxtFindClose", "Fechar");
		mensagens.put("sTxtFindReplaceText", "Substituir");
		mensagens.put("sTxtFindReplaceAll", "Substituir Tudo");
		mensagens.put("sTxtInsertFlash", "Insere Flash");
		mensagens.put("sTxtModifyFlash", "Modifica Propriedades do Flash");
		mensagens.put("sTxtExternalFlash", "Flash Externo");
		mensagens.put("sTxtInternalFlash", "Flash Interno ");
		mensagens.put("sTxtInsertFlashInst", "Informe o endere�o do flash a ser inserido ou selecione um flash na lista abaixo. Clique 'Insere' para inserir o flash.");
		mensagens.put("sTxtUploadSuccess", " enviado com sucesso");
		mensagens.put("sTxtUploadFlash", "Upload Flash");
		mensagens.put("sTxtFlashErr", "O arquivo que voc� enviou n�o � um Flash v�lido");
		mensagens.put("sTxtFlashExists", "j� existe. Voc� confirma a substitui��o do mesmo?");
		mensagens.put("sTxtLoop", "Loop");
		mensagens.put("sTxtChooseFlash", "Por favor, primeiro escolha um arquivo flash para enviar!");
		mensagens.put("sTxtCantDelete", "O arquivo selecionado n�o pode ser exclu�do");
		mensagens.put("sTxtCantUpload", "O arquivo selecionado n�o pode ser enviado");
		mensagens.put("sTxtFlashWidthErr", "A largura do flash deve ser um n�mero positivo v�lido");
		mensagens.put("sTxtFlashHeightErr", "A altura do flash deve ser um n�mero positivo v�lido");
		mensagens.put("sTxtFlashWidth", "Largura Flash ");
		mensagens.put("sTxtFlashHeight", "Altura Flash ");
		mensagens.put("sTxtInsertImage", "Inserir Imagem");
		mensagens.put("sTxtExternalImage", "Imagem Externa");
		mensagens.put("sTxtInternalImage", "Imagem Interna");
		mensagens.put("sTxtInsertImageInst", "Digite a URL da imagem a inserir ou Escolha uma das imagens mostradas abaixo e clique em seu link de inser��o para adicion�-la.");
		mensagens.put("sTxtUploadImage", "Upload (Enviar Imagem)");
		mensagens.put("sTxtImageName", "Nome do Arquivo");
		mensagens.put("sTxtFileSize", "Tamanho do Arquivo");
		mensagens.put("sTxtImageView", "Visualizar");
		mensagens.put("sTxtImageInsert", "Inserir");
		mensagens.put("sTxtImageDel", "Excluir");
		mensagens.put("sTxtImageBackgd", "Fundo");
		mensagens.put("sTxtImageErr", "O arquivo que voc� enviou n�o continha uma imagem v�lida");
		mensagens.put("sTxtImageSuccess", "enviada com sucesso");
		mensagens.put("sTxtImageSetBackgd", "Voc� tem certeza que deseja definir esta imagem como a imagem de fundo da p�gina?");
		mensagens.put("sTxtInvalidImageType", "O arquivo selecionado n�o � uma imagem v�lida");
		mensagens.put("sTxtEmptyImageLibrary", "[A cole��o de imagens selecionada est� vazia]");
		mensagens.put("sTxtImageModify", "Modificar");
		mensagens.put("sTxtImageExists", "j� existe. Voc� realmente quer sobrescrever?");
		mensagens.put("sTxtChooseImage", "Por favor, escolha uma imagem antes!");
		mensagens.put("sTxtImageDelete", "Voc� realmente quer excluir esse arquivo?");
		mensagens.put("sTxtImageDeleted", "Imagem exclu�da com sucesso!");
		mensagens.put("sTxtLinkManager", "Gerenciador de Links");
		mensagens.put("sTxtLinkManagerInst", "Digite as informa��es necess�rias e clique em Inserir Link para inserir um link em sua p�gina web.");
		mensagens.put("sTxtLinkManagerInst2", "Localize o arquivo no gerenciador de arquivos abaixo e selecione Obter Localiza��o de Links. Clique em Inserir Link para inserir o link.");
		mensagens.put("sTxtLinkErr", "A URL n�o pode ser deixada em branco");
		mensagens.put("sTxtLinkInfo", "Informa��es do Link");
		mensagens.put("sTxtUrl", "URL");
		mensagens.put("sTxtLibraryUrl", "Links Pr�-definido");
		mensagens.put("sTxtTargetWin", "Janela Destino");
		mensagens.put("sTxtAnch", "�ncora");
		mensagens.put("sTxtGetLinkInfo", "Obter informa��es do Link para");
		mensagens.put("sTxtGetLink", "Obter Localiza��o de Links");
		mensagens.put("sTxtInsertLink", "Inserir Link");
		mensagens.put("sTxtRemoveLink", "Remover Link");
		mensagens.put("sTxtInsertAnchorErr", "Nome da �ncora n�o pode ser deixado em branco");
		mensagens.put("sTxtInsertAnchorName", "Nome da �ncora");
		mensagens.put("sTxtInsertAnchor", "Inserir �ncora");
		mensagens.put("sTxtInsertAnchorInst", "Digite as informa��es necess�rias e clique em OK para inserir uma �ncora em sua p�gina web.");
		mensagens.put("sTxtModifyAnchor", "Modificar �ncora");
		mensagens.put("sTxtModifyAnchorInst", "Digite as informa��es necess�rias e clique em OK para modificar a �ncora atual.");
		mensagens.put("sTxtInsertButton", "Inserir Bot�o de A��o");
		mensagens.put("sTxtInsertButtonInst", "Digite as informa��es necess�rias e clique em OK para inserir um bot�o em sua p�gina web.");
		mensagens.put("sTxtModifyButton", "Modificar Bot�o de A��o");
		mensagens.put("sTxtModifyButtonInst", "Digite as informa��es necess�rias e clique em OK para modificar o bot�o atual.");
		mensagens.put("sTxtInsertChar", "Inserir Caracteres Especiais");
		mensagens.put("sTxtInsertCharInst", "Selecione o caractere que voc� necessita e clique em Inserir Caractere para inserir um caractere especial em sua p�gina web.");
		mensagens.put("sTxtChar", "Caractere");
		mensagens.put("sTxtCharToInsert", "Caractere a Inserir");
		mensagens.put("sTxtInsertCheckBox", "Inserir Caixa de Sele��o");
		mensagens.put("sTxtInsertCheckBoxInst", "Digite as informa��es necess�rias e clique em OK para inserir uma caixa de sele��o em sua p�gina web.");
		mensagens.put("sTxtModifyCheckBox", "Modificar Caixa de Sele��o");
		mensagens.put("sTxtModifyCheckBoxInst", "Digite as informa��es necess�rias e clique em OK para modificar uma caixa de sele��o selecionada.");
		mensagens.put("sTxtEmailErr", "Endere�o de E-mail n�o pode ser deixado em branco");
		mensagens.put("sTxtInsertEmail", "Inserir Link de E-mail");
		mensagens.put("sTxtRemoveEmailLink", "Remover Link de E-mail");
		mensagens.put("sTxtInsertEmailInst", "Digite as informa��es necess�rias e clique em Inserir Link para criar um link com o endere�o de e-mail em sua p�gina web.");
		mensagens.put("sTxtEmailAddress", "Endere�o de E-mail");
		mensagens.put("sTxtSubject", "Assunto");
		mensagens.put("sTxtInsertEmailLink", "Inserir Link de E-mail");
		mensagens.put("sTxtInsertHidden", "Inserir Campo Oculto");
		mensagens.put("sTxtInsertHiddenInst", "Digite as informa��es necess�rias e clique em OK para inserir um campo oculto em sua p�gina web.");
		mensagens.put("sTxtModifyField", "Modificar Campo Oculto");
		mensagens.put("sTxtModifyFieldInst", "Digite as informa��es necess�rias e clique em OK para modificar o campo oculto selecionado.");
		mensagens.put("sTxtInsertRadio", "Inserir Bot�o de Op��o");
		mensagens.put("sTxtInsertRadioInst", "Digite as informa��es necess�rias e clique em OK para inserir um bot�o de op��o em sua p�gina web.");
		mensagens.put("sTxtModifyRadio", "Modificar Bot�o de Op��o");
		mensagens.put("sTxtModifyRadioInst", "Digite as informa��es necess�rias e clique em OK para modificar um bot�o de op��o selecionado.");
		mensagens.put("sTxtTableRowErr", "Linhas devem conter um n�mero positivo e v�lido");
		mensagens.put("sTxtTableColErr", "Colunas devem conter um n�mero positivo e v�lido");
		mensagens.put("sTxtTableWidthErr", "Largura deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtTablePaddingErr", "Enchimento de C�lula deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtTableSpacingErr", "Espa�amento de C�lula deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtTableBorderErr", "Borda deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtRows", "Linhas");
		mensagens.put("sTxtPadding", "Enchimento de C�lula");
		mensagens.put("sTxtSpacing", "Espa�amento de C�lula");
		mensagens.put("sTxtWidth", "Largura");
		mensagens.put("sTxtHeight", "Altura");
		mensagens.put("sTxtCols", "Colunas");
		mensagens.put("sTxtInsertTable", "Inserir Tabela");
		mensagens.put("sTxtInsertTableInst", "Digite as informa��es necess�rias e clique em OK para inserir uma tabela em sua p�gina web.");
		mensagens.put("sTxtModifyTable", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtModifyTableInst", "Digite as informa��es necess�rias e clique em OK para modificar as propriedades de tabela da sua tabela.");
		mensagens.put("sTxtCellWidthErr", "Largura da C�lula deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtCellHeightErr", "Altura da C�lula deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtModifyCell", "Modificar Propriedades da C�lula");
		mensagens.put("sTxtModifyCellInst", "Digite as informa��es necess�rias e clique em OK para modificar as propriedades de c�lula.");
		mensagens.put("sTxtCellWidth", "Largura da C�lula");
		mensagens.put("sTxtCellHeight", "Altura da C�lula");
		mensagens.put("sTxtHorizontalAlign", "Alinhamento Horizontal");
		mensagens.put("sTxtVerticalAlign", "Alinhamento Vertical");
		mensagens.put("sTxtCharWidthErr", "Largura do Caractere deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtLinesErr", "Linhas devem conter um n�mero positivo e v�lido");
		mensagens.put("sTxtMaxCharsErr", "Caracteres M�ximos devem conter um n�mero positivo e v�lido");
		mensagens.put("sTxtInitialValue", "Valor Inicial");
		mensagens.put("sTxtInitialState", "Estado Inicial");
		mensagens.put("sTxtCharWidth", "Largura");
		mensagens.put("sTxtLines", "Linhas");
		mensagens.put("sTxtType", "Tipo");
		mensagens.put("sTxtMaxChars", "Caracteres M�ximos");
		mensagens.put("sTxtMethod", "M�todo");
		mensagens.put("sTxtInsertForm", "Inserir Formul�rio");
		mensagens.put("sTxtInsertFormInst", "Digite as informa��es necess�rias e clique em OK para inserir um formul�rio em sua p�gina web.");
		mensagens.put("sTxtModifyForm", "Modificar Propriedades do Formul�rio");
		mensagens.put("sTxtModifyFormInst", "Digite as informa��es necess�rias e clique em OK para modificar as propriedades de formul�rio de seu formul�rio.");
		mensagens.put("sTxtInsertTextArea", "Inserir �rea de Texto");
		mensagens.put("sTxtInsertTextAreaInst", "Digite as informa��es necess�rias e clique em OK para inserir uma �rea de texto em sua p�gina web.");
		mensagens.put("sTxtModifyTextArea", "Modificar �rea de Texto");
		mensagens.put("sTxtModifyTextAreaInst", "Digite as informa��es necess�rias e clique em OK para modificar uma �rea de texto selecionada.");
		mensagens.put("sTxtInsertTextField", "Inserir Campo Texto");
		mensagens.put("sTxtInsertTextFieldInst", "Digite as informa��es necess�rias e clique em OK para inserir um campo texto em sua p�gina web.");
		mensagens.put("sTxtModifyTextField", "Modificar Campo Texto");
		mensagens.put("sTxtModifyTextFieldInst", "Digite as informa��es necess�rias e clique em OK para modificar o campo texto selecionado.");
		mensagens.put("sTxtSetBackgd", "Voc� tem certeza de que deseja definir esta imagem como imagem de fundo da p�gina?");
		mensagens.put("sTxtGuidelines", "Linhas-Guia");
		mensagens.put("sTxtOn", "Ativar");
		mensagens.put("sTxtOff", "Desativar");
		mensagens.put("sTxtClean", "Voc� tem certeza de que deseja limpar o c�digo HTML?");
		mensagens.put("sTxtImageWidthErr", "Largura da Imagem deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtImageHeightErr", "Altura da Imagem deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtImageBorderErr", "Borda da Imagem deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtHorizontalSpacingErr", "Espa�amento Horizontal deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtVerticalSpacingErr", "Espa�amento Vertical deve conter um n�mero positivo e v�lido");
		mensagens.put("sTxtModifyImage", "Modificar Propriedades da Imagem");
		mensagens.put("sTxtModifyImageInst", "Digite as informa��es necess�rias e clique Modificar para modificar as propriedades da imagem selecionada.");
		mensagens.put("sTxtAltText", "Texto Alternativo");
		mensagens.put("sTxtImageWidth", "Largura da Imagem");
		mensagens.put("sTxtImageHeight", "Altura da Imagem");
		mensagens.put("sTxtAlignment", "Alinhamento");
		mensagens.put("sTxtHorizontalSpacing", "Espa�amento Horizontal");
		mensagens.put("sTxtVerticalSpacing", "Espa�amento Vertical");
		mensagens.put("sTxtPageProps", "Modificar Propriedades da P�gina");
		mensagens.put("sTxtPagePropsInst", "Digite as informa��es necess�rias e clique em OK para modificar as propriedades da sua p�gina.");
		mensagens.put("sTxtPageTitle", "T�tulo da P�gina");
		mensagens.put("sTxtDescription", "Descri��o");
		mensagens.put("sTxtKeywords", "Palavras-Chave");
		mensagens.put("sTxtBgImage", "Imagem de Fundo");
		mensagens.put("sTxtTextColor", "Cor do Texto");
		mensagens.put("sTxtLinkColor", "Cor do Link");
		mensagens.put("sTxtCustomInsert", "Inserir C�digo Customizado");
		mensagens.put("sTxtCustomInsertInst", "Selecione a op��o Inserir C�digo Customizado e clique em Inserir HTML para inserir o HTML Customizado");
		mensagens.put("sTxtInsertHTML", "Inserir HTML");
		mensagens.put("sTxtCustomInsertErr", "Por favor, selecione a op��o Inserir C�digo Customizado para inserir em sua p�gina web");
		mensagens.put("sTxtPreview", "Pr�-Visualiza��o");
		mensagens.put("sTxtFullscreen", "Modo Tela Cheia");
		mensagens.put("sTxtCut", "Recortar");
		mensagens.put("sTxtCopy", "Copiar");
		mensagens.put("sTxtPaste", "Colar");
		mensagens.put("sTxtFindReplace", "Localizar e Substituir");
		mensagens.put("sTxtUndo", "Desfazer");
		mensagens.put("sTxtRedo", "Refazer");
		mensagens.put("sTxtRemoveFormatting", "Remover a Formata��o de Texto");
		mensagens.put("sTxtBold", "Negrito");
		mensagens.put("sTxtUnderline", "Sublinhado");
		mensagens.put("sTxtItalic", "It�lico");
		mensagens.put("sTxtStrikethrough", "Tachado");
		mensagens.put("sTxtNumList", "Inserir Lista Numerada");
		mensagens.put("sTxtBulletList", "Inserir Lista com Marcadores");
		mensagens.put("sTxtDecreaseIndent", "Diminuir Recuo");
		mensagens.put("sTxtIncreaseIndent", "Aumentar Recuo");
		mensagens.put("sTxtSuperscript", "Sobrescrito");
		mensagens.put("sTxtSubscript", "Subscrito");
		mensagens.put("sTxtAlignLeft", "Alinhar � Esquerda");
		mensagens.put("sTxtAlignCenter", "Centralizar");
		mensagens.put("sTxtAlignRight", "Alinhar � Direita");
		mensagens.put("sTxtAlignJustify", "Justificar");
		mensagens.put("sTxtInsertHR", "Inserir Linha Horizontal");
		mensagens.put("sTxtHyperLink", "Criar ou Modificar Link");
		mensagens.put("sTxtAnchor", "Inserir/Modificar �ncora");
		mensagens.put("sTxtEmail", "Criar Link de E-mail");
		mensagens.put("sTxtTextbox", "Inserir Caixa de Texto");
		mensagens.put("sTxtFormFunctions", "Fun��es de Formul�rio");
		mensagens.put("sTxtForm", "Inserir Formul�rio");
		mensagens.put("sTxtFormModify", "Modificar Propriedades de Formul�rio");
		mensagens.put("sTxtTextField", "Inserir/Modificar Campo Texto");
		mensagens.put("sTxtTextArea", "Inserir/Modificar �rea de Texto");
		mensagens.put("sTxtHidden", "Inserir/Modificar Campo Oculto");
		mensagens.put("sTxtButton", "Inserir/Modificar Bot�o de A��o");
		mensagens.put("sTxtCheckbox", "Inserir/Modificar Caixa de Sele��o");
		mensagens.put("sTxtRadioButton", "Inserir/Modificar Bot�o de Op��o");
		mensagens.put("sTxtFont", "Fonte");
		mensagens.put("sTxtSize", "Tamanho");
		mensagens.put("sTxtColor", "Cor");
		mensagens.put("sTxtFormat", "Formato");
		mensagens.put("sTxtStyle", "Estilo");
		mensagens.put("sTxtColour", "Cor de Fonte");
		mensagens.put("sTxtBackColour", "Real�ar");
		mensagens.put("sTxtTableFunctions", "Fun��es de Tabela");
		mensagens.put("sTxtTable", "Inserir Tabela");
		mensagens.put("sTxtTableModify", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtCellModify", "Modificar Propriedades de C�lula");
		mensagens.put("sTxtInsertRowA", "Inserir Linha Acima");
		mensagens.put("sTxtInsertRowB", "Inserir Linha Abaixo");
		mensagens.put("sTxtDeleteRow", "Excluir Linha");
		mensagens.put("sTxtInsertColA", "Inserir Coluna � Direita");
		mensagens.put("sTxtInsertColB", "Inserir Coluna � Esquerda");
		mensagens.put("sTxtDeleteCol", "Excluir Coluna");
		mensagens.put("sTxtIncreaseColSpan", "Mesclar Coluna");
		mensagens.put("sTxtDecreaseColSpan", "Dividir Coluna");
		mensagens.put("sTxtImage", "Inserir/Modificar Imagem");
		mensagens.put("sTxtChars", "Inserir Caracteres Especiais");
		mensagens.put("sTxtCharsInst", "Clique no caractere requerido para inseri-lo na p�gina.");
		mensagens.put("sTxtPageProperties", "Modificar Propriedades de P�gina");
		mensagens.put("sTxtCleanCode", "Limpar C�digo HTML");
		mensagens.put("sTxtPasteWord", "Colar do MS Word");
		mensagens.put("sTxtCustomInserts", "Inserir C�digo HTML Customizado");
		mensagens.put("sTxtToggleGuidelines", "Mostrar/Ocultar Linhas-Guia");
		mensagens.put("sTxtTogglePosition", "Trocar Posicionamento Absoluto");
		mensagens.put("sTxtFlash", "Inserir / Modificar Flash");
		mensagens.put("sTxtFlashDeleted", "Arquivo flash removido com sucesso!");
		mensagens.put("sTxtEmptyFlashLibrary", "[A biblioteca de arquivos flash selecionada est� vazia.]");
		mensagens.put("sTxtHelpTitle", "&nbsp;Os comandos do Editor de HTML");
		mensagens.put("sTxtHelp", "Ajuda");
		mensagens.put("sTxtHelpNote", "Nota: Se uma das op��es abaixo n�o estiver vis�vel ou acess�vel em seu editor, talvez tenha sido desabilitada por seu administrador.");
		mensagens.put("sTxtHelpCloseWin", "Fechar Janela");
		mensagens.put("sTxtHelpSaveTitle", "Salva");
		mensagens.put("sTxtHelpSave", "Para salvar seu trabalho, pressione o bot�o Salva");
		mensagens.put("sTxtHelpFullscreenTitle", "Modo Tela Cheia");
		mensagens.put("sTxtHelpFullscreen", "Para expandir a janela ativa em tela cheia, clique no �cone 'Modo Tela Cheia'. Cliques consecutivos neste �cone ativar�o e desativar�o este recurso.");
		mensagens.put("sTxtHelpCutTitle", "Recortar (Ctrl+X)");
		mensagens.put("sTxtHelpCut", "Para recortar uma parte do documento, selecione a �rea desejada e clique no �cone 'Recortar' (tecla de atalho - CTRL+X).");
		mensagens.put("sTxtHelpCopyTitle", "Copiar (Ctrl+C)");
		mensagens.put("sTxtHelpCopy", "Para copiar uma parte do documento, selecione a �rea desejada e clique no �cone 'Copiar' (tecla de atalho - CTRL+C).");
		mensagens.put("sTxtHelpPasteTitle", "Colar (Ctrl+V)");
		mensagens.put("sTxtHelpPaste", "Para colar uma parte que j� foi recortada (ou copiada), clique no lugar em que voc� deseja exibi-la e clique no �cone 'Colar' (tecla de atalho  - CTRL+V).Para colar do Microsoft Word, clique no �cone pr�ximo ao �cone Colar.");
		mensagens.put("sTxtHelpPasteWordTitle", "Colar do Microsoft Word (Ctrl + D)");
		mensagens.put("sTxtHelpPasteWord", "Para colar do Microsoft Word: Copie o texto desejado no Microsoft Word e clique no �cone pr�ximo ao �cone Colar. Selecione a op��o 'Colar do MS Word'. Essa opera��o remover� n�o somente as tags que o Microsoft Word coloca automaticamente, mas tamb�m a maior parte da formata��o.");
		mensagens.put("sTxtHelpFindReplaceTitle", "Localizar e Substituir");
		mensagens.put("sTxtHelpFindReplace", "Para localizar e substituir palavras ou frases dentro do texto: Selecione a op��o de localizar e substituir. No campo 'Localizar', digite a palavra ou frase que deseja substituir.No campo 'Substituir', selecione a nova palavra ou frase que substituir� o texto localizado.Voc� pode escolher a op��o 'Localizar Pr�xima', que permite a substitui��o manual de cada texto localizado ou voc� pode escolher 'Substituir Tudo', que permite a substitui��o de todos os textos localizados.A op��o 'Diferenciar Mai�sculas/Min�sculas' permite que voc� busque uma palavra ou frase exatamente igual a que foi digitada no campo 'Localizar', respeitando as letras mai�sculas e min�sculas utilizadas.Selecionar a op��o 'Coincidir a palavra inteira' implica na busca de palavras que coincidam exatamente com a palavra ou frase indicada no campo 'Localizar'.");
		mensagens.put("sTxtHelpUndoTitle", "Desfazer (Ctrl+Z)");
		mensagens.put("sTxtHelpUndo", "Para desfazer a �ltima altera��o, clique no �cone 'Desfazer' (tecla de atalho - CTRL+Z). Cada clique consecutivo ir� desfazer a �ltima a��o realizada.");
		mensagens.put("sTxtHelpRedoTitle", "Refazer (Ctrl+Y)");
		mensagens.put("sTxtHelpRedo", "Para refazer a �ltima a��o, clique no �cone 'Refazer' (keyboard shortcut - CTRL+Y). Cada clique consecutivo repetir� a �ltima a��o realizada no documento.");
		mensagens.put("sTxtHelpSpellcheckTitle", "Verificar Ortografia (F7)");
		mensagens.put("sTxtHelpSpellcheck", "Para verificar a ortografia, selecione o texto que voc� gostaria de verificar (se voc� n�o selecionar o texto, todo o seu documento ser� verificado).Clique no �cone de verifica��o de ortografia ou clique com o bot�o direito do mouse e escolha a op��o 'Verificar Ortografia'.Voc� ver� a primeira palavra grafada incorretamente. Voc�, ent�o, poder� escolher - Alterar a palavra com erro de grafia utilizando as palavras sugeridas - Ignorar a palavra com erro de grafia (n�o fazer quaisquer mudan�as) Para verificar a ortografia de uma �nica palavra, selecione a palavra e clique com o bot�o direito do mouse para obter uma sugest�o de poss�veis substitui��es. Para substituir a palavra incorreta por uma das palavras sugeridas, simplesmente selecione uma das substitui��es.");
		mensagens.put("sTxtHelpRemoveFormattingTitle", "Remover Formata��o de Texto");
		mensagens.put("sTxtHelpRemoveFormatting", "Este comando permite a sele��o de uma parte de texto e a remo��o de qualquer formata��o a ele aplicada. Para remover qualquer formata��o de texto, selecione a �rea de texto desejada e clique no bot�o 'Remover Formata��o de Texto'.");
		mensagens.put("sTxtHelpBoldTitle", "Negrito (Ctrl+B)");
		mensagens.put("sTxtHelpBold", "Para negritar o texto, selecione o texto desejado e clique no �cone 'Negrito' (tecla de atalho - CTRL+B). Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpUnderlineTitle", "Sublinhado (Ctrl+U)");
		mensagens.put("sTxtHelpUnderline", "Para sublinhar texto, selecione o texto desejado e clique no �cone 'Sublinhado' (tecla de atalho - CTRL+U). Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpItalicTitle", "It�lico (Ctrl+I)");
		mensagens.put("sTxtHelpItalic", "Para converter o texto para it�lico, selecione o texto desejado e clique no �cone 'It�lico' (tecla de atalho - CTRL+I). Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpStrikethroughTitle", "Tachado");
		mensagens.put("sTxtHelpStrikethrough", "Para aplicar o efeito de tachado ao texto, realce o texto que deseja formatar e selecione o �cone 'Tachado'. Cada clique consecutivo ativar� e desativar� esta fun��o");
		mensagens.put("sTxtHelpINListTitle", "Inserir Lista Numerada");
		mensagens.put("sTxtHelpINList", "Para criar uma lista de texto numerada, clique no �cone 'Inserir Lista Numerada'. Se o texto j� tiver sido selecionado, este ser� convertido em uma lista sequencial e numerada. Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpIBListTitle", "Inserir Lista com Marcadores");
		mensagens.put("sTxtHelpIBList", "Para criar uma lista de texto com marcadores, clique no �cone 'Inserir Lista com Marcadores'. Se o texto j� tiver sido selecionado, a sele��o ser� convertida em uma lista com marcadores. Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpDIndentTitle", "Diminuir Recuo");
		mensagens.put("sTxtHelpDIndent", "Para diminuir o recuo de um par�grafo, clique no �cone 'Diminuir Recuo'. Cada clique consecutivo mover� o texto para a esquerda.");
		mensagens.put("sTxtHelpIIndentTitle", "Aumentar Recuo");
		mensagens.put("sTxtHelpIIndent", "Para aumentar o recuo de um par�grafo, clique no �cone 'Aumentar Recuo'. Cada clique consecutivo mover� o texto para a direita.");
		mensagens.put("sTxtHelpSuperscriptTitle", "Sobrescrito");
		mensagens.put("sTxtHelpSuperscript", "Para aplicar o efeito de sobrescrito ao texto (alinhamento acima): Selecione o texto desejado e clique no �cone 'Sobrescrito'.  Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpSubscriptTitle", "Subscrito");
		mensagens.put("sTxtHelpSubscript", "Para aplicar o efeito de subscrito ao texto (alinhamento abaixo): Selecione o texto desejado e clique no �cone 'Subscrito'.  Cada clique consecutivo ativar� e desativar� esta fun��o.");
		mensagens.put("sTxtHelpALeftTitle", "Alinhar � Esquerda");
		mensagens.put("sTxtHelpALeft", "Para alinhar � esquerda, selecione o texto e clique no �cone 'Alinhar � Esquerda'.");
		mensagens.put("sTxtHelpACenterTitle", "Centralizar");
		mensagens.put("sTxtHelpACenter", "Para centralizar, selecione o texto e clique no �cone 'Centralizar'.");
		mensagens.put("sTxtHelpARightTitle", "Alinhar � Direita");
		mensagens.put("sTxtHelpARight", "Para alinhar � direita, selecione o texto e clique no �cone 'Alinhar � Direita'.");
		mensagens.put("sTxtHelpJustifyTitle", "Justificar");
		mensagens.put("sTxtHelpJustify", "Para justificar um par�grafo ou texto, selecione o texto e clique no �cone 'Justificar'. ");
		mensagens.put("sTxtHelpIHLineTitle", "Inserir Linha Horizontal");
		mensagens.put("sTxtHelpIHLine", "Para inserir uma linha horizontal, selecione o local desejado para a inser��o e clique no �cone 'Inserir Linha Horizontal'.");
		mensagens.put("sTxtHelpCMHyperLinkTitle", "Criar ou Modificar Link");
		mensagens.put("sTxtHelpCMHyperLink", "Para criar um hiperlink, selecione o texto ou imagem que voc� deseja exibir como hiperlink e clique no �cone 'Criar ou Modificar Link'. ('Informa��es do Link') conter� as informa��es existentes sobre o link, caso o objeto selecionado j� tenha um link. Na caixa de texto URL, voc� tamb�m pode digitar a URL completa da p�gina que deseja exibir como hiperlink. Voc� tamb�m pode digitar as informa��es da Janela Destino (opcional) e um nome de �ncora (opcional), caso o link tenha uma �ncora.Ao finalizar, clique no bot�o 'Inserir Link' para inserir o HiperLink que voc� acabou de criar ou clique em 'Remover Link' para remover um link existente. Clicar em 'Cancelar' fechar� a janela e o levar� de volta ao editor.Para modificar um hiperlink existente, selecione o link e clique no �cone 'Criar ou Modificar Link'. A janela HiperLink aparecer�. Fa�a suas altera��es e selecione o bot�o 'Inserir Link'. Selecione 'Remover Link' para remover um link existente.");
		mensagens.put("sTxtHelpIMAnchorTitle", "Inserir/Modificar �ncora");
		mensagens.put("sTxtHelpIMAnchor", "Para inserir uma �ncora, selecione um local desejado na p�gina que voc� est� editando e clique no �cone 'Inserir/Modificar �ncora'. Na caixa de di�logo, digite o nome da �ncora.Ao finalizar, clique no bot�o 'Inserir �ncora' para inserir a �ncora ou 'Cancelar' para fechar a caixa.Para modificar uma �ncora, selecione a �ncora (exibida como uma caixinha amarela quando a op��o de Linha-Guia estiver ativada) e clique no �cone 'Inserir/Modificar �ncora'. Fa�a suas altera��es e selecione o bot�o 'Modificar �ncora' ou clique em 'Cancelar' para fechar a janela.");
		mensagens.put("sTxtHelpCELinkTitle", "Criar Link de E-mail");
		mensagens.put("sTxtHelpCELink", "Para criar um link de e-mail, na p�gina web que est� editando, selecione um texto ou uma imagem no local em que deseja que o link apare�a. Clique no �cone 'Criar Link de E-mail'. Na caixa de di�logo, digite o endere�o de e-mail para o link e o assunto do e-mail.Ao finalizar, clique no bot�o 'Inserir Link' para inserir o link de e-mail ou 'Cancelar' para fechar a caixa.");
		mensagens.put("sTxtHelpFontTitle", "Fonte");
		mensagens.put("sTxtHelpFont", "Para alterar a fonte do texto, selecione o texto desejado e clique no menu suspenso 'Fonte'.Selecione a fonte desejada (escolha Default, Times New Roman, Arial, Verdana, Tahoma, Courier New ou Georgia).");
		mensagens.put("sTxtHelpFSizeTitle", "Tamanho da Fonte");
		mensagens.put("sTxtHelpFSize", "Para alterar o tamanho do texto, selecione o texto desejado e clique no menu suspenso 'Tamanho'.Selecione o tamanho desejado (tamanho do texto de 1 a 7).");
		mensagens.put("sTxtHelpFormatTitle", "Formato");
		mensagens.put("sTxtHelpFormat", "Para alterar o formato do texto, selecione o texto desejado e clique no menu suspenso 'Formato'.Selecione o formato desejado (escolha Normal, Sobrescrito, Subscrito e H1-H6).");
		mensagens.put("sTxtHelpStyleTitle", "Estilo");
		mensagens.put("sTxtHelpStyle", "Para alterar o estilo de texto, imagens, objetos de formul�rio, tabelas, c�lulas de tabela, etc, selecione o elemento desejado e clique no menu suspenso 'Estilo', que exibir� todos os estilos definidos na lista de estilos.Selecione o estilo desejado.Nota: este recurso n�o funcionar� se n�o houver nenhuma lista de estilos aplicada � p�gina.");
		mensagens.put("sTxtHelpFColorTitle", "Cor da Fonte");
		mensagens.put("sTxtHelpFColor", "Para alterar a cor do texto, selecione a �rea de texto desejada e clique no menu suspenso 'Cor'.No menu de sele��o, selecione a cor desejada.");
		mensagens.put("sTxtHelpHColorTitle", "Real�ar");
		mensagens.put("sTxtHelpHColor", "Para alterar a cor de realce do texto, selecione a �rea de texto desejada e clique no menu suspenso 'Real�ar'.No menu de sele��o, selecione a cor desejada.");
		mensagens.put("sTxtHelpTFunctionsTitle", "Fun��es de Tabela");
		mensagens.put("sTxtHelpTFunctions", "Para inserir ou modificar uma tabela ou c�lula, selecione o �cone 'Fun��es de Tabela' para exibir uma lista das Fun��es de Tabela existentes.Se uma Fun��o de Tabela N�O estiver dispon�vel, voc� precisar� selecionar ou posicionar seu cursor dentro da tabela que deseja modificar.");
		mensagens.put("sTxtHelpITableTitle", "Inserir Tabela");
		mensagens.put("sTxtHelpITable", "Para inserir uma tabela, selecione o local desejado e clique no �cone 'Inserir Tabela'.Uma nova janela se abrir�, exibindo os seguintes campos: Linhas - n�mero de linhas na tabela; Colunas - n�mero de colunas na tabela; Largura - largura da tabela; Cor de Fundo - cor de fundo da tabela; Enchimento da C�lula - enchimento das c�lulas; Espa�amento da C�lula - espa�amento entre as c�lulas; Altura - altura da tabela e Borda - borda ao redor das c�lulas.Preencha os detalhes da tabela e clique no bot�o 'Inserir Tabela' para inserir uma tabela ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpMTPropertiesTitle", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtHelpMTProperties", "Para modificar propriedades da tabela, selecione uma tabela ou clique em qualquer lugar dentro da tabela a modificar e, ent�o, clique no �cone 'Modificar Propriedades de Tabela'.Uma janela se abrir�, exibindo as propriedades da tabela. Clique no bot�o 'Modificar Propriedades de Tabela' para salvar suas altera��es ou clique em 'Cancelar' para retornar ao editor.Nota: esta fun��o n�o funcionar� se nenhuma tabela tiver sido selecionada.");
		mensagens.put("sTxtHelpMCPropertiesTitle", "Modificar Propriedades de C�lula");
		mensagens.put("sTxtHelpMCProperties", "Para modificar propriedades da c�lula, clique dentro da c�lula a ser modificada e, ent�o, clique no �cone 'Modificar Propriedades de C�lula'.Uma janela se abrir�, exibindo as propriedades de c�lula.Clique no bot�o 'Modificar Propriedades de C�lula' para salvar suas altera��es ou clique em 'Cancelar' para retornar ao editor.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada e tamb�m n�o funciona com c�lulas m�ltiplas.");
		mensagens.put("sTxtHelpICttRightTitle", "Inserir Coluna � Direita");
		mensagens.put("sTxtHelpICttRight", "Para inserir uma coluna � direita do seu cursor, clique dentro da c�lula ap�s a qual se inserir� uma coluna e, ent�o, clique no �cone 'Inserir Coluna � Direita'.Cada clique consecutivo inserir� uma outra coluna ap�s a c�lula selecionada.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpICttLeftTitle", "Inserir Coluna � Esquerda");
		mensagens.put("sTxtHelpICttLeft", "Para inserir coluna � esquerda do seu cursor, clique dentro da c�lula antes da qual se inserir� uma coluna e, ent�o, clique no �cone 'Inserir Coluna � Esquerda'.Cada clique consecutivo inserir� uma outra coluna antes da c�lula selecionada.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpIRAboveTitle", "Inserir Linha Acima");
		mensagens.put("sTxtHelpIRAbove", "Para inserir uma linha acima, clique dentro da c�lula acima da qual se inserir� uma linha e, ent�o, clique no �cone 'Inserir Linha Acima'.Cada clique consecutivo inserir� uma outra linha acima da c�lula selecionada.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpIRBelowTitle", "Inserir Linha Abaixo");
		mensagens.put("sTxtHelpIRBelow", "Para inserir uma linha abaixo, clique dentro da c�lula abaixo da qual se inserir� uma linha e, ent�o, clique no �cone 'Inserir Linha Abaixo'.Cada clique consecutivo inserir� uma outra abaixo da c�lula selecionada.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpDRowTitle", "Excluir Linha");
		mensagens.put("sTxtHelpDRow", "Para excluir uma linha, clique dentro da c�lula da linha a ser exclu�da e clique no �cone 'Excluir Linha'.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpIColumnTitle", "Inserir Coluna");
		mensagens.put("sTxtHelpIColumn", "Para inserir uma coluna, clique dentro da c�lula da coluna a ser inserida e clique no �cone 'Inserir Coluna'.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpDColumnTitle", "Excluir Coluna");
		mensagens.put("sTxtHelpDColumn", "Para excluir uma coluna, clique dentro da c�lula da coluna a ser exclu�da e clique no �cone 'Excluir Coluna'.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpICSpanTitle", "Mesclar Coluna");
		mensagens.put("sTxtHelpICSpan", "Para mesclar colunas, clique dentro da c�lula onde est� a coluna que ser� mesclada e clique no �cone 'Mesclar Coluna'.Cada clique consecutivo mesclar� colunas da c�lula selecionada.Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpDCSpanTitle", "Dividir Coluna");
		mensagens.put("sTxtHelpDCSpan", "Para dividir colunas, clique dentro da c�lula na qual as colunas ser�o divididas e clique no �cone 'Dividir Coluna'.Cada clique consecutivo dividir� colunas da c�lula selecionada. Nota: esta fun��o n�o funcionar� se nenhuma c�lula tiver sido selecionada.");
		mensagens.put("sTxtHelpFFunctionsTitle", "Fun��es de Formul�rio");
		mensagens.put("sTxtHelpFFunctions", "Para inserir ou modificar um Formul�rio, selecione o �cone 'Fun��es de Formul�rio' para exbir uma lista de fun��es de formul�rio dispon�veis.Se uma Fun��o de Formul�rio N�O estiver dispon�vel, voc� precisar� posicionar seu cursor dentro do Formul�rio que deseja modificar.");
		mensagens.put("sTxtHelpIFormTitle", "Inserir Formul�rio");
		mensagens.put("sTxtHelpIForm", "Para inserir um formul�rio, selecione a posi��o desejada e clique no �cone 'Inserir Formul�rio'.Uma nova janela se abrir�, exibindo os seguintes campos: Nome - nome do formul�rio; A��o - local do script que processa o formul�rio e M�todo - post, get ou nenhum.Preencha os detalhes do formul�rio ou deixe em branco, se desejado.Ao finalizar, clique no bot�o 'Inserir Formul�rio' para inserir formul�rio ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpMFPropertiesTitle", "Modificar Propriedades de Formul�rio");
		mensagens.put("sTxtHelpMFProperties", "Para modificar propriedades do formul�rio, clique em qualquer lugar dentro do formul�rio que ser� modificado e, ent�o, clique no �cone 'Modificar Propriedades de Formul�rio'.Uma janela se abrir�, exibindo as propriedades do formul�rio.Clique no bot�o 'Modificar Propriedades de Formul�rio' para salvar suas altera��es ou clique em 'Cancelar' para retornar ao editor. Nota: esta fun��o n�o funcionar� se nenhum formul�rio tiver sido selecionado.");
		mensagens.put("sTxtHelpIMTFieldTitle", "Inserir/Modificar Campo Texto");
		mensagens.put("sTxtHelpIMTField", "Para inserir um campo texto, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar Campo Texto'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome do campo texto; Largura em Caractere - a largura do campo texto, em caracteres; Tipo - tipo de campo texto (Texto ou Senha); Valor Inicial - texto inicial a ser exibido e Caracteres M�ximos - n�mero m�ximo de caracteres permitido.Defina os atributos e clique no bot�o 'Inserir Campo Texto' para inserir campo texto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um campo texto, selecione o campo texto desejado e clique no �cone 'Inserir/Modificar Campo Texto'.Uma janela se abrir�, exibindo os atributos do campo.Modifique os atributos desejados e clique no bot�o 'Modificar Campo Texto' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpIMTAreaTitle", "Inserir/Modificar �rea de Texto");
		mensagens.put("sTxtHelpIMTArea", "Para inserir uma �rea de texto, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar �rea de Texto'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome da �rea de texto; Largura em Caractere - a largura da �rea de texto, em caracteres; Valor Inicial - texto inicial a ser exibido e Linhas - n�mero de linhas permitido na �rea de texto.Defina os atributos e clique no bot�o 'Inserir �rea de Texto' para inserir a �rea de texto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de uma �rea de texto, selecione a �rea de texto desejada e clique no �cone 'Inserir/Modificar �rea de Texto'.Uma janela se abrir�, exibindo os atributos da �rea.Modifique os atributos desejados e clique no bot�o 'Modificar �rea de Texto' para salvar as altera��es or clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMHAreaTitle", "Inserir/Modificar Campo Oculto");
		mensagens.put("sTxtHelpIMHArea", "Para inserir um campo oculto, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar Campo Oculto'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome do campo oculto e Valor Inicial - valor inicial do campo oculto.Defina os atributos e clique no bot�o 'Inserir Campo Oculto' para inserir o campo oculto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um campo oculto, selecione o campo oculto desejado e clique no �cone 'Inserir/Modificar Campo Oculto'.Uma janela se abrir�, exibindo os atributos do campo oculto.Modifique os atributos desejados e clique no bot�o 'Modificar Campo Oculto' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMButtonTitle", "Inserir/Modificar Bot�o de A��o");
		mensagens.put("sTxtHelpIMButton", "Para inserir um bot�o de a��o, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar Bot�o de A��o'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome do bot�o de a��o; Tipo - tipo de bot�o (Submit, Reset ou Bot�o) e Valor Inicial - texto inicial do bot�o.Defina os atributos e clique em 'Inserir Bot�o de A��o' para inserir o bot�o ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um bot�o, selecione o bot�o desejado e clique no �cone 'Inserir/Modificar Bot�o de A��o'.Uma janela se abrir�, exibindo os atributos do bot�o.Modifique os atributos desejados e clique no bot�o 'Modificar Bot�o de A��o' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMCheckboxTitle", "Inserir/Modificar Caixa de Sele��o");
		mensagens.put("sTxtHelpIMCheckbox", "Para inserir uma caixa de sele��o, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar Caixa de Sele��o'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome da caixa de sele��o; Estado Inicial - marcado ou desmarcado e Valor Inicial - valor inicial da caixa de sele��o.Defina os atributos e clique no bot�o 'Inserir Caixa de Sele��o' para inserir a caixa de sele��o ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de uma caixa de sele��o, selecione a caixa de sele��o desejada e clique no �cone 'Inserir/Modificar Caixa de Sele��o'.Uma janela se abrir�, exibindo os atributos da caixa de sele��o.Modifique os atributos desejados e clique no bot�o 'Modificar Caixa de Sele��o' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMRButtonTitle", "Inserir/Modificar Bot�o de Op��o");
		mensagens.put("sTxtHelpIMRButton", "Para inserir um bot�o de op��o, selecione a posi��o desejada e clique no �cone 'Inserir/Modificar Bot�o de Op��o'.Uma janela se abrir�, exibindo os seguintes atributos: Nome - nome do bot�o de op��o; Estado Inicial - marcado ou desmarcado e Valor Inicial - valor inicial do bot�o de op��o.Defina os atributos e clique em 'Inserir Bot�o de Op��o' para inserir o bot�o de op��o ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um bot�o de op��o, selecione o bot�o de op��o desejado e clique no �cone 'Inserir/Modificar Bot�o de Op��o'.Uma janela se abrir�, exibindo os atributos do bot�o de op��o.Modifique os atributos desejados e clique no bot�o 'Modificar Bot�o de Op��o' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMImageTitle", "Inserir/Modificar Imagem");
		mensagens.put("sTxtHelpIMImage", "Se uma imagem N�O estiver selecionada, um clique neste �cone abrir� o Gerenciador de Imagens. <a href=//#image//>Clique aqui para obter mais informa��es sobre o gerenciador de imagens</a>.Se uma imagem ESTIVER selecionada, um clique neste �cone abrir� a janela 'Modificar Propriedades de Imagem'.Para modificar as propriedades de imagem da imagem selecionada, defina os atributos necess�rios e clique no bot�o 'Modificar Propriedades de Imagem'.");
		mensagens.put("sTxtHelpTextboxTitle", "Inserir Caixa de Texto");
		mensagens.put("sTxtHelpTextbox", "Para adicionar uma caixa de texto em qualquer lugar da p�gina, selecione o local em que deseja posicion�-la e clique no �cone 'Inserir Caixa de Texto', que adicionar� uma caixa de texto no local especificado.Para reajustar a caixa de texto, clique na moldura da caixa de texto (ative o recurso 'mostrar/ocultar linhas-guia' se voc� n�o puder visualizar o contorno da caixa de texto). Clique ent�o ao lado/canto da moldura cujo tamanho deve ser reajustado e arraste at� alcan�ar o tamanho adequado. O texto que voc� digitar pertencer� � caixa de texto e se estender� junto com a caixa de texto.");
		mensagens.put("sTxtHelpAbsoluteTitle", "Trocar Posicionamento Absoluto");
		mensagens.put("sTxtHelpAbsolute", "Para posicionar uma caixa de texto ou imagem usando o posicionamento absoluto, selecione a caixa de texto ou a imagem e clique no �cone 'Posicionamento Absoluto'. Voc� agora pode clicar e arrastar uma imagem ou caixa de texto at� o local em que voc� deseja posicion�-la na janela ativa.");
		mensagens.put("sTxtHelpISCharactersTitle", "Inserir Caracteres Especiais");
		mensagens.put("sTxtHelpISCharacters", "Para inserir um caractere especial, clique no �cone 'Inserir Caractere Especial'.Uma janela se abrir�, exibindo uma lista de caracteres especiais.Clique no �cone de caractere para inseri-lo em sua p�gina web.");
		mensagens.put("sTxtHelpMPPropertiesTitle", "Modificar Propriedades da P�gina");
		mensagens.put("sTxtHelpMPProperties", "Para modificar propriedades da p�gina, clique no �cone 'Modificar Propriedades da P�gina'.Uma janela se abrir�, exibindo os atributos de p�gina: T�tulo da P�gina - t�tulo da p�gina; Descri��o - descri��o da p�gina; Imagem de Fundo - A URL da imagem atualmente definida com a imagem de fundo da p�gina; Palavras-Chave - palavras-chave que devem ser reconhecidas pela p�gina; Cor de Fundo - a cor de fundo da p�gina; Cor de Texto - cor do texto na p�gina e Cor do Link - a cor dos links na p�gina.Ao finalizar a modifica��o, clique no bot�o 'Modificar P�gina' para salvar as altera��es ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpCUHTMLCodeTitle", " Limpar C�digo HTML");
		mensagens.put("sTxtHelpCUHTMLCode", "Para limpar o c�digo HTML, clique no �cone 'Limpar C�digo HTML'.Esta opera��o limpa um c�digo de HTML incompleto e irregular.� um recurso �til quando se copia e cola de documentos do Microsoft Word e se deseja remover c�digo HTML desnecess�rio. ");
		mensagens.put("sTxtHelpCustomHTMLTitle", "Inserir C�digo HTML Customizado");
		mensagens.put("sTxtHelpCustomHTML", "� poss�vel haver uma lista de itens dispon�veis a inserir e que voc� deseje pr�-visualizar e selecionar. Em geral, esta lista conter� itens customizados em HTML, tais como logos e texto formatado espec�ficos ao seu site. Para pr�-visualizar um item, clique no item da lista e ele aparecer� no campo de pr�-visualiza��o abaixo. Para selecionar o item, clique nele e escolha 'Inserir HTML'.");
		mensagens.put("sTxtHelpSHGuidelinesTitle", "Mostrar/Ocultar Linhas-Guia");
		mensagens.put("sTxtHelpSHGuidelines", "Para mostrar ou ocultar linhas-guia, clique no �cone 'Mostrar/Ocultar Linhas-Guia'.Este recurso ativar� ou n�o a exibi��o de linhas-guia de tabelas e de formul�rio.Quando o recurso de exibir linhas-guia estiver ativado, as tabelas e c�lulas estar�o envolvidas por uma linha cinza pontilhada, os formul�rios estar�o envolvidos por uma linha vermelha pontilhada e os campos ocultos assumir�o a forma de um quadrado rosa.Note que a barra de status (na parte inferior da janela) refletir� o modo que est� atualmente em uso.");
		mensagens.put("sTxtHelpSModeTitle", "Modo C�digo Fonte HTML");
		mensagens.put("sTxtHelpSMode", " Para mudar para o modo de edi��o de c�digo-fonte, clique no bot�o 'Fonte' na parte inferior do editor.Este passo mudar� para o modo de edi��o de c�digo HTML.Para voltar para o editor de HTML, clique no bot�o 'Editar', localizado na parte inferior do editor.Este modo � recomendado apenas para usu�rios avan�ados.");
		mensagens.put("sTxtHelpPModeTitle", "Modo de Pr�-Visualiza��o");
		mensagens.put("sTxtHelpPMode", "Para apresentar uma pr�-visualiza��o da p�gina que est� sendo editada, clique no bot�o 'Pr�-Visualizar' na parte inferior do editor.� um recurso muito �til, pois exibe a p�gina com a mesma apar�ncia que ter� em seu navegador antes que as altera��es sejam salvas.Para voltar ao modo de edi��o, clique no bot�o 'Editar' na parte inferior do editor.");
		mensagens.put("sTxtHelpImageManager", "Gerenciador de Imagens");
		mensagens.put("sTxtHelpBTTOP", "Voltar ao Topo");
		mensagens.put("sTxtHelpImageDescription", "O Gerenciador de Imagens � chamado sempre que voc� pr�-visualizar, inserir, excluir e carregar seus arquivos de imagem.Voc� pode realizar uma manuten��o geral em suas imagens a partir do Gerenciador de Imagens - inserir, definir como fundo, carregar, visualizar e excluir imagens.");
		mensagens.put("sTxtHelpVaImageTitle", "Visualizando uma Imagem");
		mensagens.put("sTxtHelpVaImage", "Para visualizar uma imagem, selecione a imagem desejada e clique no link 'Visualizar'.A imagem ser� mostrada em uma janela em seu tamanho natural.Feche a janela para retornar ao Gerenciador de Imagens.");
		mensagens.put("sTxtHelpIaImageTitle", "Inserindo uma Imagem");
		mensagens.put("sTxtHelpIaImage", "`Para inserir uma imagem, clique no link 'Inserir' no navegador de imagem pr�ximo � imagem a ser inserida.");
		mensagens.put("sTxtHelpSBImageTitle", "Definir Imagem de Fundo");
		mensagens.put("sTxtHelpSBImage", "Para definir uma imagem como uma imagem de fundo, clique no link 'Fundo' no navegador de imagem pr�ximo � imagem a ser definida.A imagem ser� definida como a imagem de fundo da p�gina atual.");
		mensagens.put("sTxtHelpDaImageTitle", "Excluir uma Imagem");
		mensagens.put("sTxtHelpDaImage", "Para excluir uma imagem, selecione a imagem desejada e clique no link 'Excluir'.Uma mensagem pedir� a confirma��o da exclus�o.Se tiver certeza de que deseja excluir a imagem selecionada, clique em 'OK'.Clicar em 'Cancelar' retornar� ao Gerenciador de Imagens.");
		mensagens.put("sTxtHelpUaImageTitle", "Enviar uma Imagem para o Servidor");
		mensagens.put("sTxtHelpUaImage", "Para enviar uma imagem para o servidor (upload), clique no bot�o 'Procurar' para abrir uma caixa 'Escolher Arquivo', que permite que voc� selecione uma imagem local a ser enviada.Assim que o arquivo tiver sido selecionado, clique em 'OK' para iniciar o envio do arquivo ou clique em 'Cancelar' para retornar ao Gerenciador de Imagens.Se a imagem tiver sido enviada com sucesso, ela aparecer� no Gerenciador de Imagens.");
		mensagens.put("sTxtSave", "Salvar");
		mensagens.put("sTxtNone", "Nenhum");
		mensagens.put("sTxtMoreColors", "Mais Cores");
		mensagens.put("sTxtCheckSpelling", "Checar Ortografia");
		mensagens.put("sTxtUpload", "Upload");
		mensagens.put("sTxtSwitch", "Trocar");
		mensagens.put("sTxtImageDirNotConfigured", "Diret�rio de imagens n�o configurado corretamente");
		mensagens.put("sTxtFlashDirNotConfigured", "Diret�rio de arquivos 'flash' n�o configurado corretamente");
		mensagens.put("sTxtImageProperties", "Propriedades da Imagem");
		mensagens.put("sTxtFlashProperties", "Propriedades do Flash");
		mensagens.put("sTxtHelpIMFlashTitle", "Insere/Modifica Flash");
		mensagens.put("sTxtHelpIMFlash", "Se nenhum arquivo Flash estiver selecionado, um clique neste �cone abrir� o Gerenciador de Flash. Se algum arquivo flash estiver selecionado, um clique neste �cone abrir� a janela popup 'Modificar Propriedades do Flash'. Para modificar as propriedades do arquivo flash selecionado, defina os atributos necess�rios e clique no bot�o 'Modificar'.");
		mensagens.put("sTxtDefaultImageLibrary", "Biblioteca de Imagens Padr�o");
		mensagens.put("sTxtDefaultFlashLibrary", "Biblioteca de Flash Padr�o");
		mensagens.put("sTxtLoad", "Carregar");
		mensagens.put("sTxtInsertSelect", "Insere Campo Sele��o");
		mensagens.put("sTxtInsertSelectInst", "Digite as informa��es necess�rias e clique no bot�o OK para inserir o campo de sele��o em sua p�gina web.");
		mensagens.put("sTxtText", "Texto");
		mensagens.put("sTxtValue", "Valor");
		mensagens.put("sTxtSelected", "Selecionado");
		mensagens.put("sTxtSingle", "Selec�o �nica");
		mensagens.put("sTxtMultiple", "Sele��o Multipla");
		mensagens.put("sTxtAdd", "Adiciona");
		mensagens.put("sTxtUpdate", "Atualiza");
		mensagens.put("sTxtDelete", "Exclui");
		mensagens.put("sTxtMaintainOptions", "Op��es de Manuten��o");
		mensagens.put("sTxtCurrentOptions", "Op��es Atuais");
		mensagens.put("sTxtModifySelect", "Modifica Sele��o");
		mensagens.put("sTxtModifySelectInst", "Digite as informa��es necess�rias e clique no bot�o OK para modificar o campo de sele��o em sua p�gina web.");
		mensagens.put("sTxtSelect", "Insere / Modifica Campo Sele��o");
		mensagens.put("sTxtHelpIMSListTitle", "Insere / Modifica Campo Sele��o");
		mensagens.put("sTxtHelpIMSList", "Para inserir um campo de sele��o, selecione a posi��o desejada e clique no �cone 'Insere / Modifica Campo Sele��o'.Uma janela se abrir� com os seguintes atributos: Nome - nome do campo de sele��o;Op��es Atuais - As op��es dispon�veis para sele��o na lista;Tipo - como a lista de op��es ser� mostrada ( op��o �nica ou m�ltipla );Tamanho - quantos �tens ser�o mostrados;Estilo - Estilo a ser aplicado no campo de sele��o, se houver algum.Para adicionar op��es no campo de sele��o, use o texto, valor e caixa [selecionado] abaixo do cabe�alho [Op��es de Manuten��o].Para modificar as propriedades da lista, selecione a lista desejada e clique no bot�o 'Insere / Modifica Campo Sele��o'.Uma janela se abrir� com os atributos do campo de sele��o.Modifique os atributos desejados e clique no bot�o 'OK' para salvar as altera��es ou clique no bot�o 'Cancela' para voltar ao editor.");

	}

    private String encodeHtmlString(String text) {
        if (text == null) {
            return null;
        }
        if (text.length() == 0) {
            return text;
        }
        StringBuffer bf = new StringBuffer(text.length());
        for (int i = 0; i < text.length(); i++) {
            char cc = text.charAt(i);
            boolean found = false;
            for (int j = 0; j < ccvsts.length; j++) {
                if (cc == ccvsts[j]) {
                    bf.append(scvsts[j]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (cc < ' ' || cc > 127) {
                    bf.append("&#").append((int) cc).append(";");
                } else {
                    bf.append(cc);
                }
            }
        }

        return bf.toString();
    }

	public void loadIncFolder() {

		if (StringUtils.isEmpty(incFolder.toString())) return;

		loadMessages();

        final Collection coll = FileUtils.listFiles(incFolder, new String[] {"inc"}, true);

        for (Iterator it = coll.iterator(); it.hasNext();) {
            final File file = (File) it.next();
            String texto = "";
    		try {
    			texto = IOUtils.toString(new FileInputStream(file));
    			texto = traduzMensagens(texto);

            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            paginasInc.put(file.getName(), texto);
            //System.out.println(file.getName() + " / tamanho = " + file.length());
        }

	}

	public String getTextoInc( String incFileRef ) {
		return (String) paginasInc.get(incFileRef);
	}

	private String traduzMensagens( String texto ) {

		int pini = 0;
		int pnxt = 0;
		int pfim = 0;
		String marca = "";
		String msg = "";
		String txtOk = texto;

		while (pini < texto.length()) {
			pnxt = texto.indexOf("<%=", pini);
			if (pnxt >= 0) {
				if (debug)
					System.out.println("Marca encontrada em " + pnxt);
				pfim = texto.indexOf("%>", pnxt+3);
				if (pfim > 0) {

					marca = texto.substring(pnxt,pfim+2);
					msg = marca.substring(3, marca.length()-2).trim();
					txtOk = StringUtils.replace( txtOk, marca, encodeHtmlString((String)mensagens.get(msg)));

					if (debug)
						System.out.println("Marca = " + marca +" e Msg = " + msg);

					pini = pfim + 2;
				}
			} else {
				pini = texto.length();
			}
		}

		return txtOk;

	}

	/**
	 * @return Returns the incFolder.
	 */
	public File getIncFolder() {
		return incFolder;
	}
	/**
	 * @param incFolder The incFolder to set.
	 */
	public void setIncFolder(File incFolder) {
		this.incFolder = incFolder;
		loadIncFolder();
	}

	/**
	 * @return Returns the incFolder.
	 */
	public String getPathToIncFiles() {
		return pathToIncFiles;
	}
	/**
	 * @param incFolder The incFolder to set.
	 */
	public void setPathToIncFiles(String path) {
		this.pathToIncFiles = path;
		setIncFolder(new File(path));
	}

	public static void main(String[] args) {

		HEUtil heu = new HEUtil();
		heu.setIncFolder( new File("C:/testes/he_includes"));
		//heu.loadIncFolder();
		System.out.println(heu.getTextoInc("insert_form.inc"));
	}
}