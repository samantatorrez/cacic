package com.cacic.db.Impl;

import com.cacic.db.RevisionDao;
import com.cacic.db.TrabajoDao;
import com.cacic.db.UsuarioDao;

public class DAOFactory 
{
	public static RevisionDao getRevisionDao(String db) {
		switch(db.toUpperCase()) {
		   case "MYSQL" :
		   default : 
		      return new RevisionMysqlDAOImpl();
		}
	}
	public static UsuarioDao getUsuarioDao(String db) {
		switch(db.toUpperCase()) {
		   case "MYSQL" :
		   default : 
		      return new UsuarioMysqlDAOImpl();
		}
	}
	public static TrabajoDao getTrabajoDao(String db) {
		switch(db.toUpperCase()) {
		   case "MYSQL" :
		   default : 
		      return new TrabajoMysqlDAOImpl();
		}
	}
}
