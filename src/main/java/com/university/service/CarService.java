package com.university.service;

import com.university.dao.CarDao;
import com.university.model.Car;

import Exception.ServiceException;

public class CarService {
	private CarDao carDao;

	public CarService() {
		carDao = CarDao.getInstance();
	}
	
	public Car insertCar(Car car) throws ServiceException {
		return carDao.insertCar(car);
	}
	
	public Car getCar(String email) throws ServiceException {
		return carDao.getCar(email);
	}
	
	public String getPathImage(String email) throws ServiceException {
		return carDao.getPathImage(email);
	}
	
	public void deleteCar(String email) throws ServiceException {
			carDao.deleteCar(email);
	}
	public void updateImage(String email, String path) throws ServiceException {
			carDao.updateImage(email, path);
	}
	
}
