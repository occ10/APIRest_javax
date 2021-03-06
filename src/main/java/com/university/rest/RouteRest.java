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

import com.university.service.RouteService;
import com.university.service.UserService;

import Exception.ServiceException;

import com.university.model.*;

@Path("/RutaService")
public class RouteRest {

	RouteService routeService = new RouteService();

	// obtiene todas las rutas publicadas que tienen plazas disponibles
	@GET
	@Path("/routes/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoutes(@PathParam("email") String email) {
		List<Route> routes = null;
		try {
			routes = routeService.getRoutes(email);
			if (routes != null)
				return Response.ok(routes).build();
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/routesOrigine/{email}/{origine}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoutesFrmOrigine(@PathParam("email") String email, @PathParam("origine") String origine) {
		// public Response getRoutes(@PathParam("correo") String correo){
		List<Route> routes = null;
		try {
			routes = routeService.getAllRoutesFromOrigin(email, origine);
			if (routes != null)
				return Response.ok(routes).build();
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/routesUser/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoutesUser(@PathParam("email") String email) {
		// public Response getRoutes(@PathParam("correo") String correo){
		List<Route> routes = null;
		try {
			routes = routeService.getUserRoutes(email);
			if (routes != null)
				return Response.ok(routes).build();
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
	public Response insertRoute(Route route) {

		try {
			Route routeResult = routeService.insertRoute(route);
			// return Response.ok(routeResult).build();
			return Response.status(Response.Status.CREATED).entity(routeResult).build();

		} catch (ServiceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
