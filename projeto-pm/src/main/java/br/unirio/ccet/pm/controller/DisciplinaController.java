package br.unirio.ccet.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import br.unirio.ccet.pm.model.Disciplina;
import lombok.Getter;

/**
 * 
 * @author grupoPM
 *
 */
public class DisciplinaController {
	
	private HashMap<String, Disciplina> informacaoesDeDisciplinasObrigatoriasOptativas = new HashMap<String, Disciplina>();
	private HashMap<String, Disciplina> informacaoesDeDisciplinasEletivas = new HashMap<String, Disciplina>();
	@Getter
	private HashMap<String, Disciplina> informacaoesDeTodasDisciplinas = new HashMap<String, Disciplina>();
	private Scanner leitor;
	private Scanner leitorDeHistorico;
	
	/**
	 * 
	 * Este método lê e armazena em hashmap uma lista .txt de disciplinas e códigos
	 * que será usada posteriormente.
	 * 
	 * @param caminhoLista(String)
	 *            : endereço da lista na máquina
	 * 
	 */
	private void importarListaDisciplinasObrigatoriasOptativas(String caminhoLista) throws IOException {
		String codigoDisciplina;
		String disciplinaRetiradaDaLista;
		Disciplina disciplina;
		
		File listaDisciplina = new File(caminhoLista);
		String[] arrayDeCodigoENome;

		Scanner leitorDisciplinas = new Scanner(listaDisciplina);
		while (leitorDisciplinas.hasNextLine()) {
			disciplinaRetiradaDaLista = leitorDisciplinas.nextLine();
			arrayDeCodigoENome = disciplinaRetiradaDaLista.split(":");
			disciplina = new Disciplina();
			disciplina.setCodigo(arrayDeCodigoENome[0].trim());
			disciplina.setNome(arrayDeCodigoENome[1].toLowerCase().trim());
			codigoDisciplina = arrayDeCodigoENome[0].trim();
			informacaoesDeDisciplinasObrigatoriasOptativas.put(codigoDisciplina, disciplina);
		}
		leitorDisciplinas.close();
	}
	
	private void importarListaDisciplinasEletivas(String historicoEscolarRefinado) {
		
		leitorDeHistorico = new Scanner(historicoEscolarRefinado);
		leitorDeHistorico.nextLine();
		leitorDeHistorico.nextLine();
		leitorDeHistorico.nextLine();
		
		String codigo;
		
		while (leitorDeHistorico.hasNextLine()) {
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			
			String[] separadorLinhaAtual = linhaAtual.split(" ");
			if (!informacaoesDeDisciplinasObrigatoriasOptativas.keySet().contains(codigo) && 
					verificarSeLinhaEhDeDisciplina(separadorLinhaAtual)) {
				String abreviacaoDisciplina = verificarAbreviaturaDisciplina(separadorLinhaAtual);
				String[] separadorPorAbreviacaoLinhaAtual = linhaAtual.split(abreviacaoDisciplina);
				if (abreviacaoDisciplina.equals("ASC") || abreviacaoDisciplina.equals("DIS")) {
					int redutorTamanhoArray = 3;
					adicionarDisciplinaEletivaNoHashMap(codigo, separadorPorAbreviacaoLinhaAtual, redutorTamanhoArray);
				} else {
					int redutorTamanhoArray = 4;
					adicionarDisciplinaEletivaNoHashMap(codigo, separadorPorAbreviacaoLinhaAtual, redutorTamanhoArray);
				}
			}
		}
	}

	private void adicionarDisciplinaEletivaNoHashMap(String codigo, String[] separadorPorAbreviacaoLinhaAtual, int redutorTamanhoArray) {
		String nomeDisciplina = "";
		Disciplina disciplina;
		String[] arrayCodigoNomeDisciplina = separadorPorAbreviacaoLinhaAtual[0].split(" ");
		for (int i = 1; i < arrayCodigoNomeDisciplina.length - redutorTamanhoArray; i++) {
			nomeDisciplina = nomeDisciplina.concat(arrayCodigoNomeDisciplina[i]).concat(" ");
		}
		disciplina = new Disciplina();
		disciplina.setCodigo(codigo.trim());
		disciplina.setNome(nomeDisciplina.toLowerCase().trim());
		String codigoDisciplina = codigo.trim();
		informacaoesDeDisciplinasEletivas.put(codigoDisciplina, disciplina);
	}
	
