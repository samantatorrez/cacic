package com.cacic.dto;

import com.cacic.entity.Trabajo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrabajoDTO {
	private Integer idTrabajo;
	private String categoria;
	private String palabrasClaves;
	private Integer idAutor;
	
	public TrabajoDTO () {}

	public TrabajoDTO(Integer idTrabajo, String categoria, String palabrasClaves, Integer idAutor) {
		this.idTrabajo = idTrabajo;
		this.categoria = categoria;
		this.palabrasClaves = palabrasClaves;
		this.idAutor = idAutor;
	}

	public TrabajoDTO(String categoria, String palabrasClaves, Integer idAutor) {
		this.idTrabajo = null;
		this.categoria = categoria;
		this.palabrasClaves = palabrasClaves;
		this.idAutor = idAutor;
	}
	
	public TrabajoDTO(Trabajo trabajo) {
		this.idTrabajo = trabajo.getIdTrabajo();
		this.categoria = trabajo.getCategoria().name();
		this.palabrasClaves = trabajo.getPalabrasClaves();
		this.idAutor = trabajo.getAutor().getIdUsuario();
	}


	public Integer getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(Integer idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPalabrasClaves() {
		return palabrasClaves;
	}

	public void setPalabrasClaves(String palabrasClaves) {
		this.palabrasClaves = palabrasClaves;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((idAutor == null) ? 0 : idAutor.hashCode());
		result = prime * result + ((idTrabajo == null) ? 0 : idTrabajo.hashCode());
		result = prime * result + ((palabrasClaves == null) ? 0 : palabrasClaves.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrabajoDTO other = (TrabajoDTO) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (idAutor == null) {
			if (other.idAutor != null)
				return false;
		} else if (!idAutor.equals(other.idAutor))
			return false;
		if (idTrabajo == null) {
			if (other.idTrabajo != null)
				return false;
		} else if (!idTrabajo.equals(other.idTrabajo))
			return false;
		if (palabrasClaves == null) {
			if (other.palabrasClaves != null)
				return false;
		} else if (!palabrasClaves.equals(other.palabrasClaves))
			return false;
		return true;
	}
}
