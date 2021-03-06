package com.university.rest;

import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.ws.rs.core.Response.ResponseBuilder;
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
	
	@GET
	@Path("/userByName/{email}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByName(@PathParam("email") String email, @PathParam("name") String name) {
		// System.out.println("response correct");
		List<UserDTO> users;
		try {
			users = userService.obtainUserByName(name, email);
			if (users != null) {
				return Response.ok(users).build();
			} else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/userByOrigin/{email}/{origin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByOrigin(@PathParam("email") String email, @PathParam("origin") String origin) {
		// System.out.println("response correct");
		List<UserDTO> users;
		try {
			users = userService.obtainUserByOrigin(origin, email);
			if (users != null) {
				return Response.ok(users).build();
			} else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/userByPhone/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByPhone(@PathParam("phone") String phone) {
		// System.out.println("response correct");
		UserDTO user;
		try {
			user = userService.obtainUserByPhone(phone);
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
	
	@PUT
	@Path("/saveFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(		
			@FormDataParam("uploaded_file") InputStream uploadedInputStream,
			@FormDataParam("uploaded_file") FormDataContentDisposition fileDetail,
			@FormDataParam("email") String email) {
		System.out.println(fileDetail.getFileName());
		String UPLOAD_FOLDER = "c:/uploadedFiles/";
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null){
			System.out.println("error 400");
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid form data").build();
		}
		// create our destination folder, if it not exists
		try {
			userService.createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			System.out.println("Can not create destination folder on server");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Can not create destination folder on server")
					.build();
		}
		String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
		try {
			userService.saveToFile(uploadedInputStream, uploadedFileLocation);
			userService.updateImage(email, uploadedFileLocation);
			
		} catch (IOException e) {
			System.out.println("Can not save file: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not save file").build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not save file").build();
		}
			return Response.ok("File saved to " + uploadedFileLocation).build();
	}

	@GET
	@Path("/getImage/{email}")
	@Produces({"image/*"})
	public Response getFullImage(@PathParam("email") String email) {
		
		try {
			String path = userService.getPathImage(email);
			System.out.println("email path:" + email + ":" + path);
			if(path != null && !path.isEmpty()){
				System.out.println("path:" + path);
				//String imageFile = "C:/uploadedFiles/71mv2ecGuBL.jpg";
				File file = new File(path);
				String fileName = path.substring(path.lastIndexOf("/")+1);
				  ResponseBuilder response = Response.ok((Object) file);
				  response.header("Content-Disposition",
				   "attachment; filename=" + fileName);
				  return response.build();
			}else{
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not find file").build();
		}
		 
	}
	
	@PUT
	@Path("/deleteImage")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteImage(User user) {

		try {
			String email = user.getCorreo();
			String path = userService.getPathImage(email);
			File file = new File(path);
			if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    			userService.deleteImage(email);
    			return Response.status(Response.Status.NO_CONTENT).build();			
			}else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DELETE
	@Path("/delete/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("email") String email) {

		try {
			userService.deleteUser(email);
			return Response.status(Response.Status.NO_CONTENT).build();

		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}