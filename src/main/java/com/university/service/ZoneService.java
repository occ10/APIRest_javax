package com.university.service;

import java.sql.SQLException;
import java.util.List;

import com.university.dao.ZoneDao;
import com.university.model.User;
import com.university.model.Zone;

import Exception.ServiceException;

public class ZoneService {

	private ZoneDao zoneDao;

	public ZoneService() {
		zoneDao = ZoneDao.getInstance();
	}

	public List<Zone> getZones(String code) throws ServiceException {
		return zoneDao.getAllZones(code);
	}

	public Zone getZone(String code) throws ServiceException {
		return zoneDao.getZone(code);
	}

	public void updateZone(int code) throws ServiceException {
		zoneDao.updateZone(code);
	}

	public void desocuppyZone(int code) throws ServiceException {
		zoneDao.desocuppyZone(code);
	}

	public Zone userOcuppyZone(String email) throws ServiceException {
		return zoneDao.userOcuppyZone(email);
	}
}
