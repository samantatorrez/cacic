package com.cacic.criterios;

public abstract class CriterioCompuesto implements Criterio{
	protected Criterio criterio1;
	protected Criterio criterio2;
	

	public CriterioCompuesto(Criterio criterio1, Criterio criterio2) {
		this.criterio1 = criterio1;
		this.criterio2 = criterio2;
	}


	public abstract boolean verify() ;
}
