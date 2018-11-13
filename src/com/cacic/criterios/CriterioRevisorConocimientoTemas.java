package com.cacic.criterios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorConocimientoTemas extends CriterioSimple {

	public CriterioRevisorConocimientoTemas(Usuario r, Trabajo t) {
		super(r, t);
	}

	public boolean verify() {
		boolean contieneTodos=true;
		List<String> list = new ArrayList<String>(Arrays.asList(trabajo.getPalabrasClaves().split(" , ")));
		String temas= "," + revisor.getTemas().toLowerCase()+",";
		for(String palabra: list) {
			if(!temas.contains(","+palabra.toLowerCase()+",")) {
				return false;
			}
		}
		return contieneTodos;
	}
}
