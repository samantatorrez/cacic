package com.cacic.criterios;

public class CriterioAnd extends CriterioCompuesto {
	

	public CriterioAnd(Criterio criterio1, Criterio criterio2) {
		super(criterio1, criterio2);
	}

	@Override
	public boolean verify() {
		return criterio1.verify() && criterio2.verify();
	}
	
}
