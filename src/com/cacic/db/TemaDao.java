package com.cacic.db;

import java.util.List;

import com.cacic.entity.Tema;

public interface TemaDao {

	public String altaTema(Tema tema);
	public Tema getTema(String palabra);
	public List<Tema> getTemas();
	public void bajaTema(String palabra);
	public void eliminarDatos();
	
}
