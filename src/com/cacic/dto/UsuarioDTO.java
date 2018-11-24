package com.cacic.dto;

import java.sql.Date;

import com.cacic.entity.Rol;
import com.cacic.entity.Usuario;

public class UsuarioDTO {

	private Integer idUsuario;
	private String nombre;
	private String apellido;
	private Rol rol;
	private String lugarTrabajo;
	private String nombreUsuario;
	private String contrasenia;
	private String temas;
	private Date fechaNac;
	private String domicilio;
	private int codPostal;
	
	public UsuarioDTO(Integer idUsuario, String nombre, String apellido, Rol rol, String lugarTrabajo,
			String nombreUsuario, String contrasenia, String temas, Date fechaNac, String domicilio, int codPostal) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.rol = rol;
		this.lugarTrabajo = lugarTrabajo;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.temas = temas;
		this.fechaNac = fechaNac;
		this.domicilio = domicilio;
		this.codPostal = codPostal;
	}

	public UsuarioDTO(Usuario usuario) {
		this.idUsuario = usuario.getIdUsuario();
		this.nombre = usuario.getNombre();
		this.apellido = usuario.getApellido();
		this.rol = usuario.getRol();
		this.lugarTrabajo = usuario.getLugarTrabajo();
		this.nombreUsuario = usuario.getNombreUsuario();
		this.contrasenia = usuario.getContrasenia();
		this.temas = usuario.getTemas();
		this.fechaNac = usuario.getFechaNac();
		this.domicilio = usuario.getDomicilio();
		this.codPostal = usuario.getCodPostal();
	}
	
	public UsuarioDTO() {}

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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
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
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

}