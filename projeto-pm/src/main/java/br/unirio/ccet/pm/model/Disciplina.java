package br.unirio.ccet.pm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe para reconhecimento de informa��es referentes �s disciplinas no
 * Historico Escolar.
 * 
 * @author grupoPM
 * 
 */

public class Disciplina {

	/**
	 * Atributos da classe
	 * 
	 */
	@Getter @Setter
	private String codigo;
	
	@Getter @Setter
	private String nome;
	
	@Getter @Setter
	private String media;
	
	@Getter @Setter
	private String situacao;
	
	@Getter @Setter
	private int totalDeReprovacoes;

}
