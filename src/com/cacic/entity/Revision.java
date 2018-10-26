package com.cacic.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Revision")
public class Revision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRevision;
	@Column(nullable = false)
	private String estado;
	@Column(nullable = false)
	private Date fechaCreacion;
	

	@ManyToOne
	@JoinColumn(name ="FK_evaluador")
	private Usuario evaluador;
	@ManyToOne
	@JoinColumn(name ="FK_trabajo")
	private Trabajo trabajo;
	
	public Revision() {}
	public Revision(Usuario usuario,Trabajo trabajo) {
		this.idRevision = null;
		this.estado = "";
		this.fechaCreacion = new Date(System.currentTimeMillis());
		this.evaluador = usuario;
		this.trabajo = trabajo;
	}
	
	public Revision(Integer idRevision, String estado, Date fechaCreacion, Usuario evaluador, Trabajo trabajo) {
		this.idRevision = idRevision;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.evaluador = evaluador;
		this.trabajo = trabajo;
	}

	public Integer getIdRevision() {
		return idRevision;
	}
	public void setIdRevision(Integer idRevision) {
		this.idRevision = idRevision;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Usuario getEvaluador() {
		return evaluador;
	}
	public void setEvaluador(Usuario evaluador) {
		this.evaluador = evaluador;
	}
	public Trabajo getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}
}
