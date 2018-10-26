package com.cacic.db;

import com.cacic.db.Impl.RevisionMysqlDAOImpl;
import com.cacic.db.Impl.TrabajoMysqlDAOImpl;
import com.cacic.db.Impl.UsuarioMysqlDAOImpl;

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
