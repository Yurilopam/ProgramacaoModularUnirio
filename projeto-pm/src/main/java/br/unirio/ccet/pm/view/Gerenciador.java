package br.unirio.ccet.pm.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.unirio.ccet.pm.controller.AlunoController;
import br.unirio.ccet.pm.controller.DisciplinaController;
import br.unirio.ccet.pm.util.ManipuladorDeHistorico;
import br.unirio.ccet.pm.util.VerificadorDasRegras;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            VerificadorDasRegras regras = new VerificadorDasRegras();

            String historicoEscolarExtraido = pdf.extrairHistoricoEscolar(historicoEscolarDocumento);
            String historicoEscolarRefinado = pdf.refinadorDeConteudoDoHistoricoEscolar(historicoEscolarExtraido,
                    pdf.recuperarIndexInicial(historicoEscolarDocumento), pdf.recuperarIndexFinal(historicoEscolarDocumento));

            disciplinaController.importarListaTodasDisciplinas(LISTA_DISCIPLINA_PATH.toString(), historicoEscolarRefinado);
            disciplinaController.encontrarAtributosDisciplinas(historicoEscolarRefinado);

            alunoController.encontrarCRAdoAluno(historicoEscolarRefinado);
            alunoController.encontrarDadosDePeriodoDoAluno(historicoEscolarExtraido);

            boolean jubilarAluno = regras.verificarRegraDeAlunoJubilado(disciplinaController.getInformacaoesDeTodasDisciplinas(),
                    alunoController.getAluno());

            int integralizarAluno = regras.verificarRegrasDeIntegralizacao(alunoController.getAluno());

            boolean situacaoNotas = regras.verificarRegrasDeNotas(disciplinaController.getInformacaoesDeTodasDisciplinas());

            boolean materiasPorPeriodo = regras.verificarSeCursaAoMenosTresDisciplinas(disciplinaController.getInformacaoesDeTodasDisciplinas());

            boolean condicoesDeSeFormar = regras.verificarSeAlunoIntegralizaNormalmente(disciplinaController.getInformacaoesDeTodasDisciplinas(),
                    alunoController.getAluno());

            boolean crAluno = regras.verificarCRAAluno(alunoController.getAluno());

            System.out.println(jubilarAluno);
            System.out.println(integralizarAluno);
            System.out.println(situacaoNotas);
            System.out.println(materiasPorPeriodo);
            System.out.println(condicoesDeSeFormar);
            System.out.println(crAluno);

            System.out.println("");

            for (String codigo : disciplinaController.getInformacaoesDeTodasDisciplinas().keySet()) {
                System.out.println(disciplinaController.getInformacaoesDeTodasDisciplinas().get(codigo).getCodigo() + " "
                        + disciplinaController.getInformacaoesDeTodasDisciplinas().get(codigo).getNome() + " "
                        + disciplinaController.getInformacaoesDeTodasDisciplinas().get(codigo).getMedia() + " "
                        + disciplinaController.getInformacaoesDeTodasDisciplinas().get(codigo).getSituacao() + " "
                        + disciplinaController.getInformacaoesDeTodasDisciplinas().get(codigo).getTotalDeReprovacoes());
            }
        } catch (IOException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
