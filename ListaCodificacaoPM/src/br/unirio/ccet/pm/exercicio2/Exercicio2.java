package br.unirio.ccet.pm.exercicio2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Yuri Lopam
 *
 */
public class Exercicio2 {

	private static Scanner scanner;
	
	public static void main(String[] args) {
		
		scanner = new Scanner(System.in); 
		int numero, totalDivisores = 0, divisor;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		StringBuilder divisores = new StringBuilder();
		
		System.out.println("Entre com um número inteiro positivo: ");
		numero = scanner.nextInt();
		
		for (divisor = 1; divisor <= numero; divisor++) {
		      if (numero % divisor == 0)
		          totalDivisores++;
		}
		totalDivisores = 0;
		for (divisor = 1; divisor <= numero; divisor++) {
			++totalDivisores;
			if (numero % totalDivisores == 0) {
				arrayList.add(totalDivisores);
			}
		}
		
		divisores.append("Os divisores do número ").append(numero).append(" são: ");
		
		for (Integer integer : arrayList) {
			divisores.append(integer.toString()).append(" ");
		}
		
		System.out.println(divisores.toString());
		
	}

}
