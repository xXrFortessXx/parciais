package com.rfs.parciaiscartola.datatypes;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class RankingTD implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839253050096710800L;
	/**
	 * 
	 */
	private String rodada;
	private String mes;
	private String turno;
	private String campeonato;
	private String patrimonio;
	
	/*construtor atribui vazio aos atributos
    para que n�o sejam mostrados valores nulos na p�gina */
	public RankingTD(){
		this.rodada="";
		this.mes="";
		this.turno="";
		this.campeonato="";
		this.patrimonio="";
	}
	/*getters and setters*/

	public String getRodada() {
		return rodada;
	}

	public void setRodada(String rodada) {
		this.rodada = rodada;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(String campeonato) {
		this.campeonato = campeonato;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

}
