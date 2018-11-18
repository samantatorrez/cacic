package com.cacicTest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.cacic.controller.DBManager;
import com.cacic.entity.Revision;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;


public class ApiTestCase {
	private DBManager dbManager=new DBManager();
	public final HttpClient client = HttpClientBuilder.create().build();
	public final String BASE_URL="http://localhost:8080/cacic/api";
	public final String REVISIONES = "/revisiones";
	
	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
		Usuario autor = new Usuario();
		Usuario revisor = new Usuario();
		/*revisor.set
		Trabajo trabajo = new Trabajo();
		Integer idAutor = dbManager.getUsuarioDao().altaUsuario(autor);
		Integer idRevisor =dbManager.getUsuarioDao().altaUsuario(revisor);
		Integer idTrabajo = dbManager.getTrabajoDao().altaTrabajo(trabajo);
		String url = BASE_URL + REVISIONES +"/"+idRevisor+"/"+idTrabajo;
		*/
	}
	public void crearRevision() {
		
	}
	
}
