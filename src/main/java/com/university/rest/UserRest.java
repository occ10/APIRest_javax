package com.university.rest;


	
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
import com.university.model.*;
import com.university.service.*;
	@Path("/UserService") 

	public class UserRest {
	   UserService userService = new UserService();  
	   @GET 
	   @Path("/users") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   public Response getUsers(){

		   List<User> usuarios = null;
		   usuarios = userService.getUsers();
		   if(usuarios!= null)
		   return Response.ok(usuarios).build();
		   else{
			   return Response.status(Response.Status.NO_CONTENT).build();  
		   }
	   }  
	   @POST
	   @Path("/user") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response getUser(User usuario){
		   Respuesta res = new Respuesta();
		   UserDTO user = userService.obtenerDatosUser(usuario.getCorreo(),usuario.getContraseña());
		   if(user!= null)
		   return Response.ok(user).build();
		   else{
			   res.setClave("info");
			   res.setValor("los dats no son correctos");
			   return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		   }

	   }
	   
	   @GET
	   @Path("/userByMail/{correo}") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   public Response getUserByMail(@PathParam("correo")	String	correo){
		   //System.out.println("response correct");
		   UserDTO user = userService.obtenerDatosUserByMail(correo);
		   if(user!= null){
			  System.out.println("response correct");
		   return Response.ok(user).build();
		   }
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();  
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
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
	   @PUT 
	   @Path("/updateMail/{correo}") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
	   public Response updateMail(@PathParam("correo")	String	correo){
		   System.out.println(correo);
		   if (userService.updateMailUser(correo)) {
			   return Response.status(Response.Status.NO_CONTENT).build();
			   
		   }else
			   return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	   }
	   @PUT 
	   @Path("/update") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   @Consumes(MediaType.APPLICATION_JSON)
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