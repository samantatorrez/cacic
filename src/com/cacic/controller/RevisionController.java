package com.cacic.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.persistence.oxm.MediaType;


@Path("/revisiones")
public class RevisionController {
	
	@GET
	public String getRevision() {
		return "prueba";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void altaRevision () {
		
	}
	
	
}
