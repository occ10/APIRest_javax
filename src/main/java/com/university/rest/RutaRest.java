package com.university.rest;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.university.service.RutaService;
import com.university.service.UserService;

import Exception.ServiceException;

import com.university.model.*;

@Path("/RutaService")
public class RutaRest {

	RutaService rutaService = new RutaService();

	// obtiene todas las rutas publicadas que tienen plazas disponibles
	@GET
	@Path("/routes/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoutes(@PathParam("correo") String correo) {
		List<Ruta> rutas = null;
		try {
			rutas = rutaService.getRoutes(correo);
			if (rutas != null)
				return Response.ok(rutas).build();
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/routesOrigine/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoutesFrmOrigine(@PathParam("correo") String correo) {
		// public Response getRoutes(@PathParam("correo") String correo){
		List<Ruta> rutas = null;
		try {
			rutas = rutaService.getAllRoutesFromOrigin(correo, "Elche");
			if (rutas != null)
				return Response.ok(rutas).build();
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/insertRoute")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertRoute(Ruta route) {

		try {
			Ruta routeResult = rutaService.insertRoute(route);
			// return Response.ok(routeResult).build();
			return Response.status(Response.Status.CREATED).entity(routeResult).build();

		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
