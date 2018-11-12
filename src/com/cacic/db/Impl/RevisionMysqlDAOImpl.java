package com.cacic.db.Impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cacic.db.MysqlDao;
import com.cacic.db.RevisionDao;
import com.cacic.entity.Revision;
import com.cacic.entity.Trabajo;

public class RevisionMysqlDAOImpl extends MysqlDao implements RevisionDao{
	private String name = "Revision";
	private static RevisionMysqlDAOImpl instance = null;
	
	private RevisionMysqlDAOImpl() {}
	
	@Override
	public Integer altaRevision(Revision revision) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			eManager.persist(revision);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return revision.getIdRevision();
	}

	@Override
	public Revision getRevision(Integer id) {
		Revision revision = null;
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			revision = eManager.find(Revision.class, id);
			return revision;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return revision;
	}

	@Override
	public List<Revision> getRevisiones() {
		List<Revision> revisiones = null;
		EntityManager eManager = null;
		try {
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			revisiones = eManager.createQuery(
			         "Select a From "+getName()+" a", Revision.class).getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return revisiones;
	}
	@Override
	public List<Trabajo> getTrabajosByEvaluador(Integer id) {
		List<Trabajo> trabajos = null;
		EntityManager eManager = null;
		try {
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			TypedQuery<Trabajo> query = eManager.createQuery(
			         "Select a.trabajo From "+getName()+" a Where a.evaluador.idUsuario=:id", Trabajo.class);
			query.setParameter("id", id);
			trabajos = query.getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajos;
	}

	@Override
	public void bajaRevision(Integer id) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
		
			Revision revision = eManager.find(Revision.class, id);
			eManager.remove(revision);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
	}
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
	@Override
	public List<Revision> getTrabajosByEvaluadorAndDateRange(Integer id, Date desde, Date hasta) {
		List<Revision> revisiones = null;
		EntityManager eManager = null;
		try {
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			TypedQuery<Revision> query = eManager.createQuery(
			         "Select a From "+getName()+" a Where a.evaluador.idUsuario=:id "
			         		+ "And a.fechaCreacion<=:hasta And a.fechaCreacion>=:desde", Revision.class);
			query.setParameter("id", id);
			query.setParameter("desde", desde);
			query.setParameter("hasta", hasta);
			revisiones = query.getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return revisiones;
	}
	@Override
	public List<Trabajo> getTrabajosByAutor(Integer id) {
		List<Trabajo> trabajos = null;
		EntityManager eManager = null;
		try {
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			TypedQuery<Trabajo> query = eManager.createQuery(
			         "Select a.trabajo From "+getName()+" a Where a.trabajo.autor.idUsuario=:id", Trabajo.class);
			query.setParameter("id", id);
			trabajos = query.getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajos;
	}
	
	//Cambiar por clase tema
	@Override
	public List<Trabajo> getTrabajosByAutorRevisorArea(Integer autorId, Integer revisorId, String area) {
		List<Trabajo> trabajos = null;
		EntityManager eManager = null;
		try {
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			TypedQuery<Trabajo> query = eManager.createQuery(
			         "Select a.trabajo From "+getName()+" a Where  a.evaluador.idUsuario=:revisorId "
			         		+ " And a.trabajo.autor.idUsuario=:autorId "
			         		+ "And concat(',', a.trabajo.palabrasClaves, ',') "
			         		+ "Like concat('%,', :area, ',%')", Trabajo.class);
			query.setParameter("autorId", autorId);
			query.setParameter("revisorId", revisorId);
			query.setParameter("area", area);
			trabajos = query.getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajos;
	}

	public static RevisionMysqlDAOImpl getInstance() {
		if (instance == null)
	        instance = new RevisionMysqlDAOImpl();
	 
	     return instance;
	}
}
