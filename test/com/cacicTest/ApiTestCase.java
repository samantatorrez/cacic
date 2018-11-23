package com.cacicTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Test;

import com.cacic.controller.DBManager;
import com.cacic.dto.RevisionDTO;
import com.cacic.dto.TrabajoDTO;
import com.cacic.dto.UsuarioDTO;
import com.cacic.entity.Categoria;
import com.cacic.entity.Revision;
import com.cacic.entity.Rol;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;


public class ApiTestCase {
	private DBManager dbManager=new DBManager();
	public final HttpClient client = HttpClientBuilder.create().build();
	public final String BASE_URL="http://localhost:8080/cacic/api";
	public final String REVISIONES = "/revisiones";
	public final String TRABAJOS = "/trabajos";
	public final String CATEGORIA ="/categoria";
	public final String EVALUADOR ="/evaluador";
	public final String RANGO = "/rango";
	public final String AUTOR = "/autor";
	public final String REVISOR = "/revisor";
	public final String AREA = "/area";
	public final String USUARIOS = "/usuarios";
	
	@Test
	public void testRESTRevision()  {
		try {
			/*Tests Revision Api*/
			crearRevision();
			obtenerRevision();
			eliminarRevision();
			obtenerRevisiones();
		} catch (Exception e) {
			fail("Exception thrown" + e.getMessage());
		}
	}
	
	@Test
	public void testRESTTrabajo()  {
		try {
			/*Tests Trabajo Api*/
			getTrabajo();
			crearTrabajo();
			getTrabajos();
			eliminarTrabajo();
			getTrabajosCategoria();
			obtenerTrabajosPorEvaluador();
			obtenerTrabajosByEvaluadorAndDateRange();
			obteneTrabajosByAutor();
			obteneTrabajosByAutorRevisorArea();
		} catch (Exception e) {
			fail("Exception thrown" + e.getMessage());
		}
	}
	
	@Test
	public void testRESTUsuario()  {
		try {
			/*Tests Usuario Api*/
			crearUsuario();
//			getUsuario();
//			getUsuarios();
//			esEspecifico();
//			bajaUsuario();
		} catch (Exception e) {
			fail("Exception thrown" + e.getMessage());
		}
	}
	
	/*Tests Revision Api*/
	
