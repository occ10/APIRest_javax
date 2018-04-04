package com.university.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response.Status;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import com.university.dao.*;
import com.university.model.*;

import Exception.ServiceException;


public class UserService {
	private UserDao userDao;
	public  UserService(){
		userDao =  UserDao.getInstance();
	}
	public List<User> getUsers() throws ServiceException{ 	 
	      return userDao.getAllUsers(); 
	   }
	
	public UserDTO  insertUser(User user) throws ServiceException{		  
		//userDao.insert(user);
		//User userResult = userDao.insert(user);
		return new UserDTO(userDao.insert(user));
	}
	
	public UserDTO obtenerDatosUser(String Correo, String password) throws ServiceException{
		User user = userDao.getUser(Correo, password);
		return user != null ? new UserDTO(user) : null;
	}
	
	public UserDTO obtenerDatosUserByMail(String Correo) throws ServiceException{
		User user = userDao.getUserByMail(Correo);
		if(user != null)
		return new UserDTO(user);
		else 
			return null;
	}
	public boolean updateUser(User user) throws ServiceException{
		return userDao.update(user);
	}
	
	public boolean deleteUser(String correo) throws ServiceException{
		return userDao.delete(correo);
	}
	
	public static void sendMail(String sendTo){
		
		final String username = "infochamit@gmail.com";
		final String password = "Rector3174";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("infochamit@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(sendTo));
			message.setSubject("Testing Subject");
			//message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");
			 message.setContent("<h1>This is actual message</h1>", "text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean updateMailUser(String correo) throws ServiceException {
		// TODO Auto-generated method stub
		System.out.println(correo);
		return userDao.updateMail(correo);
			}
}
