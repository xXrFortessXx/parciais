package com.rfs.parciaiscartola.datatypes;

import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class DetalheLigaBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839253050096710800L;
	/**
	 * 
	 */
	private int ligaID;
	private String nomeLiga;
	private String flamula;
	private String slug;
	private String descricao;
	private int timeDono;
	private List<TimesBean> times;
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public DetalheLigaBean(){
		this.setLigaID(0);
		this.nomeLiga="";
		this.flamula="";
		this.slug="";
		this.descricao="";
		this.setTimeDono(0);
	}
	/*getters and setters*/

	public String getNomeLiga() {
		return nomeLiga;
	}

	public void setNomeLiga(String nomeLiga) {
		this.nomeLiga = nomeLiga;
	}

	public String getFlamula() {
		return flamula;
	}

	public void setFlamula(String flamula) {
		this.flamula = flamula;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TimesBean> getTimes() {
		return times;
	}

	public void setTimes(List<TimesBean> times) {
		this.times = times;
	}

	public int getLigaID() {
		return ligaID;
	}

	public void setLigaID(int ligaID) {
		this.ligaID = ligaID;
	}

	public int getTimeDono() {
		return timeDono;
	}

	public void setTimeDono(int timeDono) {
		this.timeDono = timeDono;
	}
}
