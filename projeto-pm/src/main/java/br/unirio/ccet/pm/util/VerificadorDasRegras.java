package br.unirio.ccet.pm.util;

import java.util.HashMap;

import br.unirio.ccet.pm.model.Aluno;
import br.unirio.ccet.pm.model.Disciplina;

public class VerificadorDasRegras {

	public String verificarRegraDeAlunoJubilado(HashMap<String, Disciplina> informacaoesDeDisciplinas, Aluno aluno) {
		
		Disciplina disciplina;
		String jubilado = "";
		String craAlunoFormatado = aluno.getCra().replace(",", ".");
		
		if (Double.valueOf(craAlunoFormatado) < 4) {
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				disciplina = informacaoesDeDisciplinas.get(codigoChave);
				if (disciplina.getTotalDeReprovacoes() >= 4) {
					jubilado = "O aluno possui CRA menor que quatro e reprovou quatro ou mais vezes em uma disciplina.";
					break;
				}
			} 
		} else {
			jubilado = "O aluno não tem que ser jubilado.";
		}
		return jubilado;
	}

	public String verificarRegrasDeIntegralizacao(Aluno aluno) {

		String integralizacao = "";
		
		if (Integer.valueOf(aluno.getAnoEntrada()) <= 20132 && 
				Integer.valueOf(aluno.getPeriodoAtual()) > 12) {
			integralizacao = "O aluno deve pedir prorrogação de integralização para o próximo período.";
		} else if (Integer.valueOf(aluno.getAnoEntrada()) >= 20141) {
			if (Integer.valueOf(aluno.getPeriodoAtual()) >= 7) {
				if (Integer.valueOf(aluno.getPeriodoAtual()) >= 12) {
					integralizacao = "O aluno não pode mais integralizar o curso.";
				} else {
					integralizacao = "O aluno deve pedir prorrogação de integralização para o próximo período.";
				}
			}
		} else {
			integralizacao = "A situação de integralização do aluno está normal.";
		}
		return integralizacao;
	}

	
	
}
