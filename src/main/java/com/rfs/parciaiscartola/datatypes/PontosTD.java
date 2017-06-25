package com.rfs.parciaiscartola.datatypes;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class PontosTD implements java.io.Serializable{
	
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
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public PontosTD(){
		this.rodada="";
		this.mes="";
		this.turno="";
		this.campeonato="";
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

}
