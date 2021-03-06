package com.cacicTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cacic.db.DAOFactory;
import com.cacic.db.RevisionDao;
import com.cacic.db.TemaDao;
import com.cacic.db.TrabajoDao;
import com.cacic.db.UsuarioDao;
import com.cacic.entity.Categoria;
import com.cacic.entity.Revision;
import com.cacic.entity.Rol;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

import junit.framework.TestCase;

public class AppTestCase extends TestCase {
	private UsuarioDao usuarioDao;
	private TrabajoDao trabajoDao;
	private RevisionDao revisionDao;
	private TemaDao temaDao;
	private static final String DB = "MYSQL";

	@Before
	public void setUp() {
		usuarioDao = DAOFactory.getUsuarioDao(DB);
		trabajoDao = DAOFactory.getTrabajoDao(DB);
		revisionDao = DAOFactory.getRevisionDao(DB);
		temaDao = DAOFactory.getTemaDao(DB);
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
			trabajo.setCategoria(Categoria.resumen);
			trabajoDao.altaTrabajo(trabajo);
		}
		for (int i = 0; i < cantidadTrabajos; i++) {
			trabajo = new Trabajo(usuario);
			trabajoDao.altaTrabajo(trabajo);
		}

		List<Trabajo> trabajos = trabajoDao.getTrabajosCategoria(Categoria.resumen.name());
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
		usuario.setRol(Rol.autor);
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
		revisor.setRol(Rol.revisor);
		Usuario otroRevisor = new Usuario();
		otroRevisor.setRol(Rol.revisor);
		
		Usuario autor = new Usuario();
		autor.setRol(Rol.autor);
		Trabajo trabajo = new Trabajo(autor);
		
		//Autor cuyo trabajo no es asignado a el revisor
		Usuario autorNoAsignado = new Usuario();
		autorNoAsignado.setRol(Rol.autor);
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
	 * Punto d iii
	 */
	@Test
	public void testObtenerRevisionesDadoRevisorYRangoFechas() {
		Usuario revisor = new Usuario();
		revisor.setRol(Rol.revisor);
		revisor.setIdUsuario(usuarioDao.altaUsuario(revisor));
		Usuario autor = new Usuario();
		usuarioDao.altaUsuario(autor);
		autor.setRol(Rol.autor);
		Trabajo trabajo = new Trabajo(autor);
		trabajoDao.altaTrabajo(trabajo);
		
		Date fechaEnElRango=new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance(); 
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, -3);
		Date desde=new Date(c.getTimeInMillis());
		
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, 3);
		Date hasta=new Date(c.getTimeInMillis());
		
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, -5);
		Date fechaAbajoDelRango=new Date(c.getTimeInMillis());
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, 5);
		Date fechaArribaDelRango=new Date(c.getTimeInMillis());
		
		List<Integer> idsRevisionesEnElRango= new ArrayList<Integer>();
		Revision review;
		for(int i = 0; i<3;i++) {
			review=new Revision(revisor,trabajo);
			review.setFechaCreacion(fechaEnElRango);
			idsRevisionesEnElRango.add(revisionDao.altaRevision(review));
		}
		review = new Revision(revisor,trabajo);
		review.setFechaCreacion(fechaAbajoDelRango);
		revisionDao.altaRevision(review);
		review = new Revision(revisor,trabajo);
		review.setFechaCreacion(fechaArribaDelRango);
		revisionDao.altaRevision(review);
		
		List<Trabajo> trabajos = revisionDao.getTrabajosByEvaluadorAndDateRange(revisor.getIdUsuario(),desde,hasta);
		assertEquals(idsRevisionesEnElRango.size(),trabajos.size());
	}
	/*
	 * Punto d iv
	 */
	@Test
	public void testObtenerTrabajosEnviadosARevisionByAutor() {
		List<Integer> idsTrabajos= new ArrayList<Integer>();
		Usuario autor = new Usuario();
		autor.setRol(Rol.autor);
		autor.setIdUsuario(usuarioDao.altaUsuario(autor));
		Usuario revisor = new Usuario();
		revisor.setRol(Rol.revisor);
		revisor.setIdUsuario(usuarioDao.altaUsuario(revisor));
		
		Trabajo trabajo;
		Integer id;
		for(int i =0;i<3; i++) {
			trabajo=new Trabajo(autor);
			id = trabajoDao.altaTrabajo(trabajo);
			idsTrabajos.add(id);
			trabajo.setIdTrabajo(id);
			revisionDao.altaRevision(new Revision(revisor,trabajo));
		}
		List<Trabajo> trabajos = revisionDao.getTrabajosByAutor(autor.getIdUsuario());
		assertEquals(idsTrabajos.size(), trabajos.size());
		for(int i =0;i<3; i++) {
			assertEquals(idsTrabajos.get(i),trabajos.get(i).getIdTrabajo());
		}	
	}
	@Test
	public void testObtenerTrabajosEnviadosARevisionByAutorCasoSinEnviar() {
		List<Integer> idsTrabajos= new ArrayList<Integer>();
		Usuario autor = new Usuario();
		autor.setRol(Rol.autor);
		autor.setIdUsuario(usuarioDao.altaUsuario(autor));
		for(int i =0;i<3; i++) {
			idsTrabajos.add(trabajoDao.altaTrabajo(new Trabajo(autor)));
		}
		List<Trabajo> trabajos = revisionDao.getTrabajosByAutor(autor.getIdUsuario());
		assertEquals(0, trabajos.size());
	}
	/*
	 * Punto e
	 */
	@Test
	public void testConsultarTrabajos(){
		//Crea un usuario
		Usuario usuario = new Usuario();
		usuario.setNombre("tester");
		usuario.setApellido("testerson");
		usuario.setCodPostal(7600);
		usuario.setContrasenia("1234");
		usuario.setDomicilio("testDomicilio");
		usuario.setFechaNac(new Date(System.currentTimeMillis()));
		usuario.setLugarTrabajo("UNICEN");
		usuario.setNombreUsuario("testUser");
		usuario.setRol(Rol.autor);
		usuario.setTemas("matematicas,algebra");
		//Obtiene el id despues de darlo de alta
		Integer idU = usuarioDao.altaUsuario(usuario);
		usuario.setIdUsuario(idU);
		//Crea un trabajo
		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(usuario);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("matematicas,algebra");
		trabajo.setRevisiones(null);
		usuario.addTrabajos(trabajo);
		//Obtiene el id despues de darlo de alta
		Integer idT = trabajoDao.altaTrabajo(trabajo);
		trabajo.setIdTrabajo(idT);
		//Obtine el trabajo que esta en la base y lo compara con el creado 
		assertEquals(trabajo, trabajoDao.getTrabajo(idT));
	}
	/*
	 * Punto f
	 */
	public void testTrabajosByAutorRevisorArea() {
		List<Integer> idsTrabajos= new ArrayList<Integer>();
		Usuario autor = new Usuario();
		autor.setRol(Rol.autor);
		autor.setIdUsuario(usuarioDao.altaUsuario(autor));
		Usuario revisor = new Usuario();
		revisor.setRol(Rol.revisor);
		revisor.setIdUsuario(usuarioDao.altaUsuario(revisor));
		
		Trabajo trabajo;
		Integer id;
		for(int i =0;i<3; i++) {
			trabajo=new Trabajo(autor);
			trabajo.setPalabrasClaves("ciencia,fisica,quimica");
			id = trabajoDao.altaTrabajo(trabajo);
			idsTrabajos.add(id);
			trabajo.setIdTrabajo(id);
			revisionDao.altaRevision(new Revision(revisor,trabajo));
		}
		List<Trabajo> trabajos = revisionDao.getTrabajosByAutorRevisorArea(autor.getIdUsuario(), revisor.getIdUsuario(), "fisica");
		assertEquals(idsTrabajos.size(), trabajos.size());
		for(int i =0;i<3; i++) {
			assertEquals(idsTrabajos.get(i),trabajos.get(i).getIdTrabajo());
		}	
	}
	/*
	 * Punto g
	 */
	@After
	public void tearDown() {
		revisionDao.eliminarDatos();
		trabajoDao.eliminarDatos();
		usuarioDao.eliminarDatos();
		temaDao.eliminarDatos();
	}
}
