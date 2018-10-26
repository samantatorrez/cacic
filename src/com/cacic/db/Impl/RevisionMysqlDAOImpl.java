package com.cacic.db.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.cacic.db.MysqlDao;
import com.cacic.db.RevisionDao;
import com.cacic.entity.Revision;

public class RevisionMysqlDAOImpl extends MysqlDao implements RevisionDao{
	private String name = "Revision";
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void eliminarDatos() {
		List<Revision> revisiones = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			revisiones  = eManager.createQuery(
			         "Select a From "+getName()+" a", Revision.class).getResultList();
			for(Revision r: revisiones) {
				eManager.remove(r);
			}
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
	}
}
