package com.cacic.controller;

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
public class RevisionController extends Controller{
	
	@GET
	public String getRevision() {
		return "prueba";
	}
	
	@POST
	@Path("/{idRevisor}/{idTrabajo}")
	@Produces("application/json")
	public Response altaRevision ( @PathParam("idRevisor") Integer idRevisor, @PathParam("idTrabajo") Integer idTrabajo) {
		dbManager=new DBManager();
		if(idRevisor == null){
	        return Response.status(400).entity("idRevisor ausente").build();
	    }
		if(idTrabajo == null){
	        return Response.status(400).entity("idTrabajo ausente").build();
	    }
		Usuario revisor = dbManager.getUsuarioDao().getUsuario(idRevisor);
		Trabajo trabajo = dbManager.getTrabajoDao().getTrabajo(idTrabajo);
		
		if(revisor == null){
	        return Response.status(400).entity("No existe revisor").build();
	    }
		if(trabajo == null){
	        return Response.status(400).entity("No existe trabajo").build();
	    }
		
		if (!validarRevision(revisor,trabajo)) {
			return Response.status(400).entity("Revisión inválida").build();
		}
		Revision revision= new Revision(revisor,trabajo);
		revision.setIdRevision(dbManager.getRevisionDao().altaRevision(revision));
		RevisionDTO revisionDTO = new RevisionDTO(revision);
		return Response.ok().entity(revisionDTO).build();
	}

	private boolean validarRevision(Usuario r, Trabajo t) {
		Criterio criterio= new CriterioAnd(new CriterioAnd(new CriterioRevisorNoAutor(r,t), new CriterioRevisorAutorNoMismaZona(r,t)),
				new CriterioOr( new  CriterioRevisorConomientoTemasPoster(r,t), new CriterioRevisorConocimientoTemas(r,t)));
		return criterio.verify();
		
	}
	
	
	
}
