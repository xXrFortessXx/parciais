package com.rfs.parciaiscartola.datatypes;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@Scope("session")
public class LigasBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839253050096710800L;
	/**
	 * 
	 */
	private String ligaID;
	private String nomeLiga;
	private String flamula;
	private String slug;
	private String posCampeonato;
	private String timeDono;
	
	/*construtor atribui vazio aos atributos
    para que não sejam mostrados valores nulos na página */
	public LigasBean(){
		this.ligaID="";
		this.nomeLiga="";
		this.flamula="";
		this.slug="";
		this.posCampeonato="";
		this.timeDono="";
	}
	/*getters and setters*/

	public String getLigaID() {
		return ligaID;
	}

	public void setLigaID(String ligaID) {
		this.ligaID = ligaID;
	}

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

	public String getPosCampeonato() {
		return posCampeonato;
	}

	public void setPosCampeonato(String posCampeonato) {
		this.posCampeonato = posCampeonato;
	}

	public String getTimeDono() {
		return timeDono;
	}

	public void setTimeDono(String timeDono) {
		this.timeDono = timeDono;
	}
}
