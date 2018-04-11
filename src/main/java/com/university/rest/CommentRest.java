package com.university.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.university.model.Comment;
import com.university.service.CommentService;

import Exception.ServiceException;

@Path("/CommentService") 
public class CommentRest {
	
	CommentService commentService = new CommentService();  
	
    @GET 
    @Path("/comments/{email}") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsUser(@PathParam("email")	String	email){
    	List<Comment> comment = null;
    	try{
    		comment = commentService.getCometnsUerComment(email);
    		if(comment != null)
    			return Response.ok(comment).build();
    		else
    			return Response.status(Response.Status.NOT_FOUND).build();
    	}catch (ServiceException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
    @GET 
    @Path("/comented/{email}") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsUserCommented(@PathParam("email")	String	email){
    	List<Comment> comment = null;
    	try{
    		comment = commentService.getCometnsUerCommented(email);
    		if(comment != null)
    			return Response.ok(comment).build();
    		else
    			return Response.status(Response.Status.NOT_FOUND).build();
    	}catch (ServiceException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	}
    }

}
