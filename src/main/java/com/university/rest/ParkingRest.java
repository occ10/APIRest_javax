package com.university.rest;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.model.Parking;
import com.university.service.ParkingService;
@Path("/ParkingService") 
public class ParkingRest {
	
	   ParkingService parkingService = new ParkingService();  
	   @GET 
	   @Path("/parking") 
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getParking(){
		   List<Parking> parking = null;
		   parking = parkingService.getParkings();
		   if(parking != null)
		   return Response.ok(parking).build();
		   else
			   return Response.status(Response.Status.NO_CONTENT).build();
	   }

}
