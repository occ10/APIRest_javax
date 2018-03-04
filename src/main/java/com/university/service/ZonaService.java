package com.university.service;

import java.util.List;

import com.university.dao.ZonaDao;
import com.university.model.Zona;

public class ZonaService {

	
	private ZonaDao zonaDao;
	public  ZonaService(){
		zonaDao =  ZonaDao.getInstance();
	}
	public List<Zona> getZones(String code){ 	 
	      return zonaDao.getAllZones(code); 
	   }
}
