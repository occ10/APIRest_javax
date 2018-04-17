package com.university.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.university.model.*;
import com.university.service.*;

import Exception.ServiceException;

@Path("/UserService")

public class UserRest {
	@Context
	UriInfo uriInfo;
	UserService userService = new UserService();

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {

		List<User> usuarios = null;
		try {
			usuarios = userService.getUsers();
			if (usuarios != null)
				return Response.ok(usuarios).build();
			else {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(User usuario) {
		Respond res = new Respond();
		UserDTO user;
		try {
			user = userService.obtenerDatosUser(usuario.getCorreo(), usuario.getContraseña());
			if (user != null){
				return Response.ok(user).build();
			}else {
				res.setClave("info");
				res.setValor("los dats no son correctos");
				System.out.println("usuario no autorizado");
				return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
			}
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/userByMail/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByMail(@PathParam("correo") String correo) {
		// System.out.println("response correct");
		UserDTO user;
		try {
			user = userService.obtenerDatosUserByMail(correo);
			if (user != null) {
				return Response.ok(user).build();
			} else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUser(User user) {
		user.setFoto("foto");
		try {
			UserDTO usuario = userService.insertUser(user);
			userService.sendMail(usuario.getCorreo());
			// UserDTO usuario =
			// userService.obtenerDatosUser(user.getCorreo(),user.getContraseña());
			// URI uri =
			// uriInfo.getAbsolutePathBuilder().path("{dni}").build(dni);
			// Response.created(uri).build();
			return Response.status(Response.Status.CREATED).entity(usuario).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GET
	@Path("/updateMail/{correo}")
	@Produces(MediaType.TEXT_HTML)
	public Response updateMail(@PathParam("correo") String correo) {
		String content = "<html> " + "<title>" + "Hello Jersey" + "</title>"
		        + "<body><h1>" + "La confirmacion se ha hecho correctamente" + "</h1></body>" + "</html> ";
		try {
			userService.updateMailUser(correo);
			return Response.ok(content).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {

		try {
			userService.updateUser(user);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/saveFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(		
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		System.out.print("");
		String UPLOAD_FOLDER = "c:/uploadedFiles/";
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			userService.createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Can not create destination folder on server")
					.build();
		}
		String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
		try {
			userService.saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not save file").build();
		}
		return Response.ok("File saved to " + uploadedFileLocation).build();
	}


	@DELETE
	@Path("/delete/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("correo") String correo) {

		try {
			userService.deleteUser(correo);
			return Response.status(Response.Status.NO_CONTENT).build();

		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}