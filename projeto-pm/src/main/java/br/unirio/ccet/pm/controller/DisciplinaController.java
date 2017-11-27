package br.unirio.ccet.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import br.unirio.ccet.pm.model.Disciplina;

/**
 * 
 * @author grupoPM
 *
 */
public class DisciplinaController {
	
	private HashMap<String, Disciplina> informacaoesDeDisciplinas = new HashMap<String, Disciplina>();
	private Scanner leitor;
	private Scanner leitorDeHistorico;
	
	/**
	 * 
	 * Este m�todo l� e armazena em hashmap uma lista .txt de disciplinas e c�digos
	 * que ser� usada posteriormente.
	 * 
	 * @param caminhoLista(String)
	 *            : endere�o da lista na m�quina
	 * 
	 */
	public void importarListaDisciplinas(String caminhoLista) throws IOException {
		String codigoDisciplina;
		String disciplinaRetiradaDaLista;
		Disciplina disciplina;
		
		File listaDisciplina = new File(caminhoLista);
		String[] arrayDeCodigoENome;

		try {
			Scanner leitorDisciplinas = new Scanner(listaDisciplina);
			while (leitorDisciplinas.hasNextLine()) {
				disciplinaRetiradaDaLista = leitorDisciplinas.nextLine();
				arrayDeCodigoENome = disciplinaRetiradaDaLista.split(":");
				disciplina = new Disciplina();
				disciplina.setCodigo(arrayDeCodigoENome[0].trim());
				disciplina.setNome(arrayDeCodigoENome[1].toLowerCase().trim());
				codigoDisciplina = arrayDeCodigoENome[0].trim();
				informacaoesDeDisciplinas.put(codigoDisciplina, disciplina);
			}
			leitorDisciplinas.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Este m�todo procura encontrar a media de aprova��o do aluno em uma
	 * disciplina e armazen�-lo no hashmap
	 * 
	 * @param historicoRefinado
	 *            (String) : um bloco menor [apenas com as disciplinas] do historico
	 *            escolar
	 * 
	 */
	
	public void encontrarNotaDisciplinas(String historicoRefinado) {

		leitorDeHistorico = new Scanner(historicoRefinado);
		String codigo;
		Disciplina disciplina;

		while (leitorDeHistorico.hasNextLine()) {
			String notaDisciplina;
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				disciplina = informacaoesDeDisciplinas.get(codigo);
				if (codigo.equals(codigoChave)) {
					if (codigo.equals("HTD0058")){
					String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
					int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
					String[] separadorDeStatus = linhaAtual.concat(leitorDeHistorico.nextLine()).concat(" ")
															   .concat(leitorDeHistorico.nextLine()).split(" ");
					notaDisciplina = recuperarNotaNoArray(tamanhoDoNomeDisciplina, separadorDeStatus);									
					}
				else {
					String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
					int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
					String[] separadorDeStatus = linhaAtual.split(" ");
					notaDisciplina = recuperarNotaNoArray(tamanhoDoNomeDisciplina, separadorDeStatus);				
						
					}	
					
					disciplina.setMedia(notaDisciplina);
				}
			}
		}
		
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (StringUtils.isEmpty(disciplina.getMedia())) {
				disciplina.setMedia("disciplina n�o cursada");
			}
		}

	}
	
	/**
	 * Este m�todo procura encontrar a situa��o em que o aluno est� em uma
	 * disciplina e armazen�-lo no hashmap
	 * 
	 * @param historicoRefinado
	 *            (String) : um bloco menor [apenas com as disciplinas] do historico
	 *            escolar
	 * 
	 */
	
public void encontrarSituacaoDisciplinas(String historicoRefinado) {
		leitorDeHistorico = new Scanner(historicoRefinado);
		String codigo;
		Disciplina disciplina;

		while (leitorDeHistorico.hasNextLine()) {
			String situacaoDisciplina;
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeDisciplinas.get(codigo);
					if (codigo.equals("HTD0058")) {
						String novaLinhaAtual = linhaAtual.concat(leitorDeHistorico.nextLine())
								.concat(leitorDeHistorico.nextLine());
						String[] separadorDeStatus = linhaAtual.concat(leitorDeHistorico.nextLine()).concat(" ")
															   .concat(leitorDeHistorico.nextLine()).split(" ");
						situacaoDisciplina = separadorDeStatus[separadorDeStatus.length - 1];
					}
					else {
						String[] separadorDeStatus = linhaAtual.split(" ");
						situacaoDisciplina = separadorDeStatus[separadorDeStatus.length - 1];

					}
				disciplina.setSituacao(situacaoDisciplina);
				}
			}
		}
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (StringUtils.isEmpty(disciplina.getSituacao())){
				disciplina.setSituacao(" ");
			}
		}
		

	}
/**
 * Este m�todo d� um set em todos os atributos essenciais da disciplina
 * 
 * @param historicoRefinado
 *            (String) : um bloco menor [apenas com as disciplinas] do historico
 *            escolar
 * 
 */
	
public void encontrarAtributosDisciplinas(String historicoRefinado) {
		encontrarReprovacoesDisciplinas(historicoRefinado);
		encontrarSituacaoDisciplinas(historicoRefinado);	
		encontrarNotaDisciplinas(historicoRefinado);
}
	
/**
 * Este m�todo procura encontrar de reprova��es que um aluno tem em uma
 * disciplina e armazen�-lo no hashmap
 * 
 * @param historicoRefinado
 *            (String) : um bloco menor [apenas com as disciplinas] do historico
 *            escolar
 * 
 */
	
public void encontrarReprovacoesDisciplinas(String historicoRefinado) {

		leitorDeHistorico = new Scanner(historicoRefinado);
		String codigo;
		Disciplina disciplina;
		int reprovacoes = 0;
		
		while (leitorDeHistorico.hasNextLine()) {
			String linhaAtual = leitorDeHistorico.nextLine();
			String notaDisciplina;
			String situacaoDisciplina;
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			int totalDeReprovacoes = 0;
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeDisciplinas.get(codigo);
					if (codigo.equals("HTD0058")) {
						String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
						int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
						String[] separadorDeStatus = linhaAtual.concat(leitorDeHistorico.nextLine()).concat(" ")
															   .concat(leitorDeHistorico.nextLine()).split(" ");
						notaDisciplina = recuperarNotaNoArray(tamanhoDoNomeDisciplina, separadorDeStatus);
						totalDeReprovacoes = reprovacoes + recuperarTotalDeReprovacoes(tamanhoDoNomeDisciplina, separadorDeStatus) + disciplina.getTotalDeReprovacoes();
					} else {
						String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
						int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
						String[] separadorDeStatus = linhaAtual.split(" ");
						totalDeReprovacoes = reprovacoes + recuperarTotalDeReprovacoes(tamanhoDoNomeDisciplina, separadorDeStatus) + disciplina.getTotalDeReprovacoes();
					}
					disciplina.setTotalDeReprovacoes(totalDeReprovacoes);
				} 
			}
		}
		
		
	}
	
	
/**
 * Este m�todo traz o posicionamento da nota dentro da linha do hist�rico
 * em forma de array
 * 
 * @param tamanhoDoNomeDisciplina
 *            (int) : quantidade de palavras que cont�m o nome de uma disciplina
 * 
 * @param separadorDeStatus
 *            (String[]) : array com a linha do hist�rico na qual se encontra uma disciplina
 *            separado por " ".
 * 
 */
	

	private String recuperarNotaNoArray(int tamanhoDoNomeDisciplina, String[] separadorDeStatus) {
		String notaDisciplina;
		if (disciplinaSemNota(tamanhoDoNomeDisciplina, separadorDeStatus)) {
			notaDisciplina = "sem nota";
		} else {
			notaDisciplina = separadorDeStatus[tamanhoDoNomeDisciplina + 3];
		}
		return notaDisciplina;
	}

	/**
	 * Este m�todo trata as disciplinas que n�o possuem nota no hist�rico, sejam elas por estarem
	 * sendo cursadas ou por terem status de aprova��o diferenciado
	 * 
	 * @param tamanhoDoNomeDisciplina
	 *            (int) : quantidade de palavras que cont�m o nome de uma disciplina
	 * 
	 * @param separadorDeStatus
	 *            (String[]) : array com a linha do hist�rico na qual se encontra uma disciplina
	 *            separado por " ".
	 * 
	 */
	private boolean disciplinaSemNota(int tamanhoDoNomeDisciplina, String[] separadorDeStatus) {
		if (separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("ASC") || 
				separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("APV") || 
				separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("REF") || 
				separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("DIS")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Este m�todo conta o n�mero de reprova��es que aquela disciplina teve no hist�rico
	 * 
	 * @param tamanhoDoNomeDisciplina
	 *            (int) : quantidade de palavras que cont�m o nome de uma disciplina
	 * 
	 * @param separadorDeStatus
	 *            (String[]) : array com a linha do hist�rico na qual se encontra uma disciplina
	 *            separado por " ".
	 * 
	 */
	private int recuperarTotalDeReprovacoes(int tamanhoDoNomeDisciplina, String[] separadorDeStatus) {
		if (disciplinaSemNota(tamanhoDoNomeDisciplina, separadorDeStatus)) {
			if (separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("REP") || 
					separadorDeStatus[tamanhoDoNomeDisciplina + 4].equals("REF")) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (separadorDeStatus[tamanhoDoNomeDisciplina + 5].equals("REP") || 
					separadorDeStatus[tamanhoDoNomeDisciplina + 5].equals("REF")) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	/**
	 * Este m�todo retorna o HashMap de informa��es de disciplina
	 
	 * 
	 */
	public HashMap<String, Disciplina> getInformacaoesDeDisciplinas() {
		return informacaoesDeDisciplinas;
	}
	
}
