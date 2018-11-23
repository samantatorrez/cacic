package com.cacic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.cacic.criterios.Criterio;
import com.cacic.criterios.CriterioAnd;
import com.cacic.criterios.CriterioOr;
import com.cacic.criterios.CriterioRevisorAutorNoMismaZona;
import com.cacic.criterios.CriterioRevisorConocimientoTemas;
import com.cacic.criterios.CriterioRevisorConomientoTemasPoster;
import com.cacic.criterios.CriterioRevisorNoAutor;
import com.cacic.dto.RevisionDTO;
import com.cacic.entity.Revision;
import com.cacic.entity.Trabajo;
import com.cacic.entity.Usuario;

@Path("/revisiones")
public class RevisionController extends Controller {

	@GET
	@Path("/{idRevision}")
	@Produces("application/json")
	public Response getRevision(@PathParam("idRevision") Integer idRevision) {
		try {
			Revision revision = dbManager.getRevisionDao().getRevision(idRevision);
			if (revision == null) {
				return Response.status(400).entity("No existe revision con ese Id").build();
			}
			return Response.ok().entity(new RevisionDTO(revision)).build();
		} catch (Exception e) {
			return Response.status(400).entity("No existe la revision que se desea obtener").build();
		}
	}

	@GET
	@Produces("application/json")
	public Response getRevisiones() {
		try {
			List<Revision> revisiones = dbManager.getRevisionDao().getRevisiones();

			List<RevisionDTO> revisionesDTO = new ArrayList<RevisionDTO>();
			for (Revision revision : revisiones) {
				revisionesDTO.add(new RevisionDTO(revision));
			}
			return Response.ok().entity(revisionesDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("No existen las revisiones que se desean obtener").build();
		}
	}

	@POST
	@Path("/{idRevisor}/{idTrabajo}")
	@Produces("application/json")
	public Response altaRevision(@PathParam("idRevisor") Integer idRevisor, @PathParam("idTrabajo") Integer idTrabajo) {
		try {
			if (idRevisor == null) {
				return Response.status(400).entity("idRevisor ausente").build();
			}
			if (idTrabajo == null) {
				return Response.status(400).entity("idTrabajo ausente").build();
			}
			Usuario revisor = dbManager.getUsuarioDao().getUsuario(idRevisor);
			Trabajo trabajo = dbManager.getTrabajoDao().getTrabajo(idTrabajo);

			if (revisor == null) {
				return Response.status(400).entity("No existe revisor").build();
			}
			if (trabajo == null) {
				return Response.status(400).entity("No existe trabajo").build();
			}

			if (!validarRevision(revisor, trabajo)) {
				return Response.status(400).entity("Revisión inválida").build();
			}
			Revision revision = new Revision(revisor, trabajo);
			revision.setIdRevision(dbManager.getRevisionDao().altaRevision(revision));
			RevisionDTO revisionDTO = new RevisionDTO(revision);
			return Response.ok().entity(revisionDTO).build();
		} catch (Exception e) {
			return Response.status(400).entity("No se pudo realizar el alta de la revision").build();
		}
	}

	@DELETE
	@Path("/{idRevision}")
	@Produces("application/json")
	public Response bajaRevision(@PathParam("idRevision") Integer idRevision) {
		try {
			Revision revision = dbManager.getRevisionDao().getRevision(idRevision);
			if (revision == null) {
				return Response.status(400).entity("No existe revision con ese Id").build();
			}
			dbManager.getRevisionDao().bajaRevision(idRevision);
			;
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(400).entity("No se pudo realizar la baja de la revision").build();
		}
	}

	private boolean validarRevision(Usuario r, Trabajo t) {
		Criterio criterio = new CriterioAnd(
				new CriterioAnd(new CriterioRevisorNoAutor(r, t), new CriterioRevisorAutorNoMismaZona(r, t)),
				new CriterioOr(new CriterioRevisorConomientoTemasPoster(r, t),
						new CriterioRevisorConocimientoTemas(r, t)));
		return criterio.verify();

	}
}
