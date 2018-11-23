package com.cacic.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.cacic.dto.TrabajoDTO;

@Entity
@Table(name="Trabajo")
public class Trabajo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTrabajo;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Categoria categoria;
	@Column(nullable = false)
	private String palabrasClaves;
	
	@ManyToOne
	@JoinColumn(name ="FK_autor")
	private Usuario autor;
	
	@OneToMany(mappedBy="trabajo", cascade = CascadeType.REMOVE, orphanRemoval=true)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Revision> revisiones;

	public Trabajo() {}
	
	public Trabajo(Usuario autor) {
		this.idTrabajo = null;
		this.categoria = Categoria.articulo;
		this.palabrasClaves = "";
		this.autor = autor;
		this.revisiones = new ArrayList<Revision>();
	}
	
	public Trabajo(Categoria categoria, String palabrasClaves, Usuario autor, List<Revision> revisiones) {
		this.idTrabajo = null;
		this.categoria = categoria;
		this.palabrasClaves = palabrasClaves;
		this.autor = autor;
		this.revisiones = revisiones;
	}
	
	public Trabajo(TrabajoDTO trabajo, Usuario autor) {
		this.idTrabajo = null;
		this.categoria = Categoria.valueOf(trabajo.getCategoria());
		this.palabrasClaves = trabajo.getPalabrasClaves();
		this.autor = autor;
		this.revisiones =  new ArrayList<Revision>();
	}

	public Integer getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(Integer idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getPalabrasClaves() {
		return palabrasClaves;
	}

	public void setPalabrasClaves(String palabrasClaves) {
		this.palabrasClaves = palabrasClaves;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public List<Revision> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(List<Revision> revisiones) {
		this.revisiones = revisiones;
	}
	
	public void addRevisiones(Revision revision) {
		this.revisiones.add(revision);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((idTrabajo == null) ? 0 : idTrabajo.hashCode());
		result = prime * result + ((palabrasClaves == null) ? 0 : palabrasClaves.hashCode());
		result = prime * result + ((revisiones == null) ? 0 : revisiones.hashCode());
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
		Trabajo other = (Trabajo) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.getIdUsuario().equals(other.autor.getIdUsuario()))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
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
