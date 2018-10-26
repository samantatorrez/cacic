package com.cacic.db.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.cacic.db.MysqlDao;
import com.cacic.db.UsuarioDao;
import com.cacic.entity.Usuario;

public class UsuarioMysqlDAOImpl extends MysqlDao implements UsuarioDao{
	private String name = "Usuario";
	
	public Integer altaUsuario(Usuario usuario) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			eManager.persist(usuario);
			eManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return usuario.getIdUsuario();
	}
	public Usuario getUsuario(Integer id) {
		Usuario usuario = null;
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			usuario = eManager.find(Usuario.class, id);
			return usuario;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return usuario;
	}
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			usuarios  = eManager.createQuery(
			         "Select u From " +getName()+ " u", Usuario.class).getResultList();
			return usuarios;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}finally{
			eManager.close();
		}
		return usuarios;
	}
	public void bajaUsuario(Integer id) {
		EntityManager eManager=null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
		
			Usuario usuario = eManager.find(Usuario.class, id);
			eManager.remove(usuario);
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
		List<Usuario> usuarios = null;
		EntityManager eManager= null;
		try{
			eManager = getEntityManager();
			eManager.getTransaction().begin();
			usuarios  = eManager.createQuery(
			         "Select a From "+getName()+" a", Usuario.class).getResultList();
			for(Usuario u: usuarios) {
				eManager.remove(u);
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
