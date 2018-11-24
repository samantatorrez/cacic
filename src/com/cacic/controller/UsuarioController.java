package com.cacic.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.cacic.dto.TrabajoDTO;
import com.cacic.dto.UsuarioDTO;
import com.cacic.entity.Tema;
import com.cacic.entity.TipoPalabra;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

@Path("/usuarios")
public class UsuarioController extends Controller {

	@GET
	@Path("/{idUsuario}")
	@Produces("application/json")
	public Response getUsuario(@PathParam("idUsuario") Integer idUsuario) {
		Usuario usuario = dbManager.getUsuarioDao().getUsuario(idUsuario);
		if (usuario == null) {
			return Response.status(400).entity("No existe usuario con ese Id").build();
		}
		return Response.ok().entity(new UsuarioDTO(usuario)).build();
	}

	@GET
	@Produces("application/json")
	public Response getUsuarios() {
		List<Usuario> usuarios = dbManager.getUsuarioDao().getUsuarios();

		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();
		for (Usuario usuario : usuarios) {
			usuariosDTO.add(new UsuarioDTO(usuario));
		}
		return Response.ok().entity(usuariosDTO).build();
	}

	@GET
	@Path("/temasespecificos/{idUsuario}")
	@Produces("application/json")
	public Response esEspecifico(@PathParam("idUsuario") Integer idUsuario) {
		Usuario usuario = dbManager.getUsuarioDao().getUsuario(idUsuario);
		if (usuario == null) {
			return Response.status(400).entity("No existe usuario con ese Id").build();
		} else {
			List<String> palabrasClaves = new ArrayList<String>(Arrays.asList(usuario.getTemas().split(" , ")));
			for (String palabra : palabrasClaves) {
				Tema pal = dbManager.getTemaDao().getTema(palabra);
				if (pal.getTipo() == TipoPalabra.especifica) {
					return Response.ok().entity(new UsuarioDTO(usuario)).build();
				}
			}
		}
		return Response.status(400).entity("El usuario no es especialista en los temas.").build();
	}

	@POST
	@Path("/{idUsuario}")
	@Produces("application/json")
	public Response altaUsuario(@PathParam("idUsuario") Integer idUsuario) {
		dbManager = new DBManager();
		if (idUsuario == null) {
			return Response.status(400).entity("idUsuario no valido").build();
		}
		Usuario usuario = dbManager.getUsuarioDao().getUsuario(idUsuario);
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
		return Response.ok().entity(usuarioDTO).build();
	}
	
	@PUT
	@Path("/{idUsuario}")
	@Produces("application/json")
	public Response actualizarUsuario(@PathParam("idUsuario") Integer idUsuario, Usuario user) {
		Usuario resultado = dbManager.getUsuarioDao().getUsuario(idUsuario);
		if (resultado == null) {
			return Response.status(400).entity("No se encontro el usuario pasado.").build();
		}
		try {
			dbManager.getUsuarioDao().actualizaUsuario(idUsuario, user);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al actualizar el usuario en la base").build();
		}
	}

	@DELETE
	@Path("/{idUsuario}")
	@Produces("application/json")
	public Response bajaUsuario(@PathParam("idUsuario") Integer idUsuario) {
		Usuario usuario = dbManager.getUsuarioDao().getUsuario(idUsuario);
		if (usuario == null) {
			return Response.status(400).entity("No existe ese usuario").build();
		}
		dbManager.getUsuarioDao().bajaUsuario(idUsuario);
		return Response.ok().build();
	}

}