package br.unirio.ccet.pm.exercicio3;

import java.util.Scanner;

/**
 * 
 * @author Yuri Lopam
 *
 */
public class Exercicio3 {

	private static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);
		double nota1, nota2, nota3, nota4, media;
		
		System.out.println("Entre com a sua primeira nota: ");
		nota1 = scanner.nextDouble();
		System.out.println("Entre com a sua segunda nota: ");
		nota2 = scanner.nextDouble();
		System.out.println("Entre com a sua terceira nota: ");
		nota3 = scanner.nextDouble();
		System.out.println("Entre com a sua quarta nota: ");
		nota4 = scanner.nextDouble();
		
		media = (nota1 + nota2 + nota3 + nota4) / 4;
		
		if(media >= 7)
			System.out.println("Aprovado!");
		else if (media < 7 && media >= 4)
			System.out.println("Prova final!");
		else
			System.out.println("Reprovado!");
		
	}

}
