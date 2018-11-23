package com.cacic.db.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.cacic.db.MysqlDao;
import com.cacic.db.TemaDao;
import com.cacic.entity.Tema;
import com.cacic.entity.TipoPalabra;

public class TemaMysqlDAOImpl extends MysqlDao implements TemaDao {

	private String name = "Tema";
	private static TemaMysqlDAOImpl instance = null;
	
	private TemaMysqlDAOImpl() {}
	
	@Override
	public String altaTema(Tema tema) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			eManager.persist(tema);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return tema.getPalabra();
	}

	@Override
	public Tema getTema(String palabra) {
		Tema tema = null;
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			tema = eManager.find(Tema.class, palabra);
			return tema;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return tema;
	}

	@Override
	public List<Tema> getTemas() {
		List<Tema> temas = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			temas = eManager.createQuery(
			        "Select a From "+ getName() +" a", Tema.class).getResultList();
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return temas;
	}

	@Override
	public void bajaTema(String palabra) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			Tema tema = eManager.find(Tema.class, palabra);
			eManager.remove(tema);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
	}

	@Override
	public void eliminarDatos() {
		List<Tema> temas = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			temas = eManager.createQuery(
			        "Select a From "+ getName() +" a", Tema.class).getResultList();
			for(Tema t: temas) {
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
	public String getName() {
		return name;
	}
	
	public static TemaMysqlDAOImpl getInstance() {
		if (instance == null)
	        instance = new TemaMysqlDAOImpl();
	 
	     return instance;
	}
	
}