package br.unirio.ccet.pm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author grupoPM
 *
 */
public class Aluno {

	/**
	 * Atributos da classe
	 * 
	 * */
	@Getter @Setter	
	private String nome;
	
	@Getter @Setter
	private String anoEntrada;
	
	@Getter @Setter
	private String periodoAtual;
	
	@Getter @Setter
	private boolean foiJubilado;
	
	@Getter @Setter
	private int CRA;
	
	@Getter @Setter
	private boolean formarNoPrazo; //verificar se ele pode se formar no prazo ou não
	
	@Getter @Setter
	private int qtdeMateriasAprovadas; //contador para casos de integralização

}
