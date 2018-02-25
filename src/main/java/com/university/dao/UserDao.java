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

	/*
	 * public User create(String correo, String nombre, String apellido, int
	 * edad, String contraseña, String telefono, String salt, String foto,
	 * String detalles, String Confirmado, int opcion) { return new User(correo,
	 * nombre, apellido, edad, contraseña, telefono, salt, foto, detalles,
	 * Confirmado, opcion); }
	 */

	public boolean insert(User user) {
		try {

			try {
				user.getSalt1(user.getContraseña());
				// System.out.println("salt: "+ user.getSalt());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "insert into usuario (correo, nombre, apellido, edad, contraseña, telefono, salt, foto, detalles) values ("
					+ "'" + user.getCorreo() + "'," + "'" + user.getNombre() + "'," + "'" + user.getApellido() + "',"
					+ user.getEdad() + "," + "'" + user.getContraseña() + "'," + "'" + user.getTelefono() + "'," + "'"
					+ user.getSalt() + "'," + "'" + user.getFoto() + "'," + "'" + user.getDetalles() + "'" + ")";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public List<User> getAllUsers() {

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
			// System.out.println(e.getMessage() + "Error aqui");
			return null;
		}
		return lista;
	}

	// public User getUser(String correo, String password) {
	public User getUser(String correo, String password) {

		// List<User> lista = new ArrayList<User>();
		//UserService.sendMail("ff");
		//System.out.print("password es :" + password);
		User user = null;

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "select contraseña,salt from usuario where correo=" + "'" + correo + "'";
			// System.out.println(sql);
			Statement stmtone = connection.createStatement();
			ResultSet rsone = stmtone.executeQuery(sql);
			if (rsone.next()) {
				user = new User();
				try {
					if (user.getPassword(password, rsone.getString("contraseña"), rsone.getString("salt"))) {

						String sqltow = "select * from usuario where correo=" + "'" + correo + "'";
						//System.out.println(sql2);
						Statement stmttow = connection.createStatement();
						ResultSet rstow = stmttow.executeQuery(sqltow);
						//System.out.println("excute " + rs2.next());
						if (rstow.next()) {
							//user = new User();
							//System.out.println("correo devuelto " + rs.getString("correo"));
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
							// lista.add(user);
						}

					}
				} catch (NoSuchAlgorithmException e) {
					//System.out.println("correo devuelto 1");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// lista.add(user);
			}

		} catch (SQLException e) {
			//System.out.println("correo devuelto 2 "+ e.toString());
			// System.out.println(e.getMessage() + "Error aqui");
			return user;
		}
		return user;
	}

	// public User getUser(String correo, String password) {
	public User getUserByMail(String correo) {

		// List<User> lista = new ArrayList<User>();
		// UserService.sendMail("ff");
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
				// lista.add(user);
			}

			// lista.add(user);

		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error aqui");
			return user;
		}
		return user;
	}

	public boolean update(User user) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update usuario set " + "correo=" + "'" + user.getCorreo() + "'," + "nombre=" + "'"
					+ user.getNombre() + "'," + "apellido=" + "'" + user.getApellido() + "'," + "edad=" + user.getEdad()
					+ "," + "telefono=" + "'" + user.getTelefono() + "'," + "foto=" + "'" + user.getFoto() + "',"
					+ "detalles=" + "'" + user.getDetalles() + "'" + "where correo=" + "'" + user.getCorreo() + "'";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean delete(String correo) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "delete from  usuario where correo=" + "'" + correo + "'";
			// System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean updateMail(String correo) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update usuario set " + "confirmado='SI'" + "where correo=" + "'" + correo + "'";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}