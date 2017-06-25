package com.rfs.parciaiscartola.datatypes;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class JogadorTD implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839253050096710800L;
	/**
	 * 
	 */
	private String apelido;
	private double pontuacao;
	private String foto;
	private int atletaId;
	private int clubeId;
	private int posicaoId;
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public JogadorTD(){
		this.apelido="";
		this.pontuacao=0;
		this.foto="";
	}
	/*getters and setters*/
	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public int getClubeId() {
		return clubeId;
	}
	public void setClubeId(int clubeId) {
		this.clubeId = clubeId;
	}
	public int getPosicaoId() {
		return posicaoId;
	}
	public void setPosicaoId(int posicaoId) {
		this.posicaoId = posicaoId;
	}
	public int getAtletaId() {
		return atletaId;
	}
	public void setAtletaId(int atletaId) {
		this.atletaId = atletaId;
	}
}
