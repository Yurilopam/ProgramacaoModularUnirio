package br.unirio.ccet.pm.model;

/**
 * Classe para reconhecimento de informações referentes às disciplinas no
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
	private String codigo;
	private String nome;
	private String media;
	private String situacao;
	private int totalDeReprovacoes;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
			
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getTotalDeReprovacoes() {
		return totalDeReprovacoes;
	}

	public void setTotalDeReprovacoes(int totalDeReprovacoes) {
		this.totalDeReprovacoes = totalDeReprovacoes;
	}

}
