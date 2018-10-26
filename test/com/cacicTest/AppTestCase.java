package com.cacicTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cacic.db.DAOFactory;
import com.cacic.db.RevisionDao;
import com.cacic.db.TrabajoDao;
import com.cacic.db.UsuarioDao;
import com.cacic.entity.Revision;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

import junit.framework.TestCase;

public class AppTestCase extends TestCase {
	private UsuarioDao usuarioDao;
	private TrabajoDao trabajoDao;
	private RevisionDao revisionDao;
	private static final String DB = "MYSQL";

	@Before
	public void setUp() {
		usuarioDao = DAOFactory.getUsuarioDao(DB);
		trabajoDao = DAOFactory.getTrabajoDao(DB);
		revisionDao = DAOFactory.getRevisionDao(DB);
	}

	/*
	 * Punto b
	 */
	@Test
	public void testCrearUsuarios() {
		int cantidadUsuarios = 10;
		Usuario usuario;
		for (int i = 0; i < cantidadUsuarios; i++) {
			usuario = new Usuario();
			usuarioDao.altaUsuario(usuario);
		}
		List<Usuario> usuarios = usuarioDao.getUsuarios();
		assertEquals(cantidadUsuarios, usuarios.size());
	}

	/*
	 * Punto c
	 */
	@Test
	public void testCrearTrabajos() {
		int cantidadTrabajos = 10;
		Trabajo trabajo;
		Usuario usuario = new Usuario();
		Integer id = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(id);

		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajoDao.altaTrabajo(trabajo);
		}
		List<Trabajo> trabajos = trabajoDao.getTrabajos();
		assertEquals(cantidadTrabajos, trabajos.size());
	}

	@Test
	public void testObtenerTrabajosPorCategoria() {
		int cantidadTrabajos = 10;
		Trabajo trabajo;
		Usuario usuario = new Usuario();
		Integer id = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(id);

		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajo.setCategoria("Articulo");
			trabajoDao.altaTrabajo(trabajo);
		}
		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajoDao.altaTrabajo(trabajo);
		}

		List<Trabajo> trabajos = trabajoDao.getTrabajosCategoria("Articulo");
		assertEquals(cantidadTrabajos, trabajos.size());
	}
	
	/*
	 * Punto d i
	 */
	@Test
	public void testConsultaDeTodosLosDatosUsuario() {
		//Crea un usuario
		Usuario usuario = new Usuario();
		usuario.setNombre("test1");
		usuario.setApellido("testApellido");
		usuario.setCodPostal(7000);
		usuario.setContrasenia("1234");
		usuario.setDomicilio("testDomicilio");
		usuario.setFechaNac(new Date(System.currentTimeMillis()));
		usuario.setLugarTrabajo("globant");
		usuario.setNombreUsuario("testUser");
		usuario.setRol("autor");
		usuario.setTemas("ciencias,programación");
		//Obtiene el id despues de darlo de alta
		Integer id = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(id);
		//Obtine el usuario que esta en la base y lo compara con el creado 
		assertEquals(usuario, usuarioDao.getUsuario(id));
	}
	/*
	 * Punto d ii
	 */
	@Test
	public void testDadoRevisorObtenerTrabajosAsignados() {
		// Revisor del que se espera encontrar un Trabajo asignado
		Usuario revisor = new Usuario();
		revisor.setRol("revisor");
		Usuario otroRevisor = new Usuario();
		otroRevisor.setRol("revisor");
		
		Usuario autor = new Usuario();
		autor.setRol("autor");
		Trabajo trabajo = new Trabajo(autor);
		
		//Autor cuyo trabajo no es asignado a el revisor
		Usuario autorNoAsignado = new Usuario();
		autorNoAsignado.setRol("autor");
		Trabajo trabajoNoAsignado = new Trabajo(autorNoAsignado);
		
		autor.setIdUsuario(usuarioDao.altaUsuario(autor));
		autorNoAsignado.setIdUsuario(usuarioDao.altaUsuario(autorNoAsignado));
		revisor.setIdUsuario(usuarioDao.altaUsuario(revisor));
		otroRevisor.setIdUsuario(usuarioDao.altaUsuario(otroRevisor));
		trabajo.setIdTrabajo(trabajoDao.altaTrabajo(trabajo));
		trabajoNoAsignado.setIdTrabajo(trabajoDao.altaTrabajo(trabajoNoAsignado));
		
		//En revision van a haber 2 trabajos, pero solo uno corresponde al revisor
		Revision revision = new Revision(revisor, trabajo);
		Revision revision2 = new Revision(otroRevisor, trabajoNoAsignado);
		revision.setIdRevision(revisionDao.altaRevision(revision));
		revision2.setIdRevision(revisionDao.altaRevision(revision2));
		
		List<Trabajo> trabajos = revisionDao.getTrabajosByEvaluador(revisor.getIdUsuario());
		assertEquals(1,trabajos.size());
		assertEquals(trabajo,trabajos.get(0));
		
	}

	/*
	 * Punto g
	 */
	@After
	public void tearDown() {
		revisionDao.eliminarDatos();
		trabajoDao.eliminarDatos();
		usuarioDao.eliminarDatos();
	}
}
