package com.cacic.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.cacic.dto.UsuarioDTO;
import com.cacic.entity.Tema;
import com.cacic.entity.TipoPalabra;
import com.cacic.entity.Usuario;

@Path("/usuarios")
public class UsuarioController extends Controller{
	
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
	@Path("/{idUsuario}")
	@Produces("application/json")
	public Response esEspecifico(@PathParam("idUsuario") Integer idUsuario) {
		Usuario usuario = dbManager.getUsuarioDao().getUsuario(idUsuario);
		if (usuario == null) {
			return Response.status(400).entity("No existe usuario con ese Id").build();
		}
		else {
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

}
