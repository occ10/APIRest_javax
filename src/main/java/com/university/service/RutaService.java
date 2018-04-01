package com.university.service;

import java.util.List;

import com.university.dao.RutaDao;
import com.university.model.Ruta;


public class RutaService {

	
	private RutaDao rutaDao;
	public  RutaService(){
		rutaDao =  RutaDao.getInstance();
	}
	public List<Ruta> getRoutes(String email){ 	 
	      return rutaDao.getAllRoutes(email); 
	   }
	public Ruta getRoute(int idRoute){ 	 
	      return rutaDao.getRoute(idRoute); 
	   }
	public int insertRoute(Ruta ruta){ 	 
	      return rutaDao.insertRoute(ruta); 
	   }
	public List<Ruta> getAllRoutesFromOrigin(String email,String origin){ 	 
		      return rutaDao.getAllRoutesFromOrigin(email,origin); 
		   }
}
