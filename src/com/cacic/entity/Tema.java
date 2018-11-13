package com.cacic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Tema")
public class Tema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPalabra;
	@Column(nullable = false)
	private String palabra;
	@Column(nullable = false)
	private TipoPalabra Tipo;
	
	/**
	 * @param palabra
	 * @param tipo
	 */
	public Tema(String palabra, TipoPalabra tipo) {
		this.palabra = palabra;
		Tipo = tipo;
	}

	/**
	 * 
	 */
	public Tema() {
	}

	/**
	 * @return the idPalabra
	 */
	public Integer getIdPalabra() {
		return idPalabra;
	}

	/**
	 * @param idPalabra the idPalabra to set
	 */
	public void setIdPalabra(Integer idPalabra) {
		this.idPalabra = idPalabra;
	}

	/**
	 * @return the palabra
	 */
	public String getPalabra() {
		return palabra;
	}

	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	/**
	 * @return the tipo
	 */
	public TipoPalabra getTipo() {
		return Tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoPalabra tipo) {
		Tipo = tipo;
	}
	
	
}
