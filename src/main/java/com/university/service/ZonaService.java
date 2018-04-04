package com.university.service;

import java.sql.SQLException;
import java.util.List;

import com.university.dao.ZonaDao;
import com.university.model.User;
import com.university.model.Zona;

import Exception.ServiceException;

public class ZonaService {

	
	private ZonaDao zonaDao;
	public  ZonaService(){
		zonaDao =  ZonaDao.getInstance();
	}
	public List<Zona> getZones(String code) throws ServiceException{ 	 
		return zonaDao.getAllZones(code); 
	}
	
	public Zona getZone(String code)throws ServiceException{ 	 
		return zonaDao.getZone(code); 
	}
	
	public void updateZone(int code)throws ServiceException{
		zonaDao.updateZone(code);
	}
	
	public void desocuppyZone(int code)throws ServiceException{
		zonaDao.desocuppyZone(code);
	}
	public Zona userOcuppyZone(String email) throws ServiceException{
		return zonaDao.userOcuppyZone(email);
	}
}
