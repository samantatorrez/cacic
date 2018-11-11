package com.cacic.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.Response;
//
//import com.cacic.db.MysqlDao;
//import com.cacic.db.UsuarioDao;
//import com.cacic.db.Impl.UsuarioMysqlDAOImpl;
//import com.cacic.entity.Usuario;

@Path("/usuarios")
public class UsuarioController {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHello() {
		String resource = "<h1> Hola esto es una prueba desde el controller. </h1>";
		return resource;
	}
	
//	/**
//	 * @return
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Usuario> getAllUsuarios() {
//		return UsuarioMysqlDAOImpl;
//	}
//	
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Usuario getUsuarioById(@PathParam("id") String msg) {
//		int id = Integer.valueOf(msg);
//		Usuario usuario = UsuarioDAO.getInstance().findById(id);
//		if(usuario!=null)
//			return usuario;
//		else
//			throw new RecursoNoExiste(id);
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response createUsuario(Usuario usuario) {
//		Usuario result = UsuarioDAO.getInstance().persist(usuario);
//		if(result==null) {
//			throw new RecursoDuplicado(usuario.getDni());
//		}
//		else {
//			return Response.status(201).entity(usuario).build();
//		}
//	}
/*	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id") int id) {
		throw new UnsupportedOperationException();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") int id,Usuario usuario) {
		throw new UnsupportedOperationException();
	}
	
	@GET
	@Path("/findUsuariosByEdad")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> findUsuariosByEdad(@QueryParam("from") int from,
			@QueryParam("to") int to) {
		throw new UnsupportedOperationException();
	}
	
	public class RecursoDuplicado extends WebApplicationException {
		public RecursoDuplicado(int id) {
			super(Response.status(Response.Status.CONFLICT)
					.entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	public class RecursoNoExiste extends WebApplicationException {
		public RecursoNoExiste(int id) {
			super(Response.status(Response.Status.NOT_FOUND)
					.entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
			}
	}*/

}
