package com.university.rest;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.model.Zona;
import com.university.service.ZonaService;

@Path("/ZonaService") 
public class ZonaRest {	
	ZonaService zonaService = new ZonaService();  
	   @GET 
	   @Path("/zonas/{code}") 
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getZone(@PathParam("code")	String	code){
		   List<Zona> zona = null;
		   zona = zonaService.getZones(code);
		   if(zona != null)
		   return Response.ok(zona).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();
	   }
}
