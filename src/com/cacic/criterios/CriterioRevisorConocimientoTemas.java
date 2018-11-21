package com.cacic.criterios;

import java.util.List;

import com.cacic.entity.Tema;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorConocimientoTemas extends CriterioSimple {

	public CriterioRevisorConocimientoTemas(Usuario r, Trabajo t) {
		super(r, t);
	}

	public boolean verify() {
		boolean contieneTodos=true;
		List<Tema> list = trabajo.getPalabrasClaves();
		List<Tema> temas= revisor.getTemas();
		for(Tema palabra: list) {
			if(!temas.contains(palabra)) {
				return false;
			}
		}
		return contieneTodos;
	}
}
