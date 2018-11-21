package com.cacic.criterios;

import java.util.List;

import com.cacic.entity.Tema;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorConomientoTemasPoster extends CriterioSimple {
	public CriterioRevisorConomientoTemasPoster(Usuario r, Trabajo t) {
		super(r, t);
	}
	private static final String POSTER= "POSTER";
	public boolean verify() {
		if (trabajo.getCategoria().equals(POSTER)) {
			List<Tema> list = trabajo.getPalabrasClaves();
			List<Tema> temas= revisor.getTemas();
			for(Tema palabra: list) {
				if(temas.contains(palabra)) {
					return true;
				}
			}
		} 
		return false;
	}
}
