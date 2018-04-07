package com.university.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.model.Announcement;
import com.university.service.AnnouncementService;

import Exception.ServiceException;

@Path("/AnuncioService") 
public class AnnouncementRest {
	
	AnnouncementService announcementService = new AnnouncementService();  
	
    @GET 
    @Path("/anuncio/{email}/{idRoute}") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getZone(@PathParam("email")	String	email, @PathParam("idRoute") int idRoute){
    	Announcement anunce = null;
    	try{
    		anunce = announcementService.getAnounce(idRoute, email);
    		if(anunce != null)
    			return Response.ok(anunce).build();
    		else
    			return Response.status(Response.Status.NOT_FOUND).build();
    	}catch (ServiceException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	}
    }

}
