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
	 * Este método lê e armazena em hashmap uma lista .txt de disciplinas e códigos
	 * que será usada posteriormente.
	 * 
	 * @param caminhoLista(String)
	 *            : endereço da lista na máquina
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
	 * Este método procura encontrar a media de aprovação do aluno em uma
	 * disciplina e armazená-lo no hashmap
	 * 
	 * @param historicoRefinado
	 *            (String) : um bloco menor [apenas com as disciplinas] do historico
	 *            escolar
	 * 
	 */
	public void encontrarAtributosDisciplinas(String historicoRefinado) {

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
						situacaoDisciplina = separadorDeStatus[separadorDeStatus.length - 1];
						totalDeReprovacoes = reprovacoes + recuperarTotalDeReprovacoes(tamanhoDoNomeDisciplina, separadorDeStatus) + disciplina.getTotalDeReprovacoes();
					} else {
						String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
						int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
						String[] separadorDeStatus = linhaAtual.split(" ");
						notaDisciplina = recuperarNotaNoArray(tamanhoDoNomeDisciplina, separadorDeStatus);
						situacaoDisciplina = separadorDeStatus[separadorDeStatus.length - 1];
						totalDeReprovacoes = reprovacoes + recuperarTotalDeReprovacoes(tamanhoDoNomeDisciplina, separadorDeStatus) + disciplina.getTotalDeReprovacoes();
					}
					disciplina.setMedia(notaDisciplina);
					disciplina.setSituacao(situacaoDisciplina);
					disciplina.setTotalDeReprovacoes(totalDeReprovacoes);
				} 
			}
		}
		for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
			disciplina = informacaoesDeDisciplinas.get(codigoChave);
			if (StringUtils.isEmpty(disciplina.getMedia())) {
				disciplina.setMedia("disciplina não cursada");
			}
			if (StringUtils.isEmpty(disciplina.getSituacao())){
				disciplina.setSituacao(" ");
			}
		}
		
	}


	private String recuperarNotaNoArray(int tamanhoDoNomeDisciplina, String[] separadorDeStatus) {
		String notaDisciplina;
		if (disciplinaSemNota(tamanhoDoNomeDisciplina, separadorDeStatus)) {
			notaDisciplina = "sem nota";
		} else {
			notaDisciplina = separadorDeStatus[tamanhoDoNomeDisciplina + 3];
		}
		return notaDisciplina;
	}


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
	
	
	public HashMap<String, Disciplina> getInformacaoesDeDisciplinas() {
		return informacaoesDeDisciplinas;
	}
	
}
