package com.cacicTest;

import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cacic.db.DAOFactory;
import com.cacic.db.RevisionDao;
import com.cacic.db.TrabajoDao;
import com.cacic.db.UsuarioDao;
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
	public void testCrearTrabajosDeInvestigacion() {
		int cantidadTrabajos = 10;
		Trabajo trabajo;
		Usuario usuario = new Usuario();
		Integer id = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(id);

		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajo.setCategoria("Investigación");
			trabajoDao.altaTrabajo(trabajo);
		}
		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajoDao.altaTrabajo(trabajo);
		}

		List<Trabajo> trabajos = trabajoDao.getTrabajosCategoria("Investigación");
		assertEquals(cantidadTrabajos, trabajos.size());
	}
	
	@Test
	public void testConsultaDeTodosLosDatosUsuario() {
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
		Integer id = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(id);
		assertEquals(usuario, usuarioDao.getUsuario(id));
	}

	@After
	public void tearDown() {
		revisionDao.eliminarDatos();
		trabajoDao.eliminarDatos();
		usuarioDao.eliminarDatos();
	}
}
