package com.cacic.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/revisiones")
public class RevisionController {
	
	@GET
	public String getRevision() {
		return "prueba";
	}
	
	
}
