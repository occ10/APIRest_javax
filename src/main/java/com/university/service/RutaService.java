package com.university.service;

import java.util.List;

import com.university.dao.RutaDao;
import com.university.model.Ruta;

import Exception.ServiceException;

public class RutaService {

	private RutaDao rutaDao;

	public RutaService() {
		rutaDao = RutaDao.getInstance();
	}

	public List<Ruta> getRoutes(String email) throws ServiceException {
		return rutaDao.getAllRoutes(email);
	}

	public Ruta getRoute(int idRoute) throws ServiceException {
		return rutaDao.getRoute(idRoute);
	}

	public Ruta insertRoute(Ruta ruta) throws ServiceException {
		return rutaDao.insertRoute(ruta);
	}

	public List<Ruta> getAllRoutesFromOrigin(String email, String origin) throws ServiceException {
		return rutaDao.getAllRoutesFromOrigin(email, origin);
	}
}
