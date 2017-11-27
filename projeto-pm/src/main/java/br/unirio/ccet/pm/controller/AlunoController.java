package br.unirio.ccet.pm.controller;

import java.util.Scanner;

import br.unirio.ccet.pm.model.Aluno;
import lombok.Getter;

/**
 * 
 * @author grupoPM
 *
 */
public class AlunoController {

	private static final String INDEX_FINAL = "Situação Local\r\n";

	private Scanner leitorDeHistoricoRefinado;
	private Scanner leitorDeHistoricoExtraido;
	
	@Getter
	Aluno aluno = new Aluno();
	
	public void encontrarCRAdoAluno(String historicoEscolarRefinado) {
		leitorDeHistoricoRefinado = new Scanner(historicoEscolarRefinado);
		String[] arrayComCRA;
		
		while (leitorDeHistoricoRefinado.hasNextLine()) {
			String linhaAtual = leitorDeHistoricoRefinado.nextLine();
			if (linhaAtual.contains("Coeficiente de Rendimento Geral")) {
				arrayComCRA = linhaAtual.split(" ");
				aluno.setCra(arrayComCRA[arrayComCRA.length - 1]);
			}
		}
	}

	public void encontrarDadosDePeriodoDoAluno(String historicoEscolarExtraido) {
		int indexComeco = 0;
		int indexFim = historicoEscolarExtraido.indexOf(INDEX_FINAL);
		String historicoEscolarRefinado = historicoEscolarExtraido.substring(indexComeco , indexFim);
		leitorDeHistoricoExtraido = new Scanner(historicoEscolarRefinado);
		
		while (leitorDeHistoricoExtraido.hasNextLine()) {
			String linhaAtual = leitorDeHistoricoExtraido.nextLine();
			if (linhaAtual.contains("210 - ")) {
				linhaAtual = leitorDeHistoricoExtraido.nextLine();
				String anoPeriodoIngressoAluno = linhaAtual.substring(0, 5);
				aluno.setAnoEntrada(anoPeriodoIngressoAluno);
			}
			if (linhaAtual.contains("Período Atual:")) {
				String periodoAtual = linhaAtual.substring(linhaAtual.indexOf("Período Atual:") + 15, 16);
				aluno.setPeriodoAtual(periodoAtual);
			}
		}
		
	}

}
