package br.unirio.ccet.pm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import br.unirio.ccet.pm.model.Aluno;
import br.unirio.ccet.pm.model.Disciplina;
import br.unirio.ccet.pm.util.VerificadorDasRegras;

public class VerificadorDasRegrasTest {

	VerificadorDasRegras vda = new VerificadorDasRegras();
	
	@Test
	public void verificarSeAlunoEhJubiladoTest() {
		
		HashMap<String, Disciplina> informacaoesDeDisciplinas = new HashMap<>();
		Aluno aluno = new Aluno();
		
		assertTrue(vda.verificarRegraDeAlunoJubilado(informacaoesDeDisciplinas, aluno));
		
	}
	
	@Test
	public void verificarSeAlunoNaoEhJubiladoTest() {
		
		HashMap<String, Disciplina> informacaoesDeDisciplinas = new HashMap<>();
		Aluno aluno = new Aluno();
		
		assertFalse(vda.verificarRegraDeAlunoJubilado(informacaoesDeDisciplinas, aluno));
		
	}
}
