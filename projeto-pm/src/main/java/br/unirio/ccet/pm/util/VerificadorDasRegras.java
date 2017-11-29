package br.unirio.ccet.pm.util;

import java.util.HashMap;

import br.unirio.ccet.pm.model.Aluno;
import br.unirio.ccet.pm.model.Disciplina;

public class VerificadorDasRegras {
	/**
	 * Este m�todo verifica se o aluno deve ser jubilado ou n�o
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informa��es completas de todas as disciplinas do curso
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que est� sendo verificado
     * @return false se n�o for jubilado e true se for
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
	 * Este m�todo verifica se o aluno pode pedir integraliza��o
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que est� sendo verificado
	 * 
	 */
	public int verificarRegrasDeIntegralizacao(Aluno aluno) {

		int codigoRetornoIntegralizacao = 10;

		if (Integer.valueOf(aluno.getAnoEntrada()) <= 20132 && Integer.valueOf(aluno.getPeriodoAtual()) > 12) {
			codigoRetornoIntegralizacao = 0;
		} else if (Integer.valueOf(aluno.getAnoEntrada()) >= 20141) {
			if (Integer.valueOf(aluno.getPeriodoAtual()) >= 7) {
				if (Integer.valueOf(aluno.getPeriodoAtual()) >= 12) {
					codigoRetornoIntegralizacao = 1;
				} else {
					codigoRetornoIntegralizacao = 2;
				}
			}
		} else {
			codigoRetornoIntegralizacao = 3;
		}
		return codigoRetornoIntegralizacao;
	}
	/**
	 * Este m�todo verifica se o aluno tem nota para fazer a integraliza��o
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informa��es completas de todas as disciplinas do curso
	 * 
	 */
	public boolean verificarRegrasDeNotas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int notasAbaixoDeCinco = 0;
		boolean situacaoNota = true;
		
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
			situacaoNota = false;
		} else {
			situacaoNota = true;
		}
		return situacaoNota;
	}
	/**
	 * Este m�todo verifica se o aluno tem cr > 7
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que est� sendo verificado
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
	 * Este m�todo verifica se o aluno est� cursando o m�nimo de disciplinas no semestre
	 * 
	 * TODO: verifica��o de eletivas
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informa��es completas de todas as disciplinas do curso
	 * 
	 */
	public boolean verificarSeCursaAoMenosTresDisciplinas(HashMap<String, Disciplina> informacaoesDeDisciplinas) {
		Disciplina disciplina;
		int contaDisciplinasMatriculadas = 0;
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (disciplina.getSituacao().equals("Matr�cula")) {
				contaDisciplinasMatriculadas++;
			}
		}
		if (contaDisciplinasMatriculadas >= 3) {
			return true;
		}
		return false;
	}
	
	/**
	 * Este m�todo verifica se o aluno consegue se formar no tempo certo
	 * 
	 * TODO: verificar regra genericamente
	 * 
	 * @param informacaoesDeDisciplinas
	 *            (HashMap<String, Disciplina>) : informa��es completas de todas as disciplinas do curso
	 * 
	 * @param aluno
	 *            (Aluno) : aluno que est� sendo verificado
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
		
		boolean segundoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 3; 
		boolean aprovadoEmMaisDeSeisMaterias = contaDisciplinasAprovadas >= 6;
		if (aprovadoEmMaisDeSeisMaterias && segundoPeriodo) {
			return true;
		} 
		
		boolean terceiroPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 4;
		boolean aprovadoEmMaisDeDozeMaterias = contaDisciplinasAprovadas >= 12;
		if (aprovadoEmMaisDeDozeMaterias && terceiroPeriodo) {
			return true;
		} 
		
		boolean quartoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 5;
		boolean aprovadoEmMaisDeDezenoveMaterias = contaDisciplinasAprovadas >= 19;
		if (aprovadoEmMaisDeDezenoveMaterias && quartoPeriodo) {
			return true;
		}
		
		boolean aprovadoEmMaisDeVinteSeisMaterias = contaDisciplinasAprovadas >= 26;
		boolean quintoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 6;
		if (aprovadoEmMaisDeVinteSeisMaterias && quintoPeriodo) {
			return true;
		}
		
		
		boolean aprovadoEmMaisDeTrintaTresMaterias = contaDisciplinasAprovadas >= 33;
		boolean sextoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 7;
		if (aprovadoEmMaisDeTrintaTresMaterias && sextoPeriodo) {
			return true;
		}

		boolean aprovadoEmMaisDeTrintaNoveMaterias = contaDisciplinasAprovadas >= 39;
		boolean setimoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 8;
		if (aprovadoEmMaisDeTrintaNoveMaterias && setimoPeriodo) {
			return true;
		}
		
		
		boolean aprovadoEmMaisDeQuarentaCincoMaterias = contaDisciplinasAprovadas >= 45;
		boolean oitavoPeriodo = Integer.valueOf(aluno.getPeriodoAtual()) < 9;
		if (aprovadoEmMaisDeQuarentaCincoMaterias && oitavoPeriodo) {
			return true;
		}
		
		return false;
	}
	
	
	private void imprimirSextaRegra(boolean crAluno) {
		if (crAluno) {
			System.out.println("O CR do aluno � maior que 7,0.");
		} else {
			System.out.println("O CR do aluno � menor que 7,0.");
		}
	}

	private void imprimirQuintaRegra(boolean condicoesDeSeFormar) {
		if (condicoesDeSeFormar) {
			System.out.println("O aluno tem condi��es de se formar dentro do prazo regular.");
		} else {
			System.out.println("O aluno n�o tem condi��es de se formar dentro do prazo regular.");
		}
	}

	private void imprimirQuartaRegra(boolean materiasPorPeriodo) {
		if (materiasPorPeriodo) {
			System.out.println("O aluno est� cursando ao menos tr�s disciplinas.");
		} else {
			System.out.println("O aluno n�o est� cursando ao menos tr�s disciplinas.");
		}
	}

	private void imprimirTerceiraRegra(boolean situacaoNotas) {
		if (situacaoNotas) {
			System.out.println("O aluno possui pelo menos nota 5,0 nos semestres definidos no plano de integraliza��o.");
		} else {
			System.out.println("O aluno possui pelo menos nota 5,0 nos semestres definidos no plano de integraliza��o.");
		}
	}

	private void imprimirSegundaRegra(int integralizarAluno) {
		if (integralizarAluno == 0) {
			System.out.println("O aluno deve pedir prorroga��o de integraliza��o para o pr�ximo per�odo.");
		} else if (integralizarAluno == 1) {
			System.out.println("O aluno n�o pode mais integralizar o curso.");
		} else if (integralizarAluno == 2) {
			System.out.println("O aluno deve pedir prorroga��o de integraliza��o para o pr�ximo per�odo.");
		} else {
			System.out.println("A situa��o de integraliza��o do aluno est� normal.");
		}
	}

	private void imprimirPrimeiraRegra(boolean jubilarAluno) {
		if (jubilarAluno) {
			System.out.println("O aluno deve ser jubilado.");
		} else {
			System.out.println("O aluno n�o deve ser jubilado.");
		}
	}
	
	
	public void imprimirRegras(boolean jubilarAluno, int integralizarAluno, boolean situacaoNotas,
			boolean materiasPorPeriodo, boolean condicoesDeSeFormar, boolean crAluno) {
		imprimirPrimeiraRegra(jubilarAluno);
        imprimirSegundaRegra(integralizarAluno);
        imprimirTerceiraRegra(situacaoNotas);
        imprimirQuartaRegra(materiasPorPeriodo);
        imprimirQuintaRegra(condicoesDeSeFormar);
        imprimirSextaRegra(crAluno);
	}

}
