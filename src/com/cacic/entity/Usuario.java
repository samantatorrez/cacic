package com.cacic.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

@Entity
@Table(name="Usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	@Column(nullable = false)
	private String rol;
	@Column(nullable = false)
	private String lugarTrabajo;
	@Column(nullable = false)
	private String nombreUsuario;
	@Column(nullable = false)
	private String contrasenia;

	private String temas;
	private Date fechaNac;
	private String domicilio;
	private int codPostal;
	
	
	@OneToMany(mappedBy="evaluador", cascade = CascadeType.REMOVE, orphanRemoval=true)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Revision> revisiones;
	@OneToMany(mappedBy="autor", cascade = CascadeType.REMOVE, orphanRemoval=true)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Trabajo> trabajos;

	public Usuario() {
		this.idUsuario = null;
		this.nombre = "";
		this.apellido = "";
		this.rol = "";
		this.lugarTrabajo = "";
		this.nombreUsuario = "root";
		setContrasenia("1234");
		this.temas = "";
		this.fechaNac = null;
		this.domicilio = "";
		this.codPostal = -1;
		this.revisiones = new ArrayList<Revision>();
		this.trabajos = new ArrayList<Trabajo>();
	}

	public Usuario( String nombre, String apellido, String rol, String lugarTrabajo, String nombreUsuario,
			String contrasenia, String temas, Date fechaNac, String domicilio, int codPostal, List<Revision> revisiones,
			List<Trabajo> trabajos) {
		this.idUsuario = null;
		this.nombre = nombre;
		this.apellido = apellido;
		this.rol = rol;
		this.lugarTrabajo = lugarTrabajo;
		this.nombreUsuario = nombreUsuario;
		setContrasenia(contrasenia);
		this.temas = temas;
		this.fechaNac = fechaNac;
		this.domicilio = domicilio;
		this.codPostal = codPostal;
		this.revisiones = revisiones;
		this.trabajos = trabajos;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getLugarTrabajo() {
		return lugarTrabajo;
	}
	public void setLugarTrabajo(String lugarTrabajo) {
		this.lugarTrabajo = lugarTrabajo;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasenia() {
		return this.contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor ();
		passwordEncryptor.setAlgorithm("SHA-1");
		passwordEncryptor.setPlainDigest(true);
		this.contrasenia = passwordEncryptor.encryptPassword(contrasenia);
	}
	public String getTemas() {
		return temas;
	}
	public void setTemas(String temas) {
		this.temas = temas;
	}
	public Date getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public int getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
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
	public List<Trabajo> getTrabajos() {
		return trabajos;
	}
	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}
	public void addTrabajos(Trabajo trabajo) {
		this.trabajos.add(trabajo);
	}
	
}
