package com.university.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;
import com.university.model.Respuesta;
import com.university.model.User;
import com.university.model.Zona;
import com.university.service.ZonaService;

import Exception.ServiceException;

@Path("/ZonaService") 
public class ZonaRest {	
	ZonaService zonaService = new ZonaService();  
		@GET 
	    @Path("/zonas/{code}") 
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getZones(@PathParam("code")	String	code){
			List<Zona> areas = null;
			try {
				areas = zonaService.getZones(code);
				if(areas != null)
					return Response.ok(areas).build();
				else
					return Response.status(Response.Status.NOT_FOUND).build();
			} catch (ServiceException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	    @GET 
	    @Path("/zona/{code}") 
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getZone(@PathParam("code")	String	code){
	    	Zona zone = null;
	    	try{
	    		zone = zonaService.getZone(code);
	    		if(zone != null)
	    			return Response.ok(zone).build();
	    		else
	    			return Response.status(Response.Status.NOT_FOUND).build();
	    	}catch (ServiceException e) {
	    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	    	}
	    }
	   
	    @PUT 
	    @Path("/updateZone") 
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response updateZone(Zona zona){
		   
	    	try{
	    		zonaService.updateZone(zona.getId());
	    		return Response.status(Response.Status.NO_CONTENT).build();
				   
	    	}catch(ServiceException e){
	    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();		   
	    	}
	    }
	   
	    @PUT 
	    @Path("/desocuppyZone") 
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response desocuppyZone(Zona zona){
	    	
	    	try{
	    		zonaService.desocuppyZone(zona.getId());
	    		return Response.status(Response.Status.NO_CONTENT).build();   
	    	}catch(ServiceException e){
	    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	    	}
	    }
	   
	    @GET
	    @Path("/userOcuppyZone/{email}") 
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response userOcuppyZone(@PathParam("email")	String	email){
		   
	    	try{
	    		Zona zone = zonaService.userOcuppyZone(email);
	    		if(zone != null){
	    			System.out.println("zona aparcamiento:" + zone.getAparcamiento());
	    			return Response.ok(zone).build();
	    		}else
	    			System.out.println("usuario no ocupa zona");
	    			return Response.status(Response.Status.NOT_FOUND).build();		   
			   
	    	}catch(ServiceException e){	   
	    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	    	}
	    }
}
