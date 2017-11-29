package br.unirio.ccet.pm;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import br.unirio.ccet.pm.controller.AlunoController;
import br.unirio.ccet.pm.controller.DisciplinaController;
import br.unirio.ccet.pm.util.ManipuladorDeHistorico;
import br.unirio.ccet.pm.util.VerificadorDasRegras;

public class VerificadorDasRegrasTest {

    private static final String LISTA_DE_DISCIPLINAS_TXT = "lista de disciplinas.txt";
    private static final String HISTORICO_ESCOLAR_CR_APROVADOS_PDF = "historicoEscolarCRAprovados.pdf";
    private static final Path LISTA_DISCIPLINA_PATH = Paths.get(System.getProperty("user.dir"), LISTA_DE_DISCIPLINAS_TXT);
    private static final Path HISTORIO_ESCOLAR_PATH = Paths.get(System.getProperty("user.dir"), HISTORICO_ESCOLAR_CR_APROVADOS_PDF);
    VerificadorDasRegras vda = new VerificadorDasRegras();
    final File historicoEscolarDocumento = new File(HISTORIO_ESCOLAR_PATH.toString());
    DisciplinaController disciplinaController = new DisciplinaController();
    AlunoController alunoController = new AlunoController();
    ManipuladorDeHistorico pdf = new ManipuladorDeHistorico();

    @Before
    public void init() {
        try {
            String historicoEscolarExtraido = pdf.extrairHistoricoEscolar(historicoEscolarDocumento);
            String historicoEscolarRefinado = pdf.refinadorDeConteudoDoHistoricoEscolar(historicoEscolarExtraido,
                    pdf.recuperarIndexInicial(historicoEscolarDocumento), pdf.recuperarIndexFinal(historicoEscolarDocumento));

            disciplinaController.importarListaTodasDisciplinas(LISTA_DISCIPLINA_PATH.toString(), historicoEscolarRefinado);
            disciplinaController.encontrarAtributosDisciplinas(historicoEscolarRefinado);

            alunoController.encontrarCRAdoAluno(historicoEscolarRefinado);
            alunoController.encontrarDadosDePeriodoDoAluno(historicoEscolarExtraido);
        } catch (IOException ex) {
            Logger.getLogger(VerificadorDasRegrasTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    @Test
    public void verificarSeAlunoNaoEhJubiladoTest() {

        assertFalse(vda.verificarRegraDeAlunoJubilado(disciplinaController.getInformacaoesDeTodasDisciplinas(), alunoController.getAluno()));

    }

    @Test
    public void verificarAlunoPrecisaFazerIntegralizacao() {

        assertEquals(vda.verificarRegrasDeIntegralizacao(alunoController.getAluno()),3);

    }
    
    @Test
    public void verificaSituacaoNotas(){
        assertTrue(vda.verificarRegrasDeNotas(disciplinaController.getInformacaoesDeTodasDisciplinas()));
        
    }
    
    @Test
    public void verificarSeCRAAluno(){
        assertFalse(vda.verificarCRAAluno(alunoController.getAluno()));
        
    }
    
	@Test
    public void verificarSeAoMenos3Disciplinas(){
        assertTrue(vda.verificarSeCursaAoMenosTresDisciplinas(disciplinaController.getInformacaoesDeTodasDisciplinas()));
        
    }
    
}
