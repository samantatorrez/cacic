package com.cacic.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="Trabajo")
public class Trabajo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTrabajo;
	@Column(nullable = false)
	private String categoria;
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
		this.categoria = "";
		this.palabrasClaves = "";
		this.autor = autor;
		this.revisiones = new ArrayList<Revision>();
	}
	
	public Trabajo(String categoria, String palabrasClaves, Usuario autor, List<Revision> revisiones) {
		this.idTrabajo = null;
		this.categoria = categoria;
		this.palabrasClaves = palabrasClaves;
		this.autor = autor;
		this.revisiones = revisiones;
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
}
