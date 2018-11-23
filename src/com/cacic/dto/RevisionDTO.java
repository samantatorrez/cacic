package com.cacic.dto;

import java.sql.Date;

import com.cacic.entity.Revision;


public class RevisionDTO {
	private Integer idRevision;
	private String estado;
	private Date fechaCreacion;
	private Integer idRevisor;
	private Integer idTrabajo;
	
	public RevisionDTO() {}
	
	public RevisionDTO(Integer idRevision, String estado, Date fechaCreacion, Integer idRevisor, Integer idTrabajo) {
		this.idRevision = idRevision;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.idRevisor = idRevisor;
		this.idTrabajo = idTrabajo;
	}
	public RevisionDTO(Revision revision) {
		this.idRevision = revision.getIdRevision();
		this.estado = revision.getEstado();
		this.fechaCreacion = revision.getFechaCreacion();
		this.idRevisor = revision.getEvaluador().getIdUsuario();
		this.idTrabajo = revision.getTrabajo().getIdTrabajo();
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
	public Integer getIdRevisor() {
		return idRevisor;
	}
	public void setIdRevisor(Integer idRevisor) {
		this.idRevisor = idRevisor;
	}
	public Integer getIdTrabajo() {
		return idTrabajo;
	}
	public void setIdTrabajo(Integer idTrabajo) {
		this.idTrabajo = idTrabajo;
	}
	
}
