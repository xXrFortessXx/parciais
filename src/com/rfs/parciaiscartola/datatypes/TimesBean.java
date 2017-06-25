package com.rfs.parciaiscartola.datatypes;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class TimesBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839253050096710800L;
	/**
	 * 
	 */
	private String escudo;
	private String timeID;
	private String nome;
	private String nomeCartola;
	private String slug;
	private String patrimonio;
	private int posicaoTurno;
	private RankingTD ranking;
	private PontosTD pontos;
	private VariacaoTD variacao;
	private double pontuacaoParcial;
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public TimesBean(){
		this.escudo="";
		this.timeID="";
		this.nome="";
		this.nomeCartola="";
		this.slug="";
		this.patrimonio="";
	}
	/*getters and setters*/

	public String getEscudo() {
		return escudo;
	}

	public void setEscudo(String escudo) {
		this.escudo = escudo;
	}

	public String getTimeID() {
		return timeID;
	}

	public void setTimeID(String timeID) {
		this.timeID = timeID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCartola() {
		return nomeCartola;
	}

	public void setNomeCartola(String nomeCartola) {
		this.nomeCartola = nomeCartola;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public RankingTD getRanking() {
		return ranking;
	}

	public void setRanking(RankingTD ranking) {
		this.ranking = ranking;
	}

	public PontosTD getPontos() {
		return pontos;
	}

	public void setPontos(PontosTD pontos) {
		this.pontos = pontos;
	}

	public VariacaoTD getVariacao() {
		return variacao;
	}

	public void setVariacao(VariacaoTD variacao) {
		this.variacao = variacao;
	}

	public double getPontuacaoParcial() {
		return pontuacaoParcial;
	}

	public void setPontuacaoParcial(double pontuacaoParcial) {
		this.pontuacaoParcial = pontuacaoParcial;
	}

	public int getPosicaoTurno() {
		return posicaoTurno;
	}

	public void setPosicaoTurno(int posicaoTurno) {
		this.posicaoTurno = posicaoTurno;
	}

}
