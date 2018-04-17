package com.university.dao;

import java.sql.Statement;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.model.*;
import com.university.service.UserService;

import Exception.ServiceException;

public class UserDao {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static UserDao getInstance() {
		return new UserDao();
	}

	public User insert(User user) throws ServiceException {
		
		Connection connection = null;
		User userResult = null;
		try {

			try {
				user.getSalt1(user.getContraseña());
				// System.out.println("salt: "+ user.getSalt());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			connection.setAutoCommit(false);
			
			String sql = "insert into usuario (correo, nombre, apellido, edad, contraseña, telefono, salt, foto, detalles) values ("
					+ "'" + user.getCorreo() + "'," + "'" + user.getNombre() + "'," + "'" + user.getApellido() + "',"
					+ user.getEdad() + "," + "'" + user.getContraseña() + "'," + "'" + user.getTelefono() + "'," + "'"
					+ user.getSalt() + "'," + "'" + user.getFoto() + "'," + "'" + user.getDetalles() + "'" + ")";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			
			String sqlTow = "select * from usuario where correo=" + "'" + user.getCorreo() + "'";
			Statement stmtTow = connection.createStatement();
			ResultSet rs= stmtTow.executeQuery(sqlTow);
			if (rs.next()) {
				userResult = new User();
				userResult.setCorreo(rs.getString("correo"));
				userResult.setNombre(rs.getString("nombre"));
				userResult.setApellido(rs.getString("apellido"));
				userResult.setEdad(rs.getInt("edad"));
				userResult.setContraseña(rs.getString("contraseña"));
				userResult.setTelefono(rs.getString("telefono"));
				userResult.setSalt(rs.getString("salt"));
				userResult.setFoto(rs.getString("foto"));
				userResult.setDetalles(rs.getString("detalles"));
				userResult.setConfirmado(rs.getString("confirmado"));
				userResult.setOpcion(rs.getInt("opcion"));
			}
			
			connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage().toString());
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					System.out.println(ex.toString());
				}
			}
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				throw new ServiceException(ex.getMessage());
			}
		}
		return userResult;
	}

	public List<User> getAllUsers() throws ServiceException {

		List<User> lista = new ArrayList<User>();
		User user;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(select * from usuario)";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				user = new User();
				user.setCorreo(rs.getString("correo"));
				user.setNombre(rs.getString("nombre"));
				user.setApellido(rs.getString("apellido"));
				user.setEdad(rs.getInt("edad"));
				user.setContraseña(rs.getString("contraseña"));
				user.setTelefono(rs.getString("telefono"));
				user.setSalt(rs.getString("salt"));
				user.setFoto(rs.getString("foto"));
				user.setDetalles(rs.getString("detalles"));
				user.setConfirmado(rs.getString("confirmado"));
				user.setOpcion(rs.getInt("opcion"));
				lista.add(user);
			}
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return lista;
	}

	public User getUser(String correo, String password) throws ServiceException {
		User user = new User();
		System.out.println("inicio session llamada al dao");
		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "select contraseña,salt from usuario where correo=" + "'" + correo + "'";
			Statement stmtone = connection.createStatement();
			ResultSet rsone = stmtone.executeQuery(sql);
			if (rsone.next()) {
				System.out.println("excute usuario existe");
				try {
					if (user.getPassword(password, rsone.getString("contraseña"), rsone.getString("salt"))) {
						String sqltow = "select * from usuario where correo=" + "'" + correo + "'";
						// System.out.println(sql2);
						Statement stmttow = connection.createStatement();
						ResultSet rstow = stmttow.executeQuery(sqltow);
						
						// System.out.println("excute " + rs2.next());
						if (rstow.next()) {
							System.out.println("excute usuario correcto");
							user.setCorreo(rstow.getString("correo"));
							user.setNombre(rstow.getString("nombre"));
							user.setApellido(rstow.getString("apellido"));
							user.setEdad(rstow.getInt("edad"));
							user.setContraseña(rstow.getString("contraseña"));
							user.setTelefono(rstow.getString("telefono"));
							user.setSalt(rstow.getString("salt"));
							user.setFoto(rstow.getString("foto"));
							user.setDetalles(rstow.getString("detalles"));
							user.setConfirmado(rstow.getString("confirmado"));
							user.setOpcion(rstow.getInt("opcion"));
						}

					}else
						return null;
				} catch (NoSuchAlgorithmException e) {
					throw new ServiceException(e.getMessage());
				}
			}else
				return null;

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return user;
	}

	public User getUserByMail(String correo) throws ServiceException {

		System.out.print("correo es :" + correo);
		User user = null;

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "select * from usuario where correo=" + "'" + correo + "'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				user = new User();
				user.setCorreo(rs.getString("correo"));
				user.setNombre(rs.getString("nombre"));
				user.setApellido(rs.getString("apellido"));
				user.setEdad(rs.getInt("edad"));
				user.setContraseña(rs.getString("contraseña"));
				user.setTelefono(rs.getString("telefono"));
				user.setSalt(rs.getString("salt"));
				user.setFoto(rs.getString("foto"));
				user.setDetalles(rs.getString("detalles"));
				user.setConfirmado(rs.getString("confirmado"));
				user.setOpcion(rs.getInt("opcion"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error aqui");
			throw new ServiceException(e.getMessage());
		}
		return user;
	}

	public boolean update(User user) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update usuario set " + "correo=" + "'" + user.getCorreo() + "'," + "nombre=" + "'"
					+ user.getNombre() + "'," + "apellido=" + "'" + user.getApellido() + "'," + "edad=" + user.getEdad()
					+ "," + "telefono=" + "'" + user.getTelefono() +  "',"
					+ "detalles=" + "'" + user.getDetalles() + "'" + "where correo=" + "'" + user.getCorreo() + "'";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return true;
	}

	public boolean delete(String correo) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "delete from  usuario where correo=" + "'" + correo + "'";
			// System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return true;
	}

	public void updateMail(String correo) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update usuario set " + "confirmado='SI'" + "where correo=" + "'" + correo + "'";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}