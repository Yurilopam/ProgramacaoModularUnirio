package br.unirio.ccet.pm.util;

import java.util.HashMap;

import br.unirio.ccet.pm.model.Aluno;
import br.unirio.ccet.pm.model.Disciplina;

public class VerificadorDasRegras {
	/**
	 * Este método verifica se o aluno deve ser jubilado ou não
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informações completas de todas as disciplinas do curso
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que está sendo verificado
     * @return false se não for jubilado e true se for
	 * 
	 */
	public boolean verificarRegraDeAlunoJubilado(HashMap<String, Disciplina> informacaoesDeDisciplinas, Aluno aluno) {
		
		Disciplina disciplina;
		boolean jubilado = false;
		String craAlunoFormatado = aluno.getCra().replace(",", ".");
		
		if (Double.valueOf(craAlunoFormatado) < 4) {
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				disciplina = informacaoesDeDisciplinas.get(codigoChave);
				if (disciplina.getTotalDeReprovacoes() >= 4) {
					jubilado = true;
					break;
				}
			} 
		} else {
			jubilado = false;
		}
		return jubilado;
	}
	/**
	 * Este método verifica se o aluno pode pedir integralização
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que está sendo verificado
	 * 
	 */
	public int verificarRegrasDeIntegralizacao(Aluno aluno) {

		String integralizacao = "";
                int codigoRetornoIntegralizacao=10;
		
		if (Integer.valueOf(aluno.getAnoEntrada()) <= 20132 && 
				Integer.valueOf(aluno.getPeriodoAtual()) > 12) {
			integralizacao = "O aluno deve pedir prorrogação de integralização para o próximo período.";
                        codigoRetornoIntegralizacao= 0;
		} else if (Integer.valueOf(aluno.getAnoEntrada()) >= 20141) {
			if (Integer.valueOf(aluno.getPeriodoAtual()) >= 7) {
				if (Integer.valueOf(aluno.getPeriodoAtual()) >= 12) {
					integralizacao = "O aluno não pode mais integralizar o curso.";
                                        codigoRetornoIntegralizacao = 1;
				} else {
					integralizacao = "O aluno deve pedir prorrogação de integralização para o próximo período.";
                                        codigoRetornoIntegralizacao = 2;
				}
			}
		} else {
			integralizacao = "A situação de integralização do aluno está normal.";
                        codigoRetornoIntegralizacao = 3;
		}
		return codigoRetornoIntegralizacao;
	}
	/**
	 * Este método verifica se o aluno tem nota para fazer a integralização
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informações completas de todas as disciplinas do curso
	 * 
	 */
	public boolean verificarRegrasDeNotas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int notasAbaixoDeCinco = 0;
		boolean situacaoNota = true;
		
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (!disciplina.getMedia().equals("sem nota") 
					&& !disciplina.getMedia().equals("disciplina não cursada")
					&& !disciplina.getMedia().equals("S")) {
				String notaDisciplinaFormatada = disciplina.getMedia().replace(",", ".");
				if (Double.valueOf(notaDisciplinaFormatada) < 5.0) {
					notasAbaixoDeCinco++;
				}
			}
		}
		if (notasAbaixoDeCinco > 0) {
			situacaoNota = false;
		} else {
			situacaoNota = true;
		}
		return situacaoNota;
	}
	/**
	 * Este método verifica se o aluno tem cr > 7
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que está sendo verificado
	 * 
	 */
	public boolean verificarCRAAluno(Aluno aluno) {
		String craAlunoFormatado = aluno.getCra().replace(",", ".");
		if (Double.valueOf(craAlunoFormatado) > 7) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Este método verifica se o aluno está cursando o mínimo de disciplinas no semestre
	 * 
	 * TODO: verificação de eletivas
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informações completas de todas as disciplinas do curso
	 * 
	 */
	public boolean verificarSeCursaAoMenosTresDisciplinas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int contaDisciplinasMatriculadas = 0;
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (disciplina.getSituacao().equals("Matrícula")) {
				contaDisciplinasMatriculadas++;
			}
		}
		if (contaDisciplinasMatriculadas >= 3) {
			return true;
		}
		return false;
	}
	
	/**
	 * Este método verifica se o aluno consegue se formar no tempo certo
	 * 
	 * TODO: verificar regra genericamente
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informações completas de todas as disciplinas do curso
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que está sendo verificado
	 * 
	 */
	
	public boolean verificarSeAlunoIntegralizaNormalmente(HashMap<String, Disciplina> informacaoesDeDisciplinas, Aluno aluno) {
		Disciplina disciplina;
		int contaDisciplinasAprovadas = 0;
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (disciplina.getSituacao().equals("Aprovado")) {
				contaDisciplinasAprovadas++;
			}
		}
		
		if (contaDisciplinasAprovadas >= 6 && Integer.valueOf(aluno.getPeriodoAtual()) < 3) {
			return true;
		} else if (contaDisciplinasAprovadas >= 12 && Integer.valueOf(aluno.getPeriodoAtual()) < 4) {
			return true;
		} else if (contaDisciplinasAprovadas >= 19 && Integer.valueOf(aluno.getPeriodoAtual()) < 5) {
			return true;
		} else if (contaDisciplinasAprovadas >= 26 && Integer.valueOf(aluno.getPeriodoAtual()) < 6) {
			return true;
		} else if (contaDisciplinasAprovadas >= 33 && Integer.valueOf(aluno.getPeriodoAtual()) < 7) {
			return true;
		} else if (contaDisciplinasAprovadas >= 39 && Integer.valueOf(aluno.getPeriodoAtual()) < 8) {
			return true;
		} else if (contaDisciplinasAprovadas >= 45 && Integer.valueOf(aluno.getPeriodoAtual()) < 9) {
			return true;
		}
		return false;
	}

}
