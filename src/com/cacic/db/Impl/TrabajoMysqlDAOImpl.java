package com.cacic.db.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cacic.db.MysqlDao;
import com.cacic.db.TrabajoDao;
import com.cacic.entity.Trabajo;

public class TrabajoMysqlDAOImpl extends MysqlDao implements TrabajoDao{
	private String name = "Trabajo";
	
	public Integer altaTrabajo(Trabajo trabajo) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			eManager.persist(trabajo);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajo.getIdTrabajo();
	}
	public Trabajo getTrabajo(Integer id) {
		Trabajo trabajo = null;
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			trabajo = eManager.find(Trabajo.class, id);
			return trabajo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajo;
	}
	public List<Trabajo> getTrabajos() {
		List<Trabajo> trabajos = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			trabajos  = eManager.createQuery(
			         "Select a From "+getName()+" a", Trabajo.class).getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return trabajos;
	}
	public void bajaTrabajo(Integer id) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
		
			Trabajo trabajo = eManager.find(Trabajo.class, id);
			eManager.remove(trabajo);
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
		List<Trabajo> trabajos = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			trabajos  = eManager.createQuery(
			         "Select a From "+getName()+" a", Trabajo.class).getResultList();
			for(Trabajo t: trabajos) {
				eManager.remove(t);
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
	public List<Trabajo> getTrabajosCategoria(String categoria) {
		List<Trabajo> trabajos = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			TypedQuery<Trabajo> query = eManager.createQuery(
			         "Select a From "+getName()+" a Where categoria=:categoria", Trabajo.class);
			query.setParameter("categoria", categoria );
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
}