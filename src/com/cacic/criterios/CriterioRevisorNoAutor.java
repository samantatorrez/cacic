package com.cacic.criterios;

import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorNoAutor extends CriterioSimple{
	
	public CriterioRevisorNoAutor(Usuario r, Trabajo t) {
		super(r, t);
	}

	public boolean verify() {
		return !revisor.getIdUsuario().equals(trabajo.getAutor().getIdUsuario());
	}
}
