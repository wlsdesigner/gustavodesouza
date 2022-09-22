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
            '<', '>', '&', '"', '©', '®', 'À', 'Á', 'Â', 'Ã', 'Ä', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ò', 'Ó', 'Ô',
            'Õ', 'Ö', 'Ù', 'Ú', 'Û', 'Ü', 'Ç', 'à', 'á', 'â', 'ã', 'ä', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ò', 'ó',
            'ô', 'õ', 'ö', 'ù', 'ú', 'û', 'ü', 'ç'
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
		mensagens.put("sTxtCloseWin", "Clique no botão Cancelar para fechar esta janela.");
		mensagens.put("sTxtReturn", "Clique em 'Cancelar' para retornar à tela anterior.");
		mensagens.put("sTxtName", "Nome");
		mensagens.put("sTxtBorder", "Borda");
		mensagens.put("sTxtBgColor", "Cor de Fundo");
		mensagens.put("sTxtAction", "Ação");
		mensagens.put("sTxtRename", "Renomear");
		mensagens.put("sTxtBytes", "Bytes");
		mensagens.put("sTxtFile", "Arquivo");
		mensagens.put("sTxtTextAreaError", "Seu navegador deve ser IE5.5 ou versão superior para exibir o Editor de HTML. Em seu lugar será exibida uma caixa de texto simples.");
		mensagens.put("sTxtContextMenuWidth", "225");
		mensagens.put("sTxtColors", "Cores");
		mensagens.put("sTxtColorsInst", "Selecione a cor desejada e clique 'OK' para usar a cor selecionada.");
		mensagens.put("sTxtFindError", "Por favor, digite o texto no campo 'Localizar:'.");
		mensagens.put("sTxtFindNotFound", "Sua palavra não foi localizada.Você gostaria de iniciar novamente pelo topo?");
		mensagens.put("sTxtFindNotReplaced", "Palavra não localizada. Nada foi substituído.");
		mensagens.put("sTxtFindReplaced", " palavra(s) foi(foram) substituída(s).");
		mensagens.put("sTxtFindFindWhat", "Localizar:");
		mensagens.put("sTxtFindReplaceWith", "Substituir por:");
		mensagens.put("sTxtFindMatchCase", "Diferenciar maiúscula/minúscula");
		mensagens.put("sTxtFindMatchWord", "Coincidir apenas palavra inteira");
		mensagens.put("sTxtFindNext", "Localizar Próxima");
		mensagens.put("sTxtFindClose", "Fechar");
		mensagens.put("sTxtFindReplaceText", "Substituir");
		mensagens.put("sTxtFindReplaceAll", "Substituir Tudo");
		mensagens.put("sTxtInsertFlash", "Insere Flash");
		mensagens.put("sTxtModifyFlash", "Modifica Propriedades do Flash");
		mensagens.put("sTxtExternalFlash", "Flash Externo");
		mensagens.put("sTxtInternalFlash", "Flash Interno ");
		mensagens.put("sTxtInsertFlashInst", "Informe o endereço do flash a ser inserido ou selecione um flash na lista abaixo. Clique 'Insere' para inserir o flash.");
		mensagens.put("sTxtUploadSuccess", " enviado com sucesso");
		mensagens.put("sTxtUploadFlash", "Upload Flash");
		mensagens.put("sTxtFlashErr", "O arquivo que você enviou não é um Flash válido");
		mensagens.put("sTxtFlashExists", "já existe. Você confirma a substituição do mesmo?");
		mensagens.put("sTxtLoop", "Loop");
		mensagens.put("sTxtChooseFlash", "Por favor, primeiro escolha um arquivo flash para enviar!");
		mensagens.put("sTxtCantDelete", "O arquivo selecionado não pode ser excluído");
		mensagens.put("sTxtCantUpload", "O arquivo selecionado não pode ser enviado");
		mensagens.put("sTxtFlashWidthErr", "A largura do flash deve ser um número positivo válido");
		mensagens.put("sTxtFlashHeightErr", "A altura do flash deve ser um número positivo válido");
		mensagens.put("sTxtFlashWidth", "Largura Flash ");
		mensagens.put("sTxtFlashHeight", "Altura Flash ");
		mensagens.put("sTxtInsertImage", "Inserir Imagem");
		mensagens.put("sTxtExternalImage", "Imagem Externa");
		mensagens.put("sTxtInternalImage", "Imagem Interna");
		mensagens.put("sTxtInsertImageInst", "Digite a URL da imagem a inserir ou Escolha uma das imagens mostradas abaixo e clique em seu link de inserção para adicioná-la.");
		mensagens.put("sTxtUploadImage", "Upload (Enviar Imagem)");
		mensagens.put("sTxtImageName", "Nome do Arquivo");
		mensagens.put("sTxtFileSize", "Tamanho do Arquivo");
		mensagens.put("sTxtImageView", "Visualizar");
		mensagens.put("sTxtImageInsert", "Inserir");
		mensagens.put("sTxtImageDel", "Excluir");
		mensagens.put("sTxtImageBackgd", "Fundo");
		mensagens.put("sTxtImageErr", "O arquivo que você enviou não continha uma imagem válida");
		mensagens.put("sTxtImageSuccess", "enviada com sucesso");
		mensagens.put("sTxtImageSetBackgd", "Você tem certeza que deseja definir esta imagem como a imagem de fundo da página?");
		mensagens.put("sTxtInvalidImageType", "O arquivo selecionado não é uma imagem válida");
		mensagens.put("sTxtEmptyImageLibrary", "[A coleção de imagens selecionada está vazia]");
		mensagens.put("sTxtImageModify", "Modificar");
		mensagens.put("sTxtImageExists", "já existe. Você realmente quer sobrescrever?");
		mensagens.put("sTxtChooseImage", "Por favor, escolha uma imagem antes!");
		mensagens.put("sTxtImageDelete", "Você realmente quer excluir esse arquivo?");
		mensagens.put("sTxtImageDeleted", "Imagem excluída com sucesso!");
		mensagens.put("sTxtLinkManager", "Gerenciador de Links");
		mensagens.put("sTxtLinkManagerInst", "Digite as informações necessárias e clique em Inserir Link para inserir um link em sua página web.");
		mensagens.put("sTxtLinkManagerInst2", "Localize o arquivo no gerenciador de arquivos abaixo e selecione Obter Localização de Links. Clique em Inserir Link para inserir o link.");
		mensagens.put("sTxtLinkErr", "A URL não pode ser deixada em branco");
		mensagens.put("sTxtLinkInfo", "Informações do Link");
		mensagens.put("sTxtUrl", "URL");
		mensagens.put("sTxtLibraryUrl", "Links Pré-definido");
		mensagens.put("sTxtTargetWin", "Janela Destino");
		mensagens.put("sTxtAnch", "Âncora");
		mensagens.put("sTxtGetLinkInfo", "Obter informações do Link para");
		mensagens.put("sTxtGetLink", "Obter Localização de Links");
		mensagens.put("sTxtInsertLink", "Inserir Link");
		mensagens.put("sTxtRemoveLink", "Remover Link");
		mensagens.put("sTxtInsertAnchorErr", "Nome da Âncora não pode ser deixado em branco");
		mensagens.put("sTxtInsertAnchorName", "Nome da Âncora");
		mensagens.put("sTxtInsertAnchor", "Inserir Âncora");
		mensagens.put("sTxtInsertAnchorInst", "Digite as informações necessárias e clique em OK para inserir uma âncora em sua página web.");
		mensagens.put("sTxtModifyAnchor", "Modificar Âncora");
		mensagens.put("sTxtModifyAnchorInst", "Digite as informações necessárias e clique em OK para modificar a âncora atual.");
		mensagens.put("sTxtInsertButton", "Inserir Botão de Ação");
		mensagens.put("sTxtInsertButtonInst", "Digite as informações necessárias e clique em OK para inserir um botão em sua página web.");
		mensagens.put("sTxtModifyButton", "Modificar Botão de Ação");
		mensagens.put("sTxtModifyButtonInst", "Digite as informações necessárias e clique em OK para modificar o botão atual.");
		mensagens.put("sTxtInsertChar", "Inserir Caracteres Especiais");
		mensagens.put("sTxtInsertCharInst", "Selecione o caractere que você necessita e clique em Inserir Caractere para inserir um caractere especial em sua página web.");
		mensagens.put("sTxtChar", "Caractere");
		mensagens.put("sTxtCharToInsert", "Caractere a Inserir");
		mensagens.put("sTxtInsertCheckBox", "Inserir Caixa de Seleção");
		mensagens.put("sTxtInsertCheckBoxInst", "Digite as informações necessárias e clique em OK para inserir uma caixa de seleção em sua página web.");
		mensagens.put("sTxtModifyCheckBox", "Modificar Caixa de Seleção");
		mensagens.put("sTxtModifyCheckBoxInst", "Digite as informações necessárias e clique em OK para modificar uma caixa de seleção selecionada.");
		mensagens.put("sTxtEmailErr", "Endereço de E-mail não pode ser deixado em branco");
		mensagens.put("sTxtInsertEmail", "Inserir Link de E-mail");
		mensagens.put("sTxtRemoveEmailLink", "Remover Link de E-mail");
		mensagens.put("sTxtInsertEmailInst", "Digite as informações necessárias e clique em Inserir Link para criar um link com o endereço de e-mail em sua página web.");
		mensagens.put("sTxtEmailAddress", "Endereço de E-mail");
		mensagens.put("sTxtSubject", "Assunto");
		mensagens.put("sTxtInsertEmailLink", "Inserir Link de E-mail");
		mensagens.put("sTxtInsertHidden", "Inserir Campo Oculto");
		mensagens.put("sTxtInsertHiddenInst", "Digite as informações necessárias e clique em OK para inserir um campo oculto em sua página web.");
		mensagens.put("sTxtModifyField", "Modificar Campo Oculto");
		mensagens.put("sTxtModifyFieldInst", "Digite as informações necessárias e clique em OK para modificar o campo oculto selecionado.");
		mensagens.put("sTxtInsertRadio", "Inserir Botão de Opção");
		mensagens.put("sTxtInsertRadioInst", "Digite as informações necessárias e clique em OK para inserir um botão de opção em sua página web.");
		mensagens.put("sTxtModifyRadio", "Modificar Botão de Opção");
		mensagens.put("sTxtModifyRadioInst", "Digite as informações necessárias e clique em OK para modificar um botão de opção selecionado.");
		mensagens.put("sTxtTableRowErr", "Linhas devem conter um número positivo e válido");
		mensagens.put("sTxtTableColErr", "Colunas devem conter um número positivo e válido");
		mensagens.put("sTxtTableWidthErr", "Largura deve conter um número positivo e válido");
		mensagens.put("sTxtTablePaddingErr", "Enchimento de Célula deve conter um número positivo e válido");
		mensagens.put("sTxtTableSpacingErr", "Espaçamento de Célula deve conter um número positivo e válido");
		mensagens.put("sTxtTableBorderErr", "Borda deve conter um número positivo e válido");
		mensagens.put("sTxtRows", "Linhas");
		mensagens.put("sTxtPadding", "Enchimento de Célula");
		mensagens.put("sTxtSpacing", "Espaçamento de Célula");
		mensagens.put("sTxtWidth", "Largura");
		mensagens.put("sTxtHeight", "Altura");
		mensagens.put("sTxtCols", "Colunas");
		mensagens.put("sTxtInsertTable", "Inserir Tabela");
		mensagens.put("sTxtInsertTableInst", "Digite as informações necessárias e clique em OK para inserir uma tabela em sua página web.");
		mensagens.put("sTxtModifyTable", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtModifyTableInst", "Digite as informações necessárias e clique em OK para modificar as propriedades de tabela da sua tabela.");
		mensagens.put("sTxtCellWidthErr", "Largura da Célula deve conter um número positivo e válido");
		mensagens.put("sTxtCellHeightErr", "Altura da Célula deve conter um número positivo e válido");
		mensagens.put("sTxtModifyCell", "Modificar Propriedades da Célula");
		mensagens.put("sTxtModifyCellInst", "Digite as informações necessárias e clique em OK para modificar as propriedades de célula.");
		mensagens.put("sTxtCellWidth", "Largura da Célula");
		mensagens.put("sTxtCellHeight", "Altura da Célula");
		mensagens.put("sTxtHorizontalAlign", "Alinhamento Horizontal");
		mensagens.put("sTxtVerticalAlign", "Alinhamento Vertical");
		mensagens.put("sTxtCharWidthErr", "Largura do Caractere deve conter um número positivo e válido");
		mensagens.put("sTxtLinesErr", "Linhas devem conter um número positivo e válido");
		mensagens.put("sTxtMaxCharsErr", "Caracteres Máximos devem conter um número positivo e válido");
		mensagens.put("sTxtInitialValue", "Valor Inicial");
		mensagens.put("sTxtInitialState", "Estado Inicial");
		mensagens.put("sTxtCharWidth", "Largura");
		mensagens.put("sTxtLines", "Linhas");
		mensagens.put("sTxtType", "Tipo");
		mensagens.put("sTxtMaxChars", "Caracteres Máximos");
		mensagens.put("sTxtMethod", "Método");
		mensagens.put("sTxtInsertForm", "Inserir Formulário");
		mensagens.put("sTxtInsertFormInst", "Digite as informações necessárias e clique em OK para inserir um formulário em sua página web.");
		mensagens.put("sTxtModifyForm", "Modificar Propriedades do Formulário");
		mensagens.put("sTxtModifyFormInst", "Digite as informações necessárias e clique em OK para modificar as propriedades de formulário de seu formulário.");
		mensagens.put("sTxtInsertTextArea", "Inserir Área de Texto");
		mensagens.put("sTxtInsertTextAreaInst", "Digite as informações necessárias e clique em OK para inserir uma área de texto em sua página web.");
		mensagens.put("sTxtModifyTextArea", "Modificar Área de Texto");
		mensagens.put("sTxtModifyTextAreaInst", "Digite as informações necessárias e clique em OK para modificar uma área de texto selecionada.");
		mensagens.put("sTxtInsertTextField", "Inserir Campo Texto");
		mensagens.put("sTxtInsertTextFieldInst", "Digite as informações necessárias e clique em OK para inserir um campo texto em sua página web.");
		mensagens.put("sTxtModifyTextField", "Modificar Campo Texto");
		mensagens.put("sTxtModifyTextFieldInst", "Digite as informações necessárias e clique em OK para modificar o campo texto selecionado.");
		mensagens.put("sTxtSetBackgd", "Você tem certeza de que deseja definir esta imagem como imagem de fundo da página?");
		mensagens.put("sTxtGuidelines", "Linhas-Guia");
		mensagens.put("sTxtOn", "Ativar");
		mensagens.put("sTxtOff", "Desativar");
		mensagens.put("sTxtClean", "Você tem certeza de que deseja limpar o código HTML?");
		mensagens.put("sTxtImageWidthErr", "Largura da Imagem deve conter um número positivo e válido");
		mensagens.put("sTxtImageHeightErr", "Altura da Imagem deve conter um número positivo e válido");
		mensagens.put("sTxtImageBorderErr", "Borda da Imagem deve conter um número positivo e válido");
		mensagens.put("sTxtHorizontalSpacingErr", "Espaçamento Horizontal deve conter um número positivo e válido");
		mensagens.put("sTxtVerticalSpacingErr", "Espaçamento Vertical deve conter um número positivo e válido");
		mensagens.put("sTxtModifyImage", "Modificar Propriedades da Imagem");
		mensagens.put("sTxtModifyImageInst", "Digite as informações necessárias e clique Modificar para modificar as propriedades da imagem selecionada.");
		mensagens.put("sTxtAltText", "Texto Alternativo");
		mensagens.put("sTxtImageWidth", "Largura da Imagem");
		mensagens.put("sTxtImageHeight", "Altura da Imagem");
		mensagens.put("sTxtAlignment", "Alinhamento");
		mensagens.put("sTxtHorizontalSpacing", "Espaçamento Horizontal");
		mensagens.put("sTxtVerticalSpacing", "Espaçamento Vertical");
		mensagens.put("sTxtPageProps", "Modificar Propriedades da Página");
		mensagens.put("sTxtPagePropsInst", "Digite as informações necessárias e clique em OK para modificar as propriedades da sua página.");
		mensagens.put("sTxtPageTitle", "Título da Página");
		mensagens.put("sTxtDescription", "Descrição");
		mensagens.put("sTxtKeywords", "Palavras-Chave");
		mensagens.put("sTxtBgImage", "Imagem de Fundo");
		mensagens.put("sTxtTextColor", "Cor do Texto");
		mensagens.put("sTxtLinkColor", "Cor do Link");
		mensagens.put("sTxtCustomInsert", "Inserir Código Customizado");
		mensagens.put("sTxtCustomInsertInst", "Selecione a opção Inserir Código Customizado e clique em Inserir HTML para inserir o HTML Customizado");
		mensagens.put("sTxtInsertHTML", "Inserir HTML");
		mensagens.put("sTxtCustomInsertErr", "Por favor, selecione a opção Inserir Código Customizado para inserir em sua página web");
		mensagens.put("sTxtPreview", "Pré-Visualização");
		mensagens.put("sTxtFullscreen", "Modo Tela Cheia");
		mensagens.put("sTxtCut", "Recortar");
		mensagens.put("sTxtCopy", "Copiar");
		mensagens.put("sTxtPaste", "Colar");
		mensagens.put("sTxtFindReplace", "Localizar e Substituir");
		mensagens.put("sTxtUndo", "Desfazer");
		mensagens.put("sTxtRedo", "Refazer");
		mensagens.put("sTxtRemoveFormatting", "Remover a Formatação de Texto");
		mensagens.put("sTxtBold", "Negrito");
		mensagens.put("sTxtUnderline", "Sublinhado");
		mensagens.put("sTxtItalic", "Itálico");
		mensagens.put("sTxtStrikethrough", "Tachado");
		mensagens.put("sTxtNumList", "Inserir Lista Numerada");
		mensagens.put("sTxtBulletList", "Inserir Lista com Marcadores");
		mensagens.put("sTxtDecreaseIndent", "Diminuir Recuo");
		mensagens.put("sTxtIncreaseIndent", "Aumentar Recuo");
		mensagens.put("sTxtSuperscript", "Sobrescrito");
		mensagens.put("sTxtSubscript", "Subscrito");
		mensagens.put("sTxtAlignLeft", "Alinhar à Esquerda");
		mensagens.put("sTxtAlignCenter", "Centralizar");
		mensagens.put("sTxtAlignRight", "Alinhar à Direita");
		mensagens.put("sTxtAlignJustify", "Justificar");
		mensagens.put("sTxtInsertHR", "Inserir Linha Horizontal");
		mensagens.put("sTxtHyperLink", "Criar ou Modificar Link");
		mensagens.put("sTxtAnchor", "Inserir/Modificar Âncora");
		mensagens.put("sTxtEmail", "Criar Link de E-mail");
		mensagens.put("sTxtTextbox", "Inserir Caixa de Texto");
		mensagens.put("sTxtFormFunctions", "Funções de Formulário");
		mensagens.put("sTxtForm", "Inserir Formulário");
		mensagens.put("sTxtFormModify", "Modificar Propriedades de Formulário");
		mensagens.put("sTxtTextField", "Inserir/Modificar Campo Texto");
		mensagens.put("sTxtTextArea", "Inserir/Modificar Área de Texto");
		mensagens.put("sTxtHidden", "Inserir/Modificar Campo Oculto");
		mensagens.put("sTxtButton", "Inserir/Modificar Botão de Ação");
		mensagens.put("sTxtCheckbox", "Inserir/Modificar Caixa de Seleção");
		mensagens.put("sTxtRadioButton", "Inserir/Modificar Botão de Opção");
		mensagens.put("sTxtFont", "Fonte");
		mensagens.put("sTxtSize", "Tamanho");
		mensagens.put("sTxtColor", "Cor");
		mensagens.put("sTxtFormat", "Formato");
		mensagens.put("sTxtStyle", "Estilo");
		mensagens.put("sTxtColour", "Cor de Fonte");
		mensagens.put("sTxtBackColour", "Realçar");
		mensagens.put("sTxtTableFunctions", "Funções de Tabela");
		mensagens.put("sTxtTable", "Inserir Tabela");
		mensagens.put("sTxtTableModify", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtCellModify", "Modificar Propriedades de Célula");
		mensagens.put("sTxtInsertRowA", "Inserir Linha Acima");
		mensagens.put("sTxtInsertRowB", "Inserir Linha Abaixo");
		mensagens.put("sTxtDeleteRow", "Excluir Linha");
		mensagens.put("sTxtInsertColA", "Inserir Coluna à Direita");
		mensagens.put("sTxtInsertColB", "Inserir Coluna à Esquerda");
		mensagens.put("sTxtDeleteCol", "Excluir Coluna");
		mensagens.put("sTxtIncreaseColSpan", "Mesclar Coluna");
		mensagens.put("sTxtDecreaseColSpan", "Dividir Coluna");
		mensagens.put("sTxtImage", "Inserir/Modificar Imagem");
		mensagens.put("sTxtChars", "Inserir Caracteres Especiais");
		mensagens.put("sTxtCharsInst", "Clique no caractere requerido para inseri-lo na página.");
		mensagens.put("sTxtPageProperties", "Modificar Propriedades de Página");
		mensagens.put("sTxtCleanCode", "Limpar Código HTML");
		mensagens.put("sTxtPasteWord", "Colar do MS Word");
		mensagens.put("sTxtCustomInserts", "Inserir Código HTML Customizado");
		mensagens.put("sTxtToggleGuidelines", "Mostrar/Ocultar Linhas-Guia");
		mensagens.put("sTxtTogglePosition", "Trocar Posicionamento Absoluto");
		mensagens.put("sTxtFlash", "Inserir / Modificar Flash");
		mensagens.put("sTxtFlashDeleted", "Arquivo flash removido com sucesso!");
		mensagens.put("sTxtEmptyFlashLibrary", "[A biblioteca de arquivos flash selecionada está vazia.]");
		mensagens.put("sTxtHelpTitle", "&nbsp;Os comandos do Editor de HTML");
		mensagens.put("sTxtHelp", "Ajuda");
		mensagens.put("sTxtHelpNote", "Nota: Se uma das opções abaixo não estiver visível ou acessível em seu editor, talvez tenha sido desabilitada por seu administrador.");
		mensagens.put("sTxtHelpCloseWin", "Fechar Janela");
		mensagens.put("sTxtHelpSaveTitle", "Salva");
		mensagens.put("sTxtHelpSave", "Para salvar seu trabalho, pressione o botão Salva");
		mensagens.put("sTxtHelpFullscreenTitle", "Modo Tela Cheia");
		mensagens.put("sTxtHelpFullscreen", "Para expandir a janela ativa em tela cheia, clique no ícone 'Modo Tela Cheia'. Cliques consecutivos neste ícone ativarão e desativarão este recurso.");
		mensagens.put("sTxtHelpCutTitle", "Recortar (Ctrl+X)");
		mensagens.put("sTxtHelpCut", "Para recortar uma parte do documento, selecione a área desejada e clique no ícone 'Recortar' (tecla de atalho - CTRL+X).");
		mensagens.put("sTxtHelpCopyTitle", "Copiar (Ctrl+C)");
		mensagens.put("sTxtHelpCopy", "Para copiar uma parte do documento, selecione a área desejada e clique no ícone 'Copiar' (tecla de atalho - CTRL+C).");
		mensagens.put("sTxtHelpPasteTitle", "Colar (Ctrl+V)");
		mensagens.put("sTxtHelpPaste", "Para colar uma parte que já foi recortada (ou copiada), clique no lugar em que você deseja exibi-la e clique no ícone 'Colar' (tecla de atalho  - CTRL+V).Para colar do Microsoft Word, clique no ícone próximo ao Ícone Colar.");
		mensagens.put("sTxtHelpPasteWordTitle", "Colar do Microsoft Word (Ctrl + D)");
		mensagens.put("sTxtHelpPasteWord", "Para colar do Microsoft Word: Copie o texto desejado no Microsoft Word e clique no ícone próximo ao ícone Colar. Selecione a opção 'Colar do MS Word'. Essa operação removerá não somente as tags que o Microsoft Word coloca automaticamente, mas também a maior parte da formatação.");
		mensagens.put("sTxtHelpFindReplaceTitle", "Localizar e Substituir");
		mensagens.put("sTxtHelpFindReplace", "Para localizar e substituir palavras ou frases dentro do texto: Selecione a opção de localizar e substituir. No campo 'Localizar', digite a palavra ou frase que deseja substituir.No campo 'Substituir', selecione a nova palavra ou frase que substituirá o texto localizado.Você pode escolher a opção 'Localizar Próxima', que permite a substituição manual de cada texto localizado ou você pode escolher 'Substituir Tudo', que permite a substituição de todos os textos localizados.A opção 'Diferenciar Maiúsculas/Minúsculas' permite que você busque uma palavra ou frase exatamente igual a que foi digitada no campo 'Localizar', respeitando as letras maiúsculas e minúsculas utilizadas.Selecionar a opção 'Coincidir a palavra inteira' implica na busca de palavras que coincidam exatamente com a palavra ou frase indicada no campo 'Localizar'.");
		mensagens.put("sTxtHelpUndoTitle", "Desfazer (Ctrl+Z)");
		mensagens.put("sTxtHelpUndo", "Para desfazer a última alteração, clique no ícone 'Desfazer' (tecla de atalho - CTRL+Z). Cada clique consecutivo irá desfazer a última ação realizada.");
		mensagens.put("sTxtHelpRedoTitle", "Refazer (Ctrl+Y)");
		mensagens.put("sTxtHelpRedo", "Para refazer a última ação, clique no ícone 'Refazer' (keyboard shortcut - CTRL+Y). Cada clique consecutivo repetirá a última ação realizada no documento.");
		mensagens.put("sTxtHelpSpellcheckTitle", "Verificar Ortografia (F7)");
		mensagens.put("sTxtHelpSpellcheck", "Para verificar a ortografia, selecione o texto que você gostaria de verificar (se você não selecionar o texto, todo o seu documento será verificado).Clique no ícone de verificação de ortografia ou clique com o botão direito do mouse e escolha a opção 'Verificar Ortografia'.Você verá a primeira palavra grafada incorretamente. Você, então, poderá escolher - Alterar a palavra com erro de grafia utilizando as palavras sugeridas - Ignorar a palavra com erro de grafia (não fazer quaisquer mudanças) Para verificar a ortografia de uma única palavra, selecione a palavra e clique com o botão direito do mouse para obter uma sugestão de possíveis substituições. Para substituir a palavra incorreta por uma das palavras sugeridas, simplesmente selecione uma das substituições.");
		mensagens.put("sTxtHelpRemoveFormattingTitle", "Remover Formatação de Texto");
		mensagens.put("sTxtHelpRemoveFormatting", "Este comando permite a seleção de uma parte de texto e a remoção de qualquer formatação a ele aplicada. Para remover qualquer formatação de texto, selecione a área de texto desejada e clique no botão 'Remover Formatação de Texto'.");
		mensagens.put("sTxtHelpBoldTitle", "Negrito (Ctrl+B)");
		mensagens.put("sTxtHelpBold", "Para negritar o texto, selecione o texto desejado e clique no ícone 'Negrito' (tecla de atalho - CTRL+B). Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpUnderlineTitle", "Sublinhado (Ctrl+U)");
		mensagens.put("sTxtHelpUnderline", "Para sublinhar texto, selecione o texto desejado e clique no ícone 'Sublinhado' (tecla de atalho - CTRL+U). Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpItalicTitle", "Itálico (Ctrl+I)");
		mensagens.put("sTxtHelpItalic", "Para converter o texto para itálico, selecione o texto desejado e clique no ícone 'Itálico' (tecla de atalho - CTRL+I). Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpStrikethroughTitle", "Tachado");
		mensagens.put("sTxtHelpStrikethrough", "Para aplicar o efeito de tachado ao texto, realce o texto que deseja formatar e selecione o ícone 'Tachado'. Cada clique consecutivo ativará e desativará esta função");
		mensagens.put("sTxtHelpINListTitle", "Inserir Lista Numerada");
		mensagens.put("sTxtHelpINList", "Para criar uma lista de texto numerada, clique no ícone 'Inserir Lista Numerada'. Se o texto já tiver sido selecionado, este será convertido em uma lista sequencial e numerada. Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpIBListTitle", "Inserir Lista com Marcadores");
		mensagens.put("sTxtHelpIBList", "Para criar uma lista de texto com marcadores, clique no ícone 'Inserir Lista com Marcadores'. Se o texto já tiver sido selecionado, a seleção será convertida em uma lista com marcadores. Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpDIndentTitle", "Diminuir Recuo");
		mensagens.put("sTxtHelpDIndent", "Para diminuir o recuo de um parágrafo, clique no ícone 'Diminuir Recuo'. Cada clique consecutivo moverá o texto para a esquerda.");
		mensagens.put("sTxtHelpIIndentTitle", "Aumentar Recuo");
		mensagens.put("sTxtHelpIIndent", "Para aumentar o recuo de um parágrafo, clique no ícone 'Aumentar Recuo'. Cada clique consecutivo moverá o texto para a direita.");
		mensagens.put("sTxtHelpSuperscriptTitle", "Sobrescrito");
		mensagens.put("sTxtHelpSuperscript", "Para aplicar o efeito de sobrescrito ao texto (alinhamento acima): Selecione o texto desejado e clique no ícone 'Sobrescrito'.  Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpSubscriptTitle", "Subscrito");
		mensagens.put("sTxtHelpSubscript", "Para aplicar o efeito de subscrito ao texto (alinhamento abaixo): Selecione o texto desejado e clique no ícone 'Subscrito'.  Cada clique consecutivo ativará e desativará esta função.");
		mensagens.put("sTxtHelpALeftTitle", "Alinhar à Esquerda");
		mensagens.put("sTxtHelpALeft", "Para alinhar à esquerda, selecione o texto e clique no ícone 'Alinhar à Esquerda'.");
		mensagens.put("sTxtHelpACenterTitle", "Centralizar");
		mensagens.put("sTxtHelpACenter", "Para centralizar, selecione o texto e clique no ícone 'Centralizar'.");
		mensagens.put("sTxtHelpARightTitle", "Alinhar à Direita");
		mensagens.put("sTxtHelpARight", "Para alinhar à direita, selecione o texto e clique no ícone 'Alinhar à Direita'.");
		mensagens.put("sTxtHelpJustifyTitle", "Justificar");
		mensagens.put("sTxtHelpJustify", "Para justificar um parágrafo ou texto, selecione o texto e clique no ícone 'Justificar'. ");
		mensagens.put("sTxtHelpIHLineTitle", "Inserir Linha Horizontal");
		mensagens.put("sTxtHelpIHLine", "Para inserir uma linha horizontal, selecione o local desejado para a inserção e clique no ícone 'Inserir Linha Horizontal'.");
		mensagens.put("sTxtHelpCMHyperLinkTitle", "Criar ou Modificar Link");
		mensagens.put("sTxtHelpCMHyperLink", "Para criar um hiperlink, selecione o texto ou imagem que você deseja exibir como hiperlink e clique no ícone 'Criar ou Modificar Link'. ('Informações do Link') conterá as informações existentes sobre o link, caso o objeto selecionado já tenha um link. Na caixa de texto URL, você também pode digitar a URL completa da página que deseja exibir como hiperlink. Você também pode digitar as informações da Janela Destino (opcional) e um nome de Âncora (opcional), caso o link tenha uma âncora.Ao finalizar, clique no botão 'Inserir Link' para inserir o HiperLink que você acabou de criar ou clique em 'Remover Link' para remover um link existente. Clicar em 'Cancelar' fechará a janela e o levará de volta ao editor.Para modificar um hiperlink existente, selecione o link e clique no ícone 'Criar ou Modificar Link'. A janela HiperLink aparecerá. Faça suas alterações e selecione o botão 'Inserir Link'. Selecione 'Remover Link' para remover um link existente.");
		mensagens.put("sTxtHelpIMAnchorTitle", "Inserir/Modificar Âncora");
		mensagens.put("sTxtHelpIMAnchor", "Para inserir uma âncora, selecione um local desejado na página que você está editando e clique no ícone 'Inserir/Modificar Âncora'. Na caixa de diálogo, digite o nome da âncora.Ao finalizar, clique no botão 'Inserir Âncora' para inserir a âncora ou 'Cancelar' para fechar a caixa.Para modificar uma âncora, selecione a Âncora (exibida como uma caixinha amarela quando a opção de Linha-Guia estiver ativada) e clique no ícone 'Inserir/Modificar Âncora'. Faça suas alterações e selecione o botão 'Modificar Âncora' ou clique em 'Cancelar' para fechar a janela.");
		mensagens.put("sTxtHelpCELinkTitle", "Criar Link de E-mail");
		mensagens.put("sTxtHelpCELink", "Para criar um link de e-mail, na página web que está editando, selecione um texto ou uma imagem no local em que deseja que o link apareça. Clique no ícone 'Criar Link de E-mail'. Na caixa de diálogo, digite o endereço de e-mail para o link e o assunto do e-mail.Ao finalizar, clique no botão 'Inserir Link' para inserir o link de e-mail ou 'Cancelar' para fechar a caixa.");
		mensagens.put("sTxtHelpFontTitle", "Fonte");
		mensagens.put("sTxtHelpFont", "Para alterar a fonte do texto, selecione o texto desejado e clique no menu suspenso 'Fonte'.Selecione a fonte desejada (escolha Default, Times New Roman, Arial, Verdana, Tahoma, Courier New ou Georgia).");
		mensagens.put("sTxtHelpFSizeTitle", "Tamanho da Fonte");
		mensagens.put("sTxtHelpFSize", "Para alterar o tamanho do texto, selecione o texto desejado e clique no menu suspenso 'Tamanho'.Selecione o tamanho desejado (tamanho do texto de 1 a 7).");
		mensagens.put("sTxtHelpFormatTitle", "Formato");
		mensagens.put("sTxtHelpFormat", "Para alterar o formato do texto, selecione o texto desejado e clique no menu suspenso 'Formato'.Selecione o formato desejado (escolha Normal, Sobrescrito, Subscrito e H1-H6).");
		mensagens.put("sTxtHelpStyleTitle", "Estilo");
		mensagens.put("sTxtHelpStyle", "Para alterar o estilo de texto, imagens, objetos de formulário, tabelas, células de tabela, etc, selecione o elemento desejado e clique no menu suspenso 'Estilo', que exibirá todos os estilos definidos na lista de estilos.Selecione o estilo desejado.Nota: este recurso não funcionará se não houver nenhuma lista de estilos aplicada à página.");
		mensagens.put("sTxtHelpFColorTitle", "Cor da Fonte");
		mensagens.put("sTxtHelpFColor", "Para alterar a cor do texto, selecione a área de texto desejada e clique no menu suspenso 'Cor'.No menu de seleção, selecione a cor desejada.");
		mensagens.put("sTxtHelpHColorTitle", "Realçar");
		mensagens.put("sTxtHelpHColor", "Para alterar a cor de realce do texto, selecione a área de texto desejada e clique no menu suspenso 'Realçar'.No menu de seleção, selecione a cor desejada.");
		mensagens.put("sTxtHelpTFunctionsTitle", "Funções de Tabela");
		mensagens.put("sTxtHelpTFunctions", "Para inserir ou modificar uma tabela ou célula, selecione o ícone 'Funções de Tabela' para exibir uma lista das Funções de Tabela existentes.Se uma Função de Tabela NÃO estiver disponível, você precisará selecionar ou posicionar seu cursor dentro da tabela que deseja modificar.");
		mensagens.put("sTxtHelpITableTitle", "Inserir Tabela");
		mensagens.put("sTxtHelpITable", "Para inserir uma tabela, selecione o local desejado e clique no ícone 'Inserir Tabela'.Uma nova janela se abrirá, exibindo os seguintes campos: Linhas - número de linhas na tabela; Colunas - número de colunas na tabela; Largura - largura da tabela; Cor de Fundo - cor de fundo da tabela; Enchimento da Célula - enchimento das células; Espaçamento da Célula - espaçamento entre as células; Altura - altura da tabela e Borda - borda ao redor das células.Preencha os detalhes da tabela e clique no botão 'Inserir Tabela' para inserir uma tabela ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpMTPropertiesTitle", "Modificar Propriedades de Tabela");
		mensagens.put("sTxtHelpMTProperties", "Para modificar propriedades da tabela, selecione uma tabela ou clique em qualquer lugar dentro da tabela a modificar e, então, clique no ícone 'Modificar Propriedades de Tabela'.Uma janela se abrirá, exibindo as propriedades da tabela. Clique no botão 'Modificar Propriedades de Tabela' para salvar suas alterações ou clique em 'Cancelar' para retornar ao editor.Nota: esta função não funcionará se nenhuma tabela tiver sido selecionada.");
		mensagens.put("sTxtHelpMCPropertiesTitle", "Modificar Propriedades de Célula");
		mensagens.put("sTxtHelpMCProperties", "Para modificar propriedades da célula, clique dentro da célula a ser modificada e, então, clique no ícone 'Modificar Propriedades de Célula'.Uma janela se abrirá, exibindo as propriedades de célula.Clique no botão 'Modificar Propriedades de Célula' para salvar suas alterações ou clique em 'Cancelar' para retornar ao editor.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada e também não funciona com células múltiplas.");
		mensagens.put("sTxtHelpICttRightTitle", "Inserir Coluna à Direita");
		mensagens.put("sTxtHelpICttRight", "Para inserir uma coluna à direita do seu cursor, clique dentro da célula após a qual se inserirá uma coluna e, então, clique no ícone 'Inserir Coluna à Direita'.Cada clique consecutivo inserirá uma outra coluna após a célula selecionada.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpICttLeftTitle", "Inserir Coluna à Esquerda");
		mensagens.put("sTxtHelpICttLeft", "Para inserir coluna à esquerda do seu cursor, clique dentro da célula antes da qual se inserirá uma coluna e, então, clique no ícone 'Inserir Coluna à Esquerda'.Cada clique consecutivo inserirá uma outra coluna antes da célula selecionada.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpIRAboveTitle", "Inserir Linha Acima");
		mensagens.put("sTxtHelpIRAbove", "Para inserir uma linha acima, clique dentro da célula acima da qual se inserirá uma linha e, então, clique no ícone 'Inserir Linha Acima'.Cada clique consecutivo inserirá uma outra linha acima da célula selecionada.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpIRBelowTitle", "Inserir Linha Abaixo");
		mensagens.put("sTxtHelpIRBelow", "Para inserir uma linha abaixo, clique dentro da célula abaixo da qual se inserirá uma linha e, então, clique no ícone 'Inserir Linha Abaixo'.Cada clique consecutivo inserirá uma outra abaixo da célula selecionada.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpDRowTitle", "Excluir Linha");
		mensagens.put("sTxtHelpDRow", "Para excluir uma linha, clique dentro da célula da linha a ser excluída e clique no ícone 'Excluir Linha'.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpIColumnTitle", "Inserir Coluna");
		mensagens.put("sTxtHelpIColumn", "Para inserir uma coluna, clique dentro da célula da coluna a ser inserida e clique no ícone 'Inserir Coluna'.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpDColumnTitle", "Excluir Coluna");
		mensagens.put("sTxtHelpDColumn", "Para excluir uma coluna, clique dentro da célula da coluna a ser excluída e clique no ícone 'Excluir Coluna'.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpICSpanTitle", "Mesclar Coluna");
		mensagens.put("sTxtHelpICSpan", "Para mesclar colunas, clique dentro da célula onde está a coluna que será mesclada e clique no ícone 'Mesclar Coluna'.Cada clique consecutivo mesclará colunas da célula selecionada.Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpDCSpanTitle", "Dividir Coluna");
		mensagens.put("sTxtHelpDCSpan", "Para dividir colunas, clique dentro da célula na qual as colunas serão divididas e clique no ícone 'Dividir Coluna'.Cada clique consecutivo dividirá colunas da célula selecionada. Nota: esta função não funcionará se nenhuma célula tiver sido selecionada.");
		mensagens.put("sTxtHelpFFunctionsTitle", "Funções de Formulário");
		mensagens.put("sTxtHelpFFunctions", "Para inserir ou modificar um Formulário, selecione o ícone 'Funções de Formulário' para exbir uma lista de funções de formulário disponíveis.Se uma Função de Formulário NÃO estiver disponível, você precisará posicionar seu cursor dentro do Formulário que deseja modificar.");
		mensagens.put("sTxtHelpIFormTitle", "Inserir Formulário");
		mensagens.put("sTxtHelpIForm", "Para inserir um formulário, selecione a posição desejada e clique no ícone 'Inserir Formulário'.Uma nova janela se abrirá, exibindo os seguintes campos: Nome - nome do formulário; Ação - local do script que processa o formulário e Método - post, get ou nenhum.Preencha os detalhes do formulário ou deixe em branco, se desejado.Ao finalizar, clique no botão 'Inserir Formulário' para inserir formulário ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpMFPropertiesTitle", "Modificar Propriedades de Formulário");
		mensagens.put("sTxtHelpMFProperties", "Para modificar propriedades do formulário, clique em qualquer lugar dentro do formulário que será modificado e, então, clique no ícone 'Modificar Propriedades de Formulário'.Uma janela se abrirá, exibindo as propriedades do formulário.Clique no botão 'Modificar Propriedades de Formulário' para salvar suas alterações ou clique em 'Cancelar' para retornar ao editor. Nota: esta função não funcionará se nenhum formulário tiver sido selecionado.");
		mensagens.put("sTxtHelpIMTFieldTitle", "Inserir/Modificar Campo Texto");
		mensagens.put("sTxtHelpIMTField", "Para inserir um campo texto, selecione a posição desejada e clique no ícone 'Inserir/Modificar Campo Texto'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome do campo texto; Largura em Caractere - a largura do campo texto, em caracteres; Tipo - tipo de campo texto (Texto ou Senha); Valor Inicial - texto inicial a ser exibido e Caracteres Máximos - número máximo de caracteres permitido.Defina os atributos e clique no botão 'Inserir Campo Texto' para inserir campo texto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um campo texto, selecione o campo texto desejado e clique no ícone 'Inserir/Modificar Campo Texto'.Uma janela se abrirá, exibindo os atributos do campo.Modifique os atributos desejados e clique no botão 'Modificar Campo Texto' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpIMTAreaTitle", "Inserir/Modificar Área de Texto");
		mensagens.put("sTxtHelpIMTArea", "Para inserir uma área de texto, selecione a posição desejada e clique no ícone 'Inserir/Modificar Área de Texto'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome da área de texto; Largura em Caractere - a largura da área de texto, em caracteres; Valor Inicial - texto inicial a ser exibido e Linhas - número de linhas permitido na área de texto.Defina os atributos e clique no botão 'Inserir Área de Texto' para inserir a área de texto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de uma área de texto, selecione a área de texto desejada e clique no ícone 'Inserir/Modificar Área de Texto'.Uma janela se abrirá, exibindo os atributos da área.Modifique os atributos desejados e clique no botão 'Modificar Área de Texto' para salvar as alterações or clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMHAreaTitle", "Inserir/Modificar Campo Oculto");
		mensagens.put("sTxtHelpIMHArea", "Para inserir um campo oculto, selecione a posição desejada e clique no ícone 'Inserir/Modificar Campo Oculto'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome do campo oculto e Valor Inicial - valor inicial do campo oculto.Defina os atributos e clique no botão 'Inserir Campo Oculto' para inserir o campo oculto ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um campo oculto, selecione o campo oculto desejado e clique no ícone 'Inserir/Modificar Campo Oculto'.Uma janela se abrirá, exibindo os atributos do campo oculto.Modifique os atributos desejados e clique no botão 'Modificar Campo Oculto' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMButtonTitle", "Inserir/Modificar Botão de Ação");
		mensagens.put("sTxtHelpIMButton", "Para inserir um botão de ação, selecione a posição desejada e clique no ícone 'Inserir/Modificar Botão de Ação'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome do botão de ação; Tipo - tipo de botão (Submit, Reset ou Botão) e Valor Inicial - texto inicial do botão.Defina os atributos e clique em 'Inserir Botão de Ação' para inserir o botão ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um botão, selecione o botão desejado e clique no ícone 'Inserir/Modificar Botão de Ação'.Uma janela se abrirá, exibindo os atributos do botão.Modifique os atributos desejados e clique no botão 'Modificar Botão de Ação' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMCheckboxTitle", "Inserir/Modificar Caixa de Seleção");
		mensagens.put("sTxtHelpIMCheckbox", "Para inserir uma caixa de seleção, selecione a posição desejada e clique no ícone 'Inserir/Modificar Caixa de Seleção'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome da caixa de seleção; Estado Inicial - marcado ou desmarcado e Valor Inicial - valor inicial da caixa de seleção.Defina os atributos e clique no botão 'Inserir Caixa de Seleção' para inserir a caixa de seleção ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de uma caixa de seleção, selecione a caixa de seleção desejada e clique no ícone 'Inserir/Modificar Caixa de Seleção'.Uma janela se abrirá, exibindo os atributos da caixa de seleção.Modifique os atributos desejados e clique no botão 'Modificar Caixa de Seleção' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMRButtonTitle", "Inserir/Modificar Botão de Opção");
		mensagens.put("sTxtHelpIMRButton", "Para inserir um botão de opção, selecione a posição desejada e clique no ícone 'Inserir/Modificar Botão de Opção'.Uma janela se abrirá, exibindo os seguintes atributos: Nome - nome do botão de opção; Estado Inicial - marcado ou desmarcado e Valor Inicial - valor inicial do botão de opção.Defina os atributos e clique em 'Inserir Botão de Opção' para inserir o botão de opção ou clique em 'Cancelar' para retornar ao editor.Para modificar as propriedades de um botão de opção, selecione o botão de opção desejado e clique no ícone 'Inserir/Modificar Botão de Opção'.Uma janela se abrirá, exibindo os atributos do botão de opção.Modifique os atributos desejados e clique no botão 'Modificar Botão de Opção' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor. ");
		mensagens.put("sTxtHelpIMImageTitle", "Inserir/Modificar Imagem");
		mensagens.put("sTxtHelpIMImage", "Se uma imagem NÃO estiver selecionada, um clique neste ícone abrirá o Gerenciador de Imagens. <a href=//#image//>Clique aqui para obter mais informações sobre o gerenciador de imagens</a>.Se uma imagem ESTIVER selecionada, um clique neste ícone abrirá a janela 'Modificar Propriedades de Imagem'.Para modificar as propriedades de imagem da imagem selecionada, defina os atributos necessários e clique no botão 'Modificar Propriedades de Imagem'.");
		mensagens.put("sTxtHelpTextboxTitle", "Inserir Caixa de Texto");
		mensagens.put("sTxtHelpTextbox", "Para adicionar uma caixa de texto em qualquer lugar da página, selecione o local em que deseja posicioná-la e clique no ícone 'Inserir Caixa de Texto', que adicionará uma caixa de texto no local especificado.Para reajustar a caixa de texto, clique na moldura da caixa de texto (ative o recurso 'mostrar/ocultar linhas-guia' se você não puder visualizar o contorno da caixa de texto). Clique então ao lado/canto da moldura cujo tamanho deve ser reajustado e arraste até alcançar o tamanho adequado. O texto que você digitar pertencerá à caixa de texto e se estenderá junto com a caixa de texto.");
		mensagens.put("sTxtHelpAbsoluteTitle", "Trocar Posicionamento Absoluto");
		mensagens.put("sTxtHelpAbsolute", "Para posicionar uma caixa de texto ou imagem usando o posicionamento absoluto, selecione a caixa de texto ou a imagem e clique no ícone 'Posicionamento Absoluto'. Você agora pode clicar e arrastar uma imagem ou caixa de texto até o local em que você deseja posicioná-la na janela ativa.");
		mensagens.put("sTxtHelpISCharactersTitle", "Inserir Caracteres Especiais");
		mensagens.put("sTxtHelpISCharacters", "Para inserir um caractere especial, clique no ícone 'Inserir Caractere Especial'.Uma janela se abrirá, exibindo uma lista de caracteres especiais.Clique no ícone de caractere para inseri-lo em sua página web.");
		mensagens.put("sTxtHelpMPPropertiesTitle", "Modificar Propriedades da Página");
		mensagens.put("sTxtHelpMPProperties", "Para modificar propriedades da página, clique no ícone 'Modificar Propriedades da Página'.Uma janela se abrirá, exibindo os atributos de página: Título da Página - título da página; Descrição - descrição da página; Imagem de Fundo - A URL da imagem atualmente definida com a imagem de fundo da página; Palavras-Chave - palavras-chave que devem ser reconhecidas pela página; Cor de Fundo - a cor de fundo da página; Cor de Texto - cor do texto na página e Cor do Link - a cor dos links na página.Ao finalizar a modificação, clique no botão 'Modificar Página' para salvar as alterações ou clique em 'Cancelar' para retornar ao editor.");
		mensagens.put("sTxtHelpCUHTMLCodeTitle", " Limpar Código HTML");
		mensagens.put("sTxtHelpCUHTMLCode", "Para limpar o código HTML, clique no ícone 'Limpar Código HTML'.Esta operação limpa um código de HTML incompleto e irregular.É um recurso útil quando se copia e cola de documentos do Microsoft Word e se deseja remover código HTML desnecessário. ");
		mensagens.put("sTxtHelpCustomHTMLTitle", "Inserir Código HTML Customizado");
		mensagens.put("sTxtHelpCustomHTML", "É possível haver uma lista de itens disponíveis a inserir e que você deseje pré-visualizar e selecionar. Em geral, esta lista conterá itens customizados em HTML, tais como logos e texto formatado específicos ao seu site. Para pré-visualizar um item, clique no item da lista e ele aparecerá no campo de pré-visualização abaixo. Para selecionar o item, clique nele e escolha 'Inserir HTML'.");
		mensagens.put("sTxtHelpSHGuidelinesTitle", "Mostrar/Ocultar Linhas-Guia");
		mensagens.put("sTxtHelpSHGuidelines", "Para mostrar ou ocultar linhas-guia, clique no ícone 'Mostrar/Ocultar Linhas-Guia'.Este recurso ativará ou não a exibição de linhas-guia de tabelas e de formulário.Quando o recurso de exibir linhas-guia estiver ativado, as tabelas e células estarão envolvidas por uma linha cinza pontilhada, os formulários estarão envolvidos por uma linha vermelha pontilhada e os campos ocultos assumirão a forma de um quadrado rosa.Note que a barra de status (na parte inferior da janela) refletirá o modo que está atualmente em uso.");
		mensagens.put("sTxtHelpSModeTitle", "Modo Código Fonte HTML");
		mensagens.put("sTxtHelpSMode", " Para mudar para o modo de edição de código-fonte, clique no botão 'Fonte' na parte inferior do editor.Este passo mudará para o modo de edição de código HTML.Para voltar para o editor de HTML, clique no botão 'Editar', localizado na parte inferior do editor.Este modo é recomendado apenas para usuários avançados.");
		mensagens.put("sTxtHelpPModeTitle", "Modo de Pré-Visualização");
		mensagens.put("sTxtHelpPMode", "Para apresentar uma pré-visualização da página que está sendo editada, clique no botão 'Pré-Visualizar' na parte inferior do editor.É um recurso muito útil, pois exibe a página com a mesma aparência que terá em seu navegador antes que as alterações sejam salvas.Para voltar ao modo de edição, clique no botão 'Editar' na parte inferior do editor.");
		mensagens.put("sTxtHelpImageManager", "Gerenciador de Imagens");
		mensagens.put("sTxtHelpBTTOP", "Voltar ao Topo");
		mensagens.put("sTxtHelpImageDescription", "O Gerenciador de Imagens é chamado sempre que você pré-visualizar, inserir, excluir e carregar seus arquivos de imagem.Você pode realizar uma manutenção geral em suas imagens a partir do Gerenciador de Imagens - inserir, definir como fundo, carregar, visualizar e excluir imagens.");
		mensagens.put("sTxtHelpVaImageTitle", "Visualizando uma Imagem");
		mensagens.put("sTxtHelpVaImage", "Para visualizar uma imagem, selecione a imagem desejada e clique no link 'Visualizar'.A imagem será mostrada em uma janela em seu tamanho natural.Feche a janela para retornar ao Gerenciador de Imagens.");
		mensagens.put("sTxtHelpIaImageTitle", "Inserindo uma Imagem");
		mensagens.put("sTxtHelpIaImage", "`Para inserir uma imagem, clique no link 'Inserir' no navegador de imagem próximo à imagem a ser inserida.");
		mensagens.put("sTxtHelpSBImageTitle", "Definir Imagem de Fundo");
		mensagens.put("sTxtHelpSBImage", "Para definir uma imagem como uma imagem de fundo, clique no link 'Fundo' no navegador de imagem próximo à imagem a ser definida.A imagem será definida como a imagem de fundo da página atual.");
		mensagens.put("sTxtHelpDaImageTitle", "Excluir uma Imagem");
		mensagens.put("sTxtHelpDaImage", "Para excluir uma imagem, selecione a imagem desejada e clique no link 'Excluir'.Uma mensagem pedirá a confirmação da exclusão.Se tiver certeza de que deseja excluir a imagem selecionada, clique em 'OK'.Clicar em 'Cancelar' retornará ao Gerenciador de Imagens.");
		mensagens.put("sTxtHelpUaImageTitle", "Enviar uma Imagem para o Servidor");
		mensagens.put("sTxtHelpUaImage", "Para enviar uma imagem para o servidor (upload), clique no botão 'Procurar' para abrir uma caixa 'Escolher Arquivo', que permite que você selecione uma imagem local a ser enviada.Assim que o arquivo tiver sido selecionado, clique em 'OK' para iniciar o envio do arquivo ou clique em 'Cancelar' para retornar ao Gerenciador de Imagens.Se a imagem tiver sido enviada com sucesso, ela aparecerá no Gerenciador de Imagens.");
		mensagens.put("sTxtSave", "Salvar");
		mensagens.put("sTxtNone", "Nenhum");
		mensagens.put("sTxtMoreColors", "Mais Cores");
		mensagens.put("sTxtCheckSpelling", "Checar Ortografia");
		mensagens.put("sTxtUpload", "Upload");
		mensagens.put("sTxtSwitch", "Trocar");
		mensagens.put("sTxtImageDirNotConfigured", "Diretório de imagens não configurado corretamente");
		mensagens.put("sTxtFlashDirNotConfigured", "Diretório de arquivos 'flash' não configurado corretamente");
		mensagens.put("sTxtImageProperties", "Propriedades da Imagem");
		mensagens.put("sTxtFlashProperties", "Propriedades do Flash");
		mensagens.put("sTxtHelpIMFlashTitle", "Insere/Modifica Flash");
		mensagens.put("sTxtHelpIMFlash", "Se nenhum arquivo Flash estiver selecionado, um clique neste ícone abrirá o Gerenciador de Flash. Se algum arquivo flash estiver selecionado, um clique neste ícone abrirá a janela popup 'Modificar Propriedades do Flash'. Para modificar as propriedades do arquivo flash selecionado, defina os atributos necessários e clique no botão 'Modificar'.");
		mensagens.put("sTxtDefaultImageLibrary", "Biblioteca de Imagens Padrão");
		mensagens.put("sTxtDefaultFlashLibrary", "Biblioteca de Flash Padrão");
		mensagens.put("sTxtLoad", "Carregar");
		mensagens.put("sTxtInsertSelect", "Insere Campo Seleção");
		mensagens.put("sTxtInsertSelectInst", "Digite as informações necessárias e clique no botão OK para inserir o campo de seleção em sua página web.");
		mensagens.put("sTxtText", "Texto");
		mensagens.put("sTxtValue", "Valor");
		mensagens.put("sTxtSelected", "Selecionado");
		mensagens.put("sTxtSingle", "Selecão Única");
		mensagens.put("sTxtMultiple", "Seleção Multipla");
		mensagens.put("sTxtAdd", "Adiciona");
		mensagens.put("sTxtUpdate", "Atualiza");
		mensagens.put("sTxtDelete", "Exclui");
		mensagens.put("sTxtMaintainOptions", "Opções de Manutenção");
		mensagens.put("sTxtCurrentOptions", "Opções Atuais");
		mensagens.put("sTxtModifySelect", "Modifica Seleção");
		mensagens.put("sTxtModifySelectInst", "Digite as informações necessárias e clique no botão OK para modificar o campo de seleção em sua página web.");
		mensagens.put("sTxtSelect", "Insere / Modifica Campo Seleção");
		mensagens.put("sTxtHelpIMSListTitle", "Insere / Modifica Campo Seleção");
		mensagens.put("sTxtHelpIMSList", "Para inserir um campo de seleção, selecione a posição desejada e clique no ícone 'Insere / Modifica Campo Seleção'.Uma janela se abrirá com os seguintes atributos: Nome - nome do campo de seleção;Opções Atuais - As opções disponíveis para seleção na lista;Tipo - como a lista de opções será mostrada ( opção única ou múltipla );Tamanho - quantos ítens serão mostrados;Estilo - Estilo a ser aplicado no campo de seleção, se houver algum.Para adicionar opções no campo de seleção, use o texto, valor e caixa [selecionado] abaixo do cabeçalho [Opções de Manutenção].Para modificar as propriedades da lista, selecione a lista desejada e clique no botão 'Insere / Modifica Campo Seleção'.Uma janela se abrirá com os atributos do campo de seleção.Modifique os atributos desejados e clique no botão 'OK' para salvar as alterações ou clique no botão 'Cancela' para voltar ao editor.");

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