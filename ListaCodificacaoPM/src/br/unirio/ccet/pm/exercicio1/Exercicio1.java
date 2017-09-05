package br.unirio.ccet.pm.exercicio1;

import java.util.Scanner;

/**
 * 
 * @author Yuri Lopam
 *
 */
public class Exercicio1 {

	private static Scanner scanner;
	
	public static void main(String[] args) {
		
		scanner = new Scanner(System.in); 
		int lado1, lado2, lado3, somaDe1e2, somaDe2e3, somaDe1e3;
		
		System.out.println("Entre com o primeiro lado do triângulo: ");
		lado1 = scanner.nextInt();
		System.out.println("Entre com o segundo lado do triângulo: ");
		lado2 = scanner.nextInt();
		System.out.println("Entre com o terceiro lado do triângulo: ");
		lado3 = scanner.nextInt();
		
		somaDe1e2 = lado1 + lado2;
		somaDe2e3 = lado2 + lado3;
		somaDe1e3 = lado1 + lado3;
		
		if (lado1 >= somaDe2e3 || lado2 >= somaDe1e3 || lado3 >= somaDe1e2) {
			System.out.println("Seu triângulo é irregular, você não entrou com dados válidos vacilão!");
		} else {
			if (lado1 == lado2 && lado2 == lado3) {
				System.out.println("Seu triângulo é equilátero.");
			} else if (lado1 == lado2 || lado2 == lado3 || lado1 == lado3) {
				System.out.println("Seu triângulo é isósceles.");
			} else {
				System.out.println("Seu triângulo é escaleno.");
			}
		}
		
	}

}
