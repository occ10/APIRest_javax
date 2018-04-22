package com.university.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.university.model.Car;
import com.university.model.User;
import com.university.service.CarService;
import com.university.service.UserService;

import Exception.ServiceException;

@Path("/CarService") 
public class CarRest {
	
	CarService carService = new CarService();  
	UserService userService = new UserService();
	
	@POST
	@Path("/car")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertCar(Car car) {
		System.out.println("call to insert car");
		try {
			Car userCar = carService.insertCar(car);
			System.out.println("the user is created:" + userCar.getUser());
				return Response.status(Response.Status.CREATED).entity(userCar).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/car/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCar(@PathParam("email") String email) {

		try {
			Car userCar = carService.getCar(email);
			if(userCar != null){
				System.out.println("call to get car con email: " + email);
				return Response.ok(userCar).build();
			}else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/getCarImage/{email}")
	@Produces({"image/*"})
	public Response getFullImage(@PathParam("email") String email) {
		System.out.println("call to get carImage path: " + email);
		try {
			String path = carService.getPathImage(email);
			System.out.println("call to get carImage path:" + path);
			if(path != null && !path.isEmpty()){
				//String imageFile = "C:/uploadedFiles/71mv2ecGuBL.jpg";
				File file = new File(path);
				String fileName = path.substring(path.lastIndexOf("/")+1);
				System.out.println("file name: " + fileName);
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
	@DELETE
	@Path("/deleteCar/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteImage(@PathParam("email") String email) {

		try {

			String path = carService.getPathImage(email);
			if(path != null){
				File file = new File(path);
				file.delete();
    			System.out.println(file.getName() + " is deleted!");
			}
    			carService.deleteCar(email);
    			return Response.status(Response.Status.NO_CONTENT).build();			
			

		} catch (ServiceException e) {
			
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
			carService.updateImage(email, uploadedFileLocation);
			
		} catch (IOException e) {
			System.out.println("Can not save file: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not save file").build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can not save file").build();
		}
			return Response.ok("File saved to " + uploadedFileLocation).build();
	}
}