	/*
	 * Punto iv: Asignar un trabajo a un revisor, chequeando las restricciones.
	 * 
	 */
	public void crearRevision() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		String url = BASE_URL + REVISIONES +"/"+idRevisor+"/"+idTrabajo;
		
		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);
		
		assertEquals(response.getStatusLine().getStatusCode(),200);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		RevisionDTO result = objectMapper.readValue(json_string, RevisionDTO.class);
		
		assertEquals(result.getIdRevisor(),idRevisor);
		assertEquals(result.getIdTrabajo(),idTrabajo);	
	}
	
	public void obtenerRevision() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor =dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		String url = BASE_URL + REVISIONES +"/"+idRevisor+"/"+idTrabajo;
		
		/*CREA UNA REVISION*/
		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);
		
		assertEquals(response.getStatusLine().getStatusCode(),200);
		RevisionDTO result = getRevisionDTO(response);
		
		/*HACE UN GET DE LA REVISION Y COMPRUEBA LOS DATOS*/
		url = BASE_URL + REVISIONES +"/" + result.getIdRevision();
		HttpGet get = new HttpGet(url);
		response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		result = getRevisionDTO(response);
		
		assertEquals(result.getIdRevisor(),idRevisor);
		assertEquals(result.getIdTrabajo(),idTrabajo);	
	}
	
	public void eliminarRevision() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		String url = BASE_URL + REVISIONES +"/"+idRevisor+"/"+idTrabajo;
		
		/*CREA UNA REVISION*/
		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);
		
		assertEquals(response.getStatusLine().getStatusCode(),200);
		RevisionDTO result = getRevisionDTO(response);
		
		/*BORRA LA REVISION*/
		url = BASE_URL + REVISIONES +"/" + result.getIdRevision();
		HttpDelete delete = new HttpDelete(url);
		response = client.execute(delete);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		
		/*HACE UN GET Y COMPRUEBA QUE NO EXISTE LA REVISION*/
		url = BASE_URL + REVISIONES +"/" + result.getIdRevision();
		HttpGet get = new HttpGet(url);
		response = client.execute(get);
		String result_get = EntityUtils.toString(response.getEntity());
		assertEquals("No existe revision con ese Id", result_get);
		
	}
	
	public void obtenerRevisiones() throws ClientProtocolException, IOException {
		String url = BASE_URL + REVISIONES +"/";
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getRevisionsDTO(response).length==2);
	}
	
	private RevisionDTO getRevisionDTO (HttpResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		return objectMapper.readValue(json_string, RevisionDTO.class);
	}
	private RevisionDTO[] getRevisionsDTO (HttpResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		return objectMapper.readValue(json_string, RevisionDTO[].class);
	}
	private TrabajoDTO getTrabajoDTO (HttpResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		return objectMapper.readValue(json_string, TrabajoDTO.class);
	}
	private TrabajoDTO[] getTrabajosDTO (HttpResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		return objectMapper.readValue(json_string, TrabajoDTO[].class);
	}
	
	/*Tests Trabajo controller*/
	
	/*
	 * Punto ii: Crear trabajos de investigación
	 */
	public void getTrabajo() throws JsonParseException, JsonMappingException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		autor.setIdUsuario(idAutor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		TrabajoDTO trabajoDTO = new TrabajoDTO(trabajo);
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		trabajoDTO.setIdTrabajo(idTrabajo);
		
		String url = BASE_URL + TRABAJOS +"/" + idTrabajo;
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		TrabajoDTO result = getTrabajoDTO(response);
		assertEquals(result,trabajoDTO);
	}
	
	public void crearTrabajo() throws UnsupportedCharsetException, ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		autor.setIdUsuario(idAutor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		TrabajoDTO trabajoDTO = new TrabajoDTO(trabajo);
		
		String url = BASE_URL + TRABAJOS +"/";
		
		HttpPost post = new HttpPost(url);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("idAutor", trabajoDTO.getIdAutor());
		jsonObject.put("categoria", trabajoDTO.getCategoria());
		jsonObject.put("palabrasClaves", trabajoDTO.getPalabrasClaves());
		String jsonString = jsonObject.toString();
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);
		
		assertEquals(response.getStatusLine().getStatusCode(),200);
		
		TrabajoDTO result = getTrabajoDTO(response);
		
		assertEquals(result.getIdAutor(),trabajoDTO.getIdAutor());
		assertEquals(result.getCategoria(),trabajoDTO.getCategoria());
		assertEquals(result.getPalabrasClaves(),trabajoDTO.getPalabrasClaves());
	}
	
	public void getTrabajos() throws ClientProtocolException, IOException {
		String url = BASE_URL + TRABAJOS +"/";
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length>=2);
	}
	
	public void eliminarTrabajo() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		autor.setIdUsuario(idAutor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		
		/*BORRA EL TRABAJO*/
		String url = BASE_URL + TRABAJOS +"/" + idTrabajo;
		HttpDelete delete = new HttpDelete(url);
		HttpResponse response = client.execute(delete);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		
		/*HACE UN GET Y COMPRUEBA QUE NO EXISTE EL TRABAJO*/
		url = BASE_URL + TRABAJOS +"/" + idTrabajo;
		HttpGet get = new HttpGet(url);
		response = client.execute(get);
		String result_get = EntityUtils.toString(response.getEntity());
		assertEquals("No existe el trabajo que se desea obtener", result_get);
	}

	public void getTrabajosCategoria() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		autor.setIdUsuario(idAutor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.resumen);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		Trabajo trabajo2 = new Trabajo();
		trabajo2.setAutor(autor);
		trabajo2.setCategoria(Categoria.resumen);
		trabajo2.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo2 = dbManager.getTrabajoDao().altaTrabajo(trabajo2);
		
		String url = BASE_URL + TRABAJOS +CATEGORIA+"/"+Categoria.resumen;
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length==2);
	}

	public void obtenerTrabajosPorEvaluador() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		trabajo.setIdTrabajo(idTrabajo);
		Trabajo trabajo2 = new Trabajo();
		trabajo2.setAutor(autor);
		trabajo2.setCategoria(Categoria.resumen);
		trabajo2.setPalabrasClaves("ciencia");
		
		Integer idTrabajo2 = dbManager.getTrabajoDao().altaTrabajo(trabajo2);
		trabajo2.setIdTrabajo(idTrabajo2);
		
		Revision review=new Revision(revisor,trabajo);
		Integer idRevision = dbManager.getRevisionDao().altaRevision(review);
		Revision review2=new Revision(revisor,trabajo2);
		Integer idRevision2 = dbManager.getRevisionDao().altaRevision(review2);
		
		String url = BASE_URL + TRABAJOS + EVALUADOR + "/"+idRevisor;
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length==2);
	}

	/*
	 * Punto vi: Retornar todas los trabajos revisados por un revisor determinado dado un rango de fechas.
	 */
	public void obtenerTrabajosByEvaluadorAndDateRange() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		
		Date fechaEnElRango=new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance(); 
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, -10);
		Date desde=new Date(c.getTimeInMillis());
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, -8);
		Date hasta=new Date(c.getTimeInMillis());
		c.setTime(fechaEnElRango); 
		c.add(Calendar.DATE, -9);
		Date medio=new Date(c.getTimeInMillis());
		
		
		Revision review=new Revision(revisor,trabajo);
		review.setFechaCreacion(medio);
		Integer idRevision = dbManager.getRevisionDao().altaRevision(review);
		
		String url = BASE_URL + TRABAJOS +EVALUADOR+"/"+idRevisor+RANGO+"?desde="+desde.getTime()+"&hasta="+hasta.getTime();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length==1);
	}

	/*
	 * Punto v: Retornar todas los trabajos enviados por un autor determinado
	 */
	public void obteneTrabajosByAutor() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		autor.setIdUsuario(idAutor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		revisor.setIdUsuario(idAutor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.resumen);
		trabajo.setPalabrasClaves("ciencia");
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);

		Trabajo trabajo2 = new Trabajo();
		trabajo2.setAutor(autor);
		trabajo2.setCategoria(Categoria.resumen);
		trabajo2.setPalabrasClaves("ciencia");
		Integer idTrabajo2 = dbManager.getTrabajoDao().altaTrabajo(trabajo2);
		
		Revision review=new Revision(revisor,trabajo);
		Integer idRevision = dbManager.getRevisionDao().altaRevision(review);
		Revision review2=new Revision(revisor,trabajo2);
		Integer idRevision2 = dbManager.getRevisionDao().altaRevision(review2);
		
		String url = BASE_URL + TRABAJOS +AUTOR+"/"+idAutor;
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length==2);
	}

	public void obteneTrabajosByAutorRevisorArea() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		autor.setNombre("autor");
		autor.setCodPostal(7000);
		autor.setLugarTrabajo("tandil sa");
		
		Usuario revisor = new Usuario();
		revisor.setNombre("revisor");
		revisor.setCodPostal(7600);
		revisor.setLugarTrabajo("mdq SA");
		revisor.setTemas("ciencia,tic");
		
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor = dbManager.getUsuarioDao().altaUsuario(revisor);
		autor.setIdUsuario(idAutor);
		revisor.setIdUsuario(idRevisor);

		Trabajo trabajo = new Trabajo();
		trabajo.setAutor(autor);
		trabajo.setCategoria(Categoria.articulo);
		trabajo.setPalabrasClaves("ciencia");
		
		
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		Trabajo trabajo2 = new Trabajo();
		trabajo2.setAutor(autor);
		trabajo2.setCategoria(Categoria.resumen);
		trabajo2.setPalabrasClaves("ciencia");
		Integer idTrabajo2 = dbManager.getTrabajoDao().altaTrabajo(trabajo2);
		
		Revision review=new Revision(revisor,trabajo);
		Integer idRevision = dbManager.getRevisionDao().altaRevision(review);
		Revision review2=new Revision(revisor,trabajo2);
		Integer idRevision2 = dbManager.getRevisionDao().altaRevision(review2);
		
		String area= "ciencia";
		String url = BASE_URL + TRABAJOS + AUTOR +"/"+idAutor+REVISOR+"/"+idRevisor+AREA+"/"+area;
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		assertEquals(response.getStatusLine().getStatusCode(),200);
		assertTrue(getTrabajosDTO(response).length==2);
	}
	
	/*Tests Usuario Api*/
	
	/*
	 * Punto i: Crear usuarios.
	 * 
	 */
	public void crearUsuario() throws ClientProtocolException, IOException {
		Usuario user = new Usuario();
		user.setNombre("Juan");
		user.setApellido("Perez");
		user.setRol(Rol.autor);
		user.setLugarTrabajo("PLADEMA");
		user.setNombreUsuario("jPerez");
		user.setContrasenia("123456");
		user.setTemas("programacion, bases de datos");
		user.setFechaNac(new Date(System.currentTimeMillis()));
		user.setDomicilio("calle 123");
		user.setCodPostal(7000);
		
		Integer idUser = dbManager.getUsuarioDao().altaUsuario(user);
		
		String url = BASE_URL + USUARIOS +"/" + idUser;
		
		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);
		
		assertEquals(response.getStatusLine().getStatusCode(),200);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json_string = EntityUtils.toString(response.getEntity());
		UsuarioDTO result = objectMapper.readValue(json_string, UsuarioDTO.class);
		
		assertEquals(result.getIdUsuario(),idUser);
	}
	
	
	
	@After
	public void tearDown() {
		dbManager.getRevisionDao().eliminarDatos();
		dbManager.getTrabajoDao().eliminarDatos();
		dbManager.getUsuarioDao().eliminarDatos();
	}
}
