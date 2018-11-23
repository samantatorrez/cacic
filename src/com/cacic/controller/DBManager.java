package com.cacic.controller;

import com.cacic.db.DAOFactory;
import com.cacic.db.RevisionDao;
import com.cacic.db.TemaDao;
import com.cacic.db.TrabajoDao;
import com.cacic.db.UsuarioDao;

public class DBManager {
	private String db = "MYSQL";

	public DBManager() {
	};

	public RevisionDao getRevisionDao() {
		return DAOFactory.getRevisionDao(db);
	}

	public UsuarioDao getUsuarioDao() {
		return DAOFactory.getUsuarioDao(db);
	}

	public TrabajoDao getTrabajoDao() {
		return DAOFactory.getTrabajoDao(db);
	}

	public TemaDao getTemaDao() {
		return DAOFactory.getTemaDao(db);
	}

	private void setDB(String db) {
		this.db = db;
	}
}