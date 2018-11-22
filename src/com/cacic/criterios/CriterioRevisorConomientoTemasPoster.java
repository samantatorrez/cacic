package com.cacic.criterios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public class CriterioRevisorConomientoTemasPoster extends CriterioSimple {
	public CriterioRevisorConomientoTemasPoster(Usuario r, Trabajo t) {
		super(r, t);
	}
	private static final String POSTER= "POSTER";
	public boolean verify() {
		if (trabajo.getCategoria().toUpperCase().equals(POSTER)) {
			List<String> list = new ArrayList<String>(Arrays.asList(trabajo.getPalabrasClaves().split(" , ")));
			String temas= "," + revisor.getTemas().toLowerCase()+",";
			for(String palabra: list) {
				if(temas.contains(","+palabra.toLowerCase()+",")) {
					return true;
				}
			}
		} 
		return false;
	}
}
