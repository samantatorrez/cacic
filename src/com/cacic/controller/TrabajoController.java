package com.cacic.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.cacic.dto.TrabajoDTO;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

@Path("/trabajos")
public class TrabajoController extends Controller {

	@GET
	@Path("/{idTrabajo}")
	@Produces("application/json")
	public Response getTrabajo(@PathParam("idTrabajo") Integer idTrabajo) {
		Trabajo trabajo = dbManager.getTrabajoDao().getTrabajo(idTrabajo);
		if (trabajo == null) {
			return Response.status(400).entity("No existe el trabajo que se desea obtener").build();
		}
		return Response.ok().entity(new TrabajoDTO(trabajo)).build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response crearTrabajo(TrabajoDTO trabajo) {
		Usuario autor = dbManager.getUsuarioDao().getUsuario(trabajo.getIdAutor());
		if (autor == null) {
			return Response.status(400).entity("Autor invalido").build();
		}
		try {
			Integer id = dbManager.getTrabajoDao().altaTrabajo(new Trabajo(trabajo, autor));
			trabajo.setIdTrabajo(id);
			return Response.ok().entity(trabajo).build();
		} catch (Exception e) {
			return Response.status(400).entity("Se produjo un error al intentar dar de alta").build();
		}
	}

	@GET
	@Produces("application/json")
	public Response getTrabajos() {
		try {
			List<TrabajoDTO> resultado = new ArrayList<TrabajoDTO>();
			for (Trabajo trabajo : dbManager.getTrabajoDao().getTrabajos()) {
				resultado.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(resultado).build();
		} catch (Exception e) {
			return Response.status(400).entity("Se produjo un error al obtener los trabajos").build();
		}
	}

	@DELETE
	@Path("/{idTrabajo}")
	@Produces("application/json")
	public Response eliminarTrabajo(@PathParam("idTrabajo")Integer idTrabajo) {
		Trabajo trabajo = dbManager.getTrabajoDao().getTrabajo(idTrabajo);
		if (trabajo == null) {
			return Response.status(400).entity("No existe el trabajo que se desea eliminar").build();
		}
		try {
			dbManager.getTrabajoDao().bajaTrabajo(idTrabajo);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al eliminar el trabajo de la base").build();
		}
	}

	@GET
	@Path("/categoria/{categoria}")
	@Produces("application/json")
	public Response getTrabajosCategoria(@PathParam("categoria") String categoria) {
		List<TrabajoDTO> resultado = new ArrayList<TrabajoDTO>();
		try {
			for (Trabajo trabajo : dbManager.getTrabajoDao().getTrabajosCategoria(categoria)) {
				resultado.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(resultado).build();
		} catch (Exception e) {
			return Response.status(400).entity("Se produjo un error al obtener los trabajos por categoria").build();
		}
	}

	@GET
	@Path("/evaluador/{idEvaluador}")
	@Produces("application/json")
	public Response obtenerTrabajosPorEvaluador(@PathParam("idEvaluador") Integer idEvaluador) {
		try {
			List<Trabajo> trabajos = dbManager.getRevisionDao().getTrabajosByEvaluador(idEvaluador);

			List<TrabajoDTO> trabajosDTO = new ArrayList<TrabajoDTO>();
			for (Trabajo trabajo : trabajos) {
				trabajosDTO.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(trabajosDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al intentar obtener los trabajos").build();
		}
	}

	@GET
	@Path("/evaluador/{idEvaluador}/rango")
	@Produces("application/json")
	public Response obtenerTrabajosByEvaluadorAndDateRange(@PathParam("idEvaluador") Integer idEvaluador,
			@QueryParam("desde") Long desde, @QueryParam("hasta") Long hasta) {
		try {
			Date desdeDate = new Date(desde);
			Date hastaDate = new Date(hasta);
			List<Trabajo> trabajos = dbManager.getRevisionDao().getTrabajosByEvaluadorAndDateRange(idEvaluador, desdeDate,
					hastaDate);

			List<TrabajoDTO> trabajosDTO = new ArrayList<TrabajoDTO>();
			for (Trabajo trabajo : trabajos) {
				trabajosDTO.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(trabajosDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al intentar obtener los trabajos").build();
		}
	}

	@GET
	@Path("/autor/{idAutor}")
	@Produces("application/json")
	public Response obteneTrabajosByAutor(@PathParam("idAutor") Integer idAutor) {
		try {
			List<Trabajo> trabajos = dbManager.getRevisionDao().getTrabajosByAutor(idAutor);

			List<TrabajoDTO> trabajosDTO = new ArrayList<TrabajoDTO>();
			for (Trabajo trabajo : trabajos) {
				trabajosDTO.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(trabajosDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al intentar obtener los trabajos").build();
		}
	}

	@GET
	@Path("/autor/{autorId}/revisor/{revisorId}/area/{area}")
	@Produces("application/json")
	public Response obteneTrabajosByAutorRevisorArea(@PathParam("autorId") Integer autorId,
			@PathParam("revisorId") Integer revisorId,@PathParam("area") String area) {
		try {
			List<Trabajo> trabajos = dbManager.getRevisionDao().getTrabajosByAutorRevisorArea(autorId, revisorId, area);

			List<TrabajoDTO> trabajosDTO = new ArrayList<TrabajoDTO>();
			for (Trabajo trabajo : trabajos) {
				trabajosDTO.add(new TrabajoDTO(trabajo));
			}
			return Response.ok().entity(trabajosDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("Error al intentar obtener los trabajos").build();
		}
	}
}
