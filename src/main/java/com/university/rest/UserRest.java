package com.university.rest;


	import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces; 
	import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.dao.*;
	import com.university.model.*;
	import com.university.service.*;
	@Path("/UserService") 

	public class UserRest {
	   UserService userService = new UserService();  
	   @GET 
	   @Path("/users") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   public Response getUsers(){
		   if(userService.getUsers()!= null)
		   return Response.ok(userService.getUsers()).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();  
	      //return userService.getUsers(); 
		   //return Response.status(Response.Status.NOT_FOUND).build();
	   }  
	   @POST
	   @Path("/user") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response getUser(User usuario){
		   UserDTO user = userService.obtenerDatosUser(usuario.getCorreo(),usuario.getContraseña());
		   if(user!= null)
		   return Response.ok(user).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();  
	      //return userService.getUsers(); 
		   //return Response.status(Response.Status.NOT_FOUND).build();
	   }
	   
	   @POST 
	   @Path("/insert") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response insertUser(User user){
		   user.setFoto("foto");
		   if (userService.insertUser(user)) {
			   System.out.println("true");
			   UserDTO usuario = userService.obtenerDatosUser(user.getCorreo(),user.getContraseña());
			   return Response.status(Response.Status.CREATED).entity(usuario).build();
			   
		   }else
			   System.out.println("false");
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
	   @PUT 
	   @Path("/updateMail/{correo}") 
	   //@Produces(MediaType.APPLICATION_JSON) 
	  // @Consumes(MediaType.APPLICATION_JSON)
	   public Response updateMail(@PathParam("correo")	String	correo){
		   System.out.println(correo);
		   if (userService.updateMailUser(correo)) {
			   return Response.status(Response.Status.NO_CONTENT).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
	   @PUT 
	   @Path("/update") 
	   //@Produces(MediaType.APPLICATION_JSON) 
	   //@Consumes(MediaType.APPLICATION_JSON)
	   public Response updateUser(User user){ 
		    
		   if (userService.updateUser(user)) {
			   return Response.status(Response.Status.NO_CONTENT).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   } 
	   
	   @DELETE
	   @Path("/delete/{correo}") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response deleteUser(@PathParam("correo")	String	correo){ 
		    
		   if (userService.deleteUser(correo)) {
			   return Response.status(Response.Status.NO_CONTENT).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   } 
	}