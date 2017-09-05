package br.unirio.ccet.pm.exercicio4;

import java.util.Scanner;

/**
 * 
 * @author Yuri Lopam
 *
 */
public class Exercicio4 {

	private static final String[] RESULTADOS = {
			"Flamengo", "3", "0", "Volta Redonda",
			"Volta Redonda", "1", "1", "Botafogo",
			"Bonsucesso", "5", "0", "Macaé",
			"Macaé", "3", "4", "Fluminense",
			"Botafogo", "2", "2", "Flamengo"
	};
	
	private static Scanner scanner;
	
	public static void main(String[] args) {
		
		scanner = new Scanner(System.in);
		String time;
		int golsVisitante, golsCasa, pontosTotais = 0;
		
		System.out.println("Entre com o nome do seu time: ");
		time = scanner.nextLine();
		
		for (int i = 0; i < RESULTADOS.length; i++ ) {
			if (RESULTADOS[i].equalsIgnoreCase(time)) {
				if (i != 0) {
					if (RESULTADOS[i-1].length() == 1) {
						golsVisitante = Integer.parseInt(RESULTADOS[i-1].toString());
						golsCasa = Integer.parseInt(RESULTADOS[i-2].toString());
						if (golsVisitante == golsCasa) {
							pontosTotais += 1;
						} else if (golsVisitante > golsCasa) {
							pontosTotais += 3;
						}
					} else if (RESULTADOS[i+1].length() == 1) {
						golsCasa = Integer.parseInt(RESULTADOS[i+1].toString());
						golsVisitante = Integer.parseInt(RESULTADOS[i+2].toString());
						if (golsCasa == golsVisitante) {
							pontosTotais += 1;
						} else if (golsCasa > golsVisitante) {
							pontosTotais += 3;
						}
					}
				} else {
					golsCasa = Integer.parseInt(RESULTADOS[i+1].toString());
					golsVisitante = Integer.parseInt(RESULTADOS[i+2].toString());
					if (golsCasa == golsVisitante) {
						pontosTotais += 1;
					} else if (golsCasa > golsVisitante) {
						pontosTotais += 3;
					}
				}
			}
		}
		
		System.out.println("O " + time + " obteve " + pontosTotais + " pontos no campeonato.");
		
	}

}
