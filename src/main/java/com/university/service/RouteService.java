package com.university.service;

import java.util.List;

import com.university.dao.RouteDao;
import com.university.model.Route;

import Exception.ServiceException;

public class RouteService {

	private RouteDao routeDao;

	public RouteService() {
		routeDao = RouteDao.getInstance();
	}

	public List<Route> getRoutes(String email) throws ServiceException {
		return routeDao.getAllRoutes(email);
	}

	public Route getRoute(int idRoute) throws ServiceException {
		return routeDao.getRoute(idRoute);
	}

	public Route insertRoute(Route route) throws ServiceException {
		return routeDao.insertRoute(route);
	}

	public List<Route> getAllRoutesFromOrigin(String email, String origin) throws ServiceException {
		return routeDao.getAllRoutesFromOrigin(email, origin);
	}
	
	public List<Route> getUserRoutes(String email) throws ServiceException {
		return routeDao.getUserRoutes(email);
	}
}
