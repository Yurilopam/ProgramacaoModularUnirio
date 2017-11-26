package br.unirio.ccet.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import br.unirio.ccet.pm.model.Disciplina;

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
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeDisciplinas.get(codigo);
					String[] separadorNomeDisciplina = disciplina.getNome().split(" ");
					int tamanhoDoNomeDisciplina = separadorNomeDisciplina.length;
					String[] separadorDeStatus = linhaAtual.split(" ");
					String notaDisciplina = separadorDeStatus[tamanhoDoNomeDisciplina + 2];
					if(!notaDisciplina.equals(" ")){
						disciplina.setMedia(notaDisciplina);
					}
					else {
						disciplina.setMedia("nota vazia");
					}
				}
			}
		}

	}


	/**
	 * Este m�todo procura encontrar o status de aprova��o do aluno em uma
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
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					disciplina = informacaoesDeDisciplinas.get(codigo);
					String[] separadorDeStatus = linhaAtual.split("-");
					if(codigo.equals("TIN0110")){
						disciplina.setSituacao(separadorDeStatus[2]);
					}
					else if (codigo.equals("HTD0058")) {
						String novaLinhaAtual = linhaAtual.concat(leitorDeHistorico.nextLine())
								.concat(leitorDeHistorico.nextLine());
						separadorDeStatus = novaLinhaAtual.split("-");
						disciplina.setSituacao(separadorDeStatus[1]);
					}
					else {
						disciplina.setSituacao(separadorDeStatus[1]);
					}
				}
			}
		}

	}
	
	
	public HashMap<String, Disciplina> getInformacaoesDeDisciplinas() {
		return informacaoesDeDisciplinas;
	}
	
}
