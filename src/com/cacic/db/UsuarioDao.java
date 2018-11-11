package com.cacic.db;

import java.util.List;

import com.cacic.entity.Usuario;

public interface UsuarioDao {
	
	public Integer altaUsuario(Usuario usuario);
	public Usuario getUsuario(Integer id);
	public List<Usuario> getUsuarios();
	public void bajaUsuario(Integer id);
	public void eliminarDatos();
	
}
