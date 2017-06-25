package com.rfs.parciaiscartola.datatypes;

import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class UsuarioBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8999097742822503378L;
	private String emailUsu;
	private String senhaUsu;
	private String tokenSession;
	private List<LigasBean> ligas;
	private List<JogadorTD> jogadores;
	private List<JogadorTD> timeUsuario;
	private DetalheLigaBean ligaSelecionada;
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public UsuarioBean(){
		this.emailUsu="";
		this.senhaUsu="";
		this.tokenSession="";
	}
	/*getters and setters*/
	public String getEmail() {
		return emailUsu;
	}
	public void setEmail(String email) {
		this.emailUsu = email;
	}
	public String getSenha() {
		return senhaUsu;
	}
	public void setSenha(String senha) {
		this.senhaUsu = senha;
	}
	public String getTokenSession() {
		return tokenSession;
	}
	public void setTokenSession(String tokenSession) {
		this.tokenSession = tokenSession;
	}
	public List<LigasBean> getLigas() {
		return ligas;
	}
	public void setLigas(List<LigasBean> ligas) {
		this.ligas = ligas;
	}
	public DetalheLigaBean getLigaSelecionada() {
		return ligaSelecionada;
	}
	public void setLigaSelecionada(DetalheLigaBean ligaSelecionada) {
		this.ligaSelecionada = ligaSelecionada;
	}
	public List<JogadorTD> getJogadores() {
		return jogadores;
	}
	public void setJogadores(List<JogadorTD> jogadores) {
		this.jogadores = jogadores;
	}
	public List<JogadorTD> getTimeUsuario() {
		return timeUsuario;
	}
	public void setTimeUsuario(List<JogadorTD> timeUsuario) {
		this.timeUsuario = timeUsuario;
	}
}
