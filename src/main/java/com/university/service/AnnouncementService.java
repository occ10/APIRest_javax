package com.university.service;

import com.university.dao.AnnouncementDao;
import com.university.model.Announcement;

import Exception.ServiceException;

public class AnnouncementService {

	private AnnouncementDao anuncioDao;

	public AnnouncementService() {
		anuncioDao = AnnouncementDao.getInstance();
	}
	
	public Announcement getAnounce(int idRoute, String email) throws ServiceException {
		return anuncioDao.getAnounce(idRoute, email);
	}
}
