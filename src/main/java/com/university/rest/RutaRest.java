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

import com.university.service.RutaService;
import com.university.service.UserService;
import com.university.model.*;
@Path("/RutaService") 
public class RutaRest {

	   RutaService rutaService = new RutaService();  
	   @GET 
	   @Path("/routes/{correo}") 
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getRoutes(@PathParam("correo")	String	correo){
	   //public Response getRoutes(@PathParam("correo")	String	correo){
		   List<Ruta> rutas = null;
		   rutas = rutaService.getRutas(correo);
		   if(rutas != null)
		   return Response.ok(rutas).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();  
	      //return userService.getUsers(); 
		   //return Response.status(Response.Status.NOT_FOUND).build();
	   }  
}
