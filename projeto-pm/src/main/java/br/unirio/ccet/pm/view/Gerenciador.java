package br.unirio.ccet.pm.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.unirio.ccet.pm.controller.DisciplinaController;
import br.unirio.ccet.pm.util.ManipuladorDeHistorico;

public class Gerenciador {

	private static final String LISTA_DE_DISCIPLINAS_TXT = "lista de disciplinas.txt";
	private static final String HISTORICO_ESCOLAR_CR_APROVADOS_PDF = "historicoEscolarCRAprovados.pdf";
	private static final Path LISTA_DISCIPLINA_PATH = Paths.get(System.getProperty("user.dir"), LISTA_DE_DISCIPLINAS_TXT);
	private static final Path HISTORIO_ESCOLAR_PATH = Paths.get(System.getProperty("user.dir"), HISTORICO_ESCOLAR_CR_APROVADOS_PDF);
	
	
	public static void main(String[] args) throws IOException {
		final File historicoEscolarDocumento = new File(HISTORIO_ESCOLAR_PATH.toString());
		DisciplinaController disciplinaController = new DisciplinaController();
		ManipuladorDeHistorico pdf = new ManipuladorDeHistorico();
		
		String historicoEscolarExtraido = pdf.extrairHistoricoEscolar(historicoEscolarDocumento);
		String historicoEscolarRefinado = pdf.refinadorDeConteudoDoHistoricoEscolar(historicoEscolarExtraido,
				pdf.recuperarIndexInicial(historicoEscolarDocumento), pdf.recuperarIndexFinal(historicoEscolarDocumento));
		disciplinaController.importarListaDisciplinas(LISTA_DISCIPLINA_PATH.toString());
		disciplinaController.encontrarAtributosDisciplinas(historicoEscolarRefinado);
		
		
		for (String codigo : disciplinaController.getInformacaoesDeDisciplinas().keySet()) {
			System.out.println(disciplinaController.getInformacaoesDeDisciplinas().get(codigo).getCodigo() + " " + 
					disciplinaController.getInformacaoesDeDisciplinas().get(codigo).getNome() + " " + 
					disciplinaController.getInformacaoesDeDisciplinas().get(codigo).getMedia() + " " + 
					disciplinaController.getInformacaoesDeDisciplinas().get(codigo).getSituacao() + " " + 
					disciplinaController.getInformacaoesDeDisciplinas().get(codigo).getTotalDeReprovacoes());
		}
	}
}
