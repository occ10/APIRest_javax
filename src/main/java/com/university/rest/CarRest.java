package com.university.rest;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.university.model.Car;
import com.university.service.CarService;
import Exception.ServiceException;

@Path("/CarService") 
public class CarRest {
	
	CarService carService = new CarService();  
	
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
		System.out.println("call to get car");
		try {
			Car userCar = carService.getCar(email);
			if(userCar != null)
				return Response.ok(userCar).build();
			else
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
			if(!path.equals("") && !path.equals(null)){
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
}
