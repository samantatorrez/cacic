package com.cacic.criterios;

import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

public abstract class CriterioSimple implements Criterio{
	protected Usuario revisor;
	protected Trabajo trabajo;
	public CriterioSimple(Usuario r, Trabajo t) {
		this.revisor=r;
		this.trabajo=t;
	}
	public abstract boolean verify();
}
