package com.university.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	public UserService() {
		userDao = UserDao.getInstance();
	}

	public List<User> getUsers() throws ServiceException {
		return userDao.getAllUsers();
	}

	public UserDTO insertUser(User user) throws ServiceException {
		// userDao.insert(user);
		// User userResult = userDao.insert(user);
		return new UserDTO(userDao.insert(user));
	}

	public UserDTO obtenerDatosUser(String Correo, String password) throws ServiceException {
		User user = userDao.getUser(Correo, password);
		UserDTO userDTO = null;
		if(user == null)
			System.out.println("usuario nuloooooo");
		else
			System.out.println("usuario " + user.getCorreo());
		return userDTO = user != null ? new UserDTO(user) : null;
	}

	public UserDTO obtenerDatosUserByMail(String Correo) throws ServiceException {
		User user = userDao.getUserByMail(Correo);
		if (user != null)
			return new UserDTO(user);
		else
			return null;
	}

	public boolean updateUser(User user) throws ServiceException {
		return userDao.update(user);
	}

	public boolean deleteUser(String correo) throws ServiceException {
		return userDao.delete(correo);
	}

	public static void sendMail(String sendTo) throws ServiceException {

		final String username = "infochamit@gmail.com";
		final String password = "Rector3174";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("infochamit@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
			message.setSubject("Testing Subject");
			// message.setText("Dear Mail Crawler," + "\n\n No spam to my email,
			// please!");
			message.setContent("<p>haga click en el enlace abajo para activar tu cuenta, y poder utilizar la aplicacion </p><a href='http://localhost:8080/tfg/rest/UserService/updateMail/" + sendTo + "'>Confirm register</a>", "text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateMailUser(String correo) throws ServiceException {
		userDao.updateMail(correo);
	}
	
	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream
	 *            - InputStream to be saved
	 * @param target
	 *            - full path to destination file
	 */
	public void saveToFile(InputStream inStream, String target)
			throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName
	 *            - full path to the folder
	 * @throws SecurityException
	 *             - in case you don't have permission to create the folder
	 */
	public void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
}
