package com.university.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.model.Respuesta;
import com.university.model.Zona;
import com.university.service.ZonaService;

@Path("/ZonaService") 
public class ZonaRest {	
	ZonaService zonaService = new ZonaService();  
	   @GET 
	   @Path("/zonas/{code}") 
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getZones(@PathParam("code")	String	code){
		   List<Zona> zona = null;
		   zona = zonaService.getZones(code);
		   if(zona != null)
		   return Response.ok(zona).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();
	   }
	   @GET 
	   @Path("/zona/{code}") 
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getZone(@PathParam("code")	String	code){
		   Zona zona = null;
		   zona = zonaService.getZone(code);
		   if(zona != null)
		   return Response.ok(zona).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();
	   }
	   
	   @PUT 
	   @Path("/updateZone") 
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response updateZone(Zona zona){
		   System.out.println(zona.getId());
		   if (zonaService.updateZone(zona.getId())) {
			   
			   Respuesta res = new Respuesta();
			   res.setClave("info");
			   res.setValor("el dato se ha actualizado correctamente");
			   return Response.ok(res).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
	   
	   @PUT 
	   @Path("/desocuppyZone") 
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response desocuppyZone(Zona zona){
		   System.out.println(zona.getId());
		   if (zonaService.desocuppyZone(zona.getId())) {
			   Respuesta res = new Respuesta();
			   res.setClave("info");
			   res.setValor("el dato se ha actualizado correctamente");
			   return Response.ok(res).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
}
