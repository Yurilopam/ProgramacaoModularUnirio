package br.unirio.ccet.pm.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.DocumentException;

import br.unirio.ccet.pm.controller.AlunoController;
import br.unirio.ccet.pm.controller.DisciplinaController;
import br.unirio.ccet.pm.service.ManipuladorDeSvg;
import br.unirio.ccet.pm.util.ManipuladorDeHistorico;
import br.unirio.ccet.pm.util.VerificadorDasRegras;

/**
 *
 * @author grupoPM
 *
 */
public class Gerenciador {

    private static final String LISTA_DE_DISCIPLINAS_TXT = "lista de disciplinas.txt";
    private static final String HISTORICO_ESCOLAR_CR_APROVADOS_PDF = "historicoEscolarCRAprovados.pdf";
    private static final Path LISTA_DISCIPLINA_PATH = Paths.get(System.getProperty("user.dir"), LISTA_DE_DISCIPLINAS_TXT);
    private static final Path HISTORIO_ESCOLAR_PATH = Paths.get(System.getProperty("user.dir"), HISTORICO_ESCOLAR_CR_APROVADOS_PDF);

    public static void main(String[] args) {
        try {
            final File historicoEscolarDocumento = new File(HISTORIO_ESCOLAR_PATH.toString());
            DisciplinaController disciplinaController = new DisciplinaController();
            AlunoController alunoController = new AlunoController();
            ManipuladorDeHistorico pdf = new ManipuladorDeHistorico();
            ManipuladorDeSvg svg = new ManipuladorDeSvg();
            VerificadorDasRegras regras = new VerificadorDasRegras();

            String historicoEscolarExtraido = pdf.extrairHistoricoEscolar(historicoEscolarDocumento);
            String historicoEscolarRefinado = pdf.refinadorDeConteudoDoHistoricoEscolar(historicoEscolarExtraido,
                    pdf.recuperarIndexInicial(historicoEscolarDocumento), pdf.recuperarIndexFinal(historicoEscolarDocumento));

            disciplinaController.importarListaTodasDisciplinas(LISTA_DISCIPLINA_PATH.toString(), historicoEscolarRefinado);
            disciplinaController.encontrarAtributosDisciplinas(historicoEscolarRefinado);

            alunoController.encontrarCRAdoAluno(historicoEscolarRefinado);
            alunoController.encontrarDadosDePeriodoDoAluno(historicoEscolarExtraido);

            svg.manipularSvg(disciplinaController.getInformacaoesDeTodasDisciplinas());
            
            boolean jubilarAluno = regras.verificarRegraDeAlunoJubilado(disciplinaController.getInformacaoesDeTodasDisciplinas(),
                    alunoController.getAluno());

            int integralizarAluno = regras.verificarRegrasDeIntegralizacao(alunoController.getAluno());

            boolean situacaoNotas = regras.verificarRegrasDeNotas(disciplinaController.getInformacaoesDeTodasDisciplinas());

            boolean materiasPorPeriodo = regras.verificarSeCursaAoMenosTresDisciplinas(disciplinaController.getInformacaoesDeTodasDisciplinas());

            boolean condicoesDeSeFormar = regras.verificarSeAlunoIntegralizaNormalmente(disciplinaController.getInformacaoesDeTodasDisciplinas(),
                    alunoController.getAluno());

            boolean crAluno = regras.verificarCRAAluno(alunoController.getAluno());
            
            
            regras.imprimirRegras(jubilarAluno, integralizarAluno, situacaoNotas, 
            		materiasPorPeriodo, condicoesDeSeFormar, crAluno);

        } catch (IOException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException e) {
        	Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, e);
		}
    }

	
}
