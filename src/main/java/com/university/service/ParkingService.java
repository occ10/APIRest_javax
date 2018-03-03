package com.university.service;

import java.util.List;

import com.university.dao.ParkingDao;
import com.university.model.Parking;


public class ParkingService {

	
	private ParkingDao parkingDao;
	public  ParkingService(){
		parkingDao =  parkingDao.getInstance();
	}
	public List<Parking> getParkings(){ 	 
	      return parkingDao.getAllParkings(); 
	   }
}
