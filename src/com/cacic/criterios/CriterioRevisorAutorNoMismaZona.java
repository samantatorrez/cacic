package com.cacic.criterios;

import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorAutorNoMismaZona extends CriterioSimple{
	public CriterioRevisorAutorNoMismaZona(Usuario r, Trabajo t) {
		super(r, t);
	}

	public boolean verify() {
		return (revisor.getCodPostal()!=trabajo.getAutor().getCodPostal()) 
				|| !(revisor.getLugarTrabajo().equals(trabajo.getAutor().getLugarTrabajo())) ;
	}
}
