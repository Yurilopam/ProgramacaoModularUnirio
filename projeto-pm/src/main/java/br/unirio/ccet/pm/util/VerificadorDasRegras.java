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
			jubilado = "O aluno n�o tem que ser jubilado.";
		}
		return jubilado;
	}

	public String verificarRegrasDeIntegralizacao(Aluno aluno) {

		String integralizacao = "";
		
		if (Integer.valueOf(aluno.getAnoEntrada()) <= 20132 && 
				Integer.valueOf(aluno.getPeriodoAtual()) > 12) {
			integralizacao = "O aluno deve pedir prorroga��o de integraliza��o para o pr�ximo per�odo.";
		} else if (Integer.valueOf(aluno.getAnoEntrada()) >= 20141) {
			if (Integer.valueOf(aluno.getPeriodoAtual()) >= 7) {
				if (Integer.valueOf(aluno.getPeriodoAtual()) >= 12) {
					integralizacao = "O aluno n�o pode mais integralizar o curso.";
				} else {
					integralizacao = "O aluno deve pedir prorroga��o de integraliza��o para o pr�ximo per�odo.";
				}
			}
		} else {
			integralizacao = "A situa��o de integraliza��o do aluno est� normal.";
		}
		return integralizacao;
	}
	
	public String verificarRegrasDeNotas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int notasAbaixoDeCinco = 0;
		String situacaoNota = "";
		
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (!disciplina.getMedia().equals("sem nota") 
					&& !disciplina.getMedia().equals("disciplina n�o cursada")
					&& !disciplina.getMedia().equals("S")) {
				String notaDisciplinaFormatada = disciplina.getMedia().replace(",", ".");
				if (Double.valueOf(notaDisciplinaFormatada) < 5.0) {
					notasAbaixoDeCinco++;
				}
			}
		}
		if (notasAbaixoDeCinco > 0) {
			situacaoNota = "O aluno ainda n�o pode integralizar pois possui notas abaixo de 5,0.";
		} else {
			situacaoNota = "O aluno est� com todas as notas regulamentadas para integralizar.";
		}
		return situacaoNota;
	}

	public String verificarCrAluno(Aluno aluno) {
		String craAlunoFormatado = aluno.getCra().replace(",", ".");
		if (Double.valueOf(craAlunoFormatado) > 7) {
			return "O CR do aluno � maior que sete.";
		} else {
			return "O CR do aluno � menor que sete.";
		}
	}

	public String verificarSeCursaAoMenosTresDisciplinas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int contaDisciplinasMatriculadas = 0;
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (disciplina.getSituacao().equals("Matr�cula")) {
				contaDisciplinasMatriculadas++;
			}
		}
		if (contaDisciplinasMatriculadas >= 3) {
			return "O aluno est� cursando ao menos tr�s disciplinas.";
		}
		return "O aluno n�o est� cursando ao menos tr�s disciplinas.";
	}

	public String verificarSeAlunoIntegralizaNormalmente(HashMap<String, Disciplina> informacaoesDeDisciplinas, Aluno aluno) {
		Disciplina disciplina;
		int contaDisciplinasAprovadas = 0;
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (disciplina.getSituacao().equals("Aprovado")) {
				contaDisciplinasAprovadas++;
			}
		}
		
		String condicoesFormacao = "O aluno tem condi��es de se formar dentro do prazo regular.";
		if (contaDisciplinasAprovadas >= 6 && Integer.valueOf(aluno.getPeriodoAtual()) < 3) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 12 && Integer.valueOf(aluno.getPeriodoAtual()) < 4) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 19 && Integer.valueOf(aluno.getPeriodoAtual()) < 5) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 26 && Integer.valueOf(aluno.getPeriodoAtual()) < 6) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 33 && Integer.valueOf(aluno.getPeriodoAtual()) < 7) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 39 && Integer.valueOf(aluno.getPeriodoAtual()) < 8) {
			return condicoesFormacao;
		} else if (contaDisciplinasAprovadas >= 45 && Integer.valueOf(aluno.getPeriodoAtual()) < 9) {
			return condicoesFormacao;
		}
		return "O aluno n�o tem condi��es de se formar dentro do prazo regular.";
	}

}
