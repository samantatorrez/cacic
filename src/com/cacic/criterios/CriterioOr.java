package com.cacic.criterios;

public class CriterioOr extends CriterioCompuesto {
	

	public CriterioOr(Criterio criterio1, Criterio criterio2) {
		super(criterio1, criterio2);
	}

	@Override
	public boolean verify() {
		return criterio1.verify() || criterio2.verify();
	}
	
}
