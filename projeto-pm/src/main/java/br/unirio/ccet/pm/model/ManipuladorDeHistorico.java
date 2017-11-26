package br.unirio.ccet.pm.model;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import br.unirio.ccet.pm.controller.DisciplinaController;

/**
 * Classe destinada a ler, refinar e interpretar o hist�rico escolar do aluno
 * 
 * @author grupoPM
 * 
 */
public class ManipuladorDeHistorico {
	private static final String MARCOINDICEINICIAL = "Situa��o Local\r\n";
	private static final String MARCOINDICEIFINAL = "Geral";
	
	
	/**
	 * M�todo utilizado para recuperar o index inicial do doc
	 * 
	 * @param historicoEscolarDoc
	 * @return indexComeco
	 * @throws IOException
	 */
	public static int recuperarIndexInicial(File historicoEscolarDoc) throws IOException {
		int indexComeco;
		ArrayList<Integer> indexesComecoEFimDasMaterias = recuperarIndexesComecoEFim(historicoEscolarDoc);
		indexComeco = indexesComecoEFimDasMaterias.get(0);
		return indexComeco;
	}

	/**
	 * M�todo utilizado para recuperar o index final do doc
	 * 
	 * @param historicoEscolarDoc
	 * @return indexFim
	 * @throws IOException
	 */
	public static int recuperarIndexFinal(File historicoEscolarDoc) throws IOException {
		int indexFim;
		ArrayList<Integer> indexesComecoEFimDasMaterias = recuperarIndexesComecoEFim(historicoEscolarDoc);
		indexFim = indexesComecoEFimDasMaterias.get(1);
		return indexFim;
	}

	/**
	 * M�todo utilizado para recuperar os indexes de come�o e de fim do doc
	 * 
	 * @param historicoEscolarDoc
	 * @return indexesComecoEFimDasMaterias
	 * @throws IOException
	 */
	protected static ArrayList<Integer> recuperarIndexesComecoEFim(File historicoEscolarDoc) throws IOException {
		ArrayList<Integer> indexesComecoEFimDasMaterias = new ArrayList<Integer>();
		String historicoEscolarExtraido = extrairHistoricoEscolar(historicoEscolarDoc);
		indexesComecoEFimDasMaterias = buscadoresDeIndex(historicoEscolarExtraido);
		return indexesComecoEFimDasMaterias;
	}

	/**
	 * M�todo utilizado para extrarir o historico escolar do doc
	 * 
	 * @param historicoEscolarDoc
	 * @return historicoEscolarExtraido
	 * @throws IOException
	 */
	public static String extrairHistoricoEscolar(File historicoEscolarDoc) throws IOException {
		String historicoEscolarExtraido;
		historicoEscolarExtraido = extrairPDF(historicoEscolarDoc);
		return historicoEscolarExtraido;
	}

	/**
	 * 
	 * Este m�todo procura extrair o hist�rico escolar do documento PDF e
	 * transform�-lo em uma String
	 * 
	 * @param historicoEscolarCaminho
	 *            (File) : � o caminho de onde o hist�rico est� armazenado na
	 *            m�quina.
	 * 
	 * @return String com o hist�rico extraido
	 * 
	 */
	public static String extrairPDF(File historicoEscolarCaminho) throws IOException {
		PDDocument historicoEscolar = PDDocument.load(historicoEscolarCaminho);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String historicoEscolarExtraido = pdfStripper.getText(historicoEscolar);
		historicoEscolar.close();
		return historicoEscolarExtraido;
	}

	/**
	 * Este m�todo procura encontrar o in�cio e o fim aproximado do conte�do de
	 * mat�rias do hist�rico escolar.
	 * 
	 * @param historicoEscolarExtraido
	 *            (String): String com o hist�rico escolar completo, antes do
	 *            refinamento
	 * 
	 * @return : ArrayList<Integer> com os valores correspondentes �s posi��es
	 *         inicial e final do bloco de aprova��es
	 * 
	 */
	public static ArrayList<Integer> buscadoresDeIndex(String historicoEscolarExtraido) throws IOException {
		ArrayList<Integer> indexesComecoEFimDasMaterias = new ArrayList<Integer>();
		int indexComeco = historicoEscolarExtraido.indexOf(MARCOINDICEINICIAL) + 16;
		int indexFim = historicoEscolarExtraido.indexOf(MARCOINDICEIFINAL) + 13;

		indexesComecoEFimDasMaterias.add(indexComeco);
		indexesComecoEFimDasMaterias.add(indexFim);

		return indexesComecoEFimDasMaterias;
	}

	/**
	 * Este m�todo se destina a separar o bloco de aprova��es do restante das
	 * informa��es do hist�rico
	 * 
	 * @param historicoEscolarExtraido
	 *            (String) : String com o hist�rico escolar completo, antes do
	 *            refinamento
	 * @param indexComeco
	 *            (Int) : Indicador do come�o do texto refinado
	 * @param indexFim
	 *            (Int) : Indicador do fim do texto refinado
	 * 
	 * @return Substring com o bloco de mat�rias separado do restante
	 * 
	 */
	public static String refinadorDeConteudoDoHistoricoEscolar(String historicoEscolarExtraido, int indexComeco,
			int indexFim) {
		String historicoEscolarRefinado = historicoEscolarExtraido.substring(indexComeco, indexFim);
		return historicoEscolarRefinado;
	}

}
