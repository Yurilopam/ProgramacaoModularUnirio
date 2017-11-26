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
				disciplina.setNome(arrayDeCodigoENome[1].trim());
				codigoDisciplina = arrayDeCodigoENome[0].trim();
				informacaoesDeDisciplinas.put(codigoDisciplina, disciplina);
			}
			leitorDisciplinas.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Este método procura encontrar o status de aprovação do aluno em uma
	 * disciplina e armazená-lo no hashmap
	 * 
	 * @param historicoRefinado
	 *            (String) : um bloco menor [apenas com as disciplinas] do historico
	 *            escolar
	 * 
	 */
	public void encontrarAtributosDasDisciplinas(String historicoRefinado) {

		leitorDeHistorico = new Scanner(historicoRefinado);
		String codigo;
		Disciplina disciplina;

		while (leitorDeHistorico.hasNextLine()) {
			String linhaAtual = leitorDeHistorico.nextLine();
			leitor = new Scanner(linhaAtual);
			codigo = leitor.next();
			for (String codigoChave : informacaoesDeDisciplinas.keySet()) {
				if (codigo.equals(codigoChave)) {
					if (codigo.equals("HTD0058")) {
						String novaLinhaAtual = linhaAtual.concat(leitorDeHistorico.nextLine()).concat(" ")
														  .concat(leitorDeHistorico.nextLine());
						String[] separadorDeStatus = novaLinhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[10].trim());
						disciplina.setFrequencia(separadorDeStatus[11].trim());
						disciplina.setSituacao(separadorDeStatus[13].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComSetePalavrasNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[10].trim());
						disciplina.setFrequencia(separadorDeStatus[11].trim());
						disciplina.setSituacao(separadorDeStatus[13].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComSeisPalavrasNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[9].trim());
						disciplina.setFrequencia(separadorDeStatus[10].trim());
						disciplina.setSituacao(separadorDeStatus[12].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComCincoPalavrasNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[8].trim());
						disciplina.setFrequencia(separadorDeStatus[9].trim());
						disciplina.setSituacao(separadorDeStatus[11].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComTresPalavrasNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[6].trim());
						disciplina.setFrequencia(separadorDeStatus[7].trim());
						disciplina.setSituacao(separadorDeStatus[9].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComDuasPalavrasNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[5].trim());
						disciplina.setFrequencia(separadorDeStatus[6].trim());
						disciplina.setSituacao(separadorDeStatus[8].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else if (diciplinasComUmaPalavraNoNome(codigo)) {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[4].trim());
						disciplina.setFrequencia(separadorDeStatus[5].trim());
						disciplina.setSituacao(separadorDeStatus[7].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					} else {
						String[] separadorDeStatus = linhaAtual.split(" ");
						disciplina = informacaoesDeDisciplinas.get(codigo);
						disciplina.setMedia(separadorDeStatus[7].trim());
						disciplina.setFrequencia(separadorDeStatus[8].trim());
						disciplina.setSituacao(separadorDeStatus[10].trim());
						informacaoesDeDisciplinas.put(codigoChave, disciplina);
					}
					
				}

			}

		}

	}

	private boolean diciplinasComSetePalavrasNoNome(String codigo) {
		return codigo.equals("TIN0141") || codigo.equals("TIN0164") || codigo.equals("TIN0162") || codigo.equals("TIN0163");
	}
	
	private boolean diciplinasComSeisPalavrasNoNome(String codigo) {
		return codigo.equals("TIN0125");
	}
	
	private boolean diciplinasComCincoPalavrasNoNome(String codigo) {
		return codigo.equals("TIN0112") || codigo.equals("TME0112") || codigo.equals("TIN0054") || codigo.equals("TME0113") || 
				codigo.equals("TIN0055") || codigo.equals("TIN0160") || codigo.equals("TIN0132") || codigo.equals("TIN0056") || 
				codigo.equals("TIN0171") || codigo.equals("HTD0004") || codigo.equals("TIN0057");
	}
	
	private boolean diciplinasComTresPalavrasNoNome(String codigo) {
		return codigo.equals("TIN0108") || codigo.equals("TIN0150") || codigo.equals("TIN0115");
	}
	
	private boolean diciplinasComDuasPalavrasNoNome(String codigo) {
		return codigo.equals("TME0101") || codigo.equals("TME0015") || codigo.equals("TIN0110") || codigo.equals("TIN0174") || 
				codigo.equals("TIN0109") || codigo.equals("TIN0116") || codigo.equals("TIN0117") || codigo.equals("HDI0143") || 
				codigo.equals("TIN0122") || codigo.equals("TIN0118") || codigo.equals("TIN0148") || codigo.equals("TIN0159") || 
				codigo.equals("TIN0121");
	}
	
	private boolean diciplinasComUmaPalavraNoNome(String codigo) {
		return codigo.equals("TIN0130") || codigo.equals("TME0114") || codigo.equals("TME0115");
	}


	public HashMap<String, Disciplina> getInformacaoesDeDisciplinas() {
		return informacaoesDeDisciplinas;
	}
	
}
