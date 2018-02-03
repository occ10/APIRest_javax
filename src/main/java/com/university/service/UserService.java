package com.university.service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.university.dao.*;
import com.university.model.*;


public class UserService {
	private UserDao userDao;
	public  UserService(){
		userDao =  UserDao.getInstance();
	}
	public List<User> getUsers(){ 	 
	      return userDao.getAllUsers(); 
	   }
	
	public boolean  insertUser(User user){		  
		return userDao.insert(user);
	}
	
	public UserDTO obtenerDatosUser(String Correo, String password){
		User user = userDao.getUser(Correo, password);
		if(user != null)
		return new UserDTO(user);
		else 
			return null;
	}
	public boolean updateUser(User user){
		return userDao.update(user);
	}
	
	public boolean deleteUser(String correo){
		return userDao.delete(correo);
	}
}