	private String verificarAbreviaturaDisciplina(String[] separadorLinhaAtual) {
		for (int i = 0; i < separadorLinhaAtual.length; i++) {
			switch (separadorLinhaAtual[i]) {
			case "ASC":
				return "ASC";
			case "APV-":
				return "APV-";
			case "REF":
				return "REF";
			case "APV":
				return "APV";
			case "REP":
				return "REP";
			case "DIS":
				return "DIS";
			}
		}
		return "";
	}

	private boolean verificarSeLinhaEhDeDisciplina(String[] separadorLinhaAtual) {
		
		if (separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("Aprovado") || 
				separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("falta") || 
				separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("por") || 
				separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("de") || 
				separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("nota") || 
				separadorLinhaAtual[separadorLinhaAtual.length - 1].equals("Matrícula"))
			return true;
		return false;
	}

	
	public void importarListaTodasDisciplinas(String caminhoLista, String historicoEscolarRefinado) throws IOException {
		
		importarListaDisciplinasObrigatoriasOptativas(caminhoLista);
		importarListaDisciplinasEletivas(historicoEscolarRefinado);
		
		informacaoesDeTodasDisciplinas.putAll(informacaoesDeDisciplinasObrigatoriasOptativas);
		informacaoesDeTodasDisciplinas.putAll(informacaoesDeDisciplinasEletivas);
		
	}
	

	/**
	 * Este método procura encontrar a media de aprovação do aluno em uma
	 * disciplina e armazená-lo no hashmap
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
			for (String codigoChave : informacaoesDeTodasDisciplinas.keySet()) {
				disciplina = informacaoesDeTodasDisciplinas.get(codigo);
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
		
		for (String codigoChave : informacaoesDeTodasDisciplinas.keySet()) {
			disciplina = informacaoesDeTodasDisciplinas.get(codigoChave);
			if (StringUtils.isEmpty(disciplina.getMedia())) {
				disciplina.setMedia("disciplina não cursada");
			}
		}

	}
	
	/**
	 * Este método procura encontrar a situação em que o aluno está em uma
	 * disciplina e armazená-lo no hashmap
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
			for (String codigoChave : informacaoesDeTodasDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeTodasDisciplinas.get(codigo);
					if (codigo.equals("HTD0058")) {
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
		for (String codigoChave : informacaoesDeTodasDisciplinas.keySet()) {
			disciplina = informacaoesDeTodasDisciplinas.get(codigoChave);
			if (StringUtils.isEmpty(disciplina.getSituacao())){
				disciplina.setSituacao(" ");
			}
		}
		

	}
	
	/**
	 * Este método dá um set em todos os atributos essenciais da disciplina
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
	 * Este método procura encontrar de reprovações que um aluno tem em uma
	 * disciplina e armazená-lo no hashmap
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
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			int totalDeReprovacoes = 0;
			for (String codigoChave : informacaoesDeTodasDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeTodasDisciplinas.get(codigo);
					if (codigo.equals("HTD0058")) {
						String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
						int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
						String[] separadorDeStatus = linhaAtual.concat(leitorDeHistorico.nextLine()).concat(" ")
															   .concat(leitorDeHistorico.nextLine()).split(" ");
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
 * Este método traz o posicionamento da nota dentro da linha do histórico
 * em forma de array
 * 
 * @param tamanhoDoNomeDisciplina
 *            (int) : quantidade de palavras que contém o nome de uma disciplina
 * 
 * @param separadorDeStatus
 *            (String[]) : array com a linha do histórico na qual se encontra uma disciplina
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
	 * Este método trata as disciplinas que não possuem nota no histórico, sejam elas por estarem
	 * sendo cursadas ou por terem status de aprovação diferenciado
	 * 
	 * @param tamanhoDoNomeDisciplina
	 *            (int) : quantidade de palavras que contém o nome de uma disciplina
	 * 
	 * @param separadorDeStatus
	 *            (String[]) : array com a linha do histórico na qual se encontra uma disciplina
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
	 * Este método conta o número de reprovações que aquela disciplina teve no histórico
	 * 
	 * @param tamanhoDoNomeDisciplina
	 *            (int) : quantidade de palavras que contém o nome de uma disciplina
	 * 
	 * @param separadorDeStatus
	 *            (String[]) : array com a linha do histórico na qual se encontra uma disciplina
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
	
}
