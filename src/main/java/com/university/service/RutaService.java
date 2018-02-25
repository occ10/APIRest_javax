package com.university.service;

import java.util.List;

import com.university.dao.RutaDao;
import com.university.model.Ruta;


public class RutaService {

	
	private RutaDao rutaDao;
	public  RutaService(){
		rutaDao =  RutaDao.getInstance();
	}
	public List<Ruta> getRutas(String correo){ 	 
	      return rutaDao.getAllRoutes(correo); 
	   }
}
