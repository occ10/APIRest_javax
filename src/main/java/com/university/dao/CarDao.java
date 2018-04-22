package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.university.model.Car;
import Exception.ServiceException;

public class CarDao {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static CarDao getInstance() {
		return new CarDao();
	}
	
	public Car insertCar(Car car) throws ServiceException {
		
		Connection connection = null;
		Car carResult = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			
			String sql = "insert into coche (matricula, modelo, color, acientos, usuario, marca, categoria) values ("
					+ "'" + car.getRegistration() + "','" + car.getModel() + "','" + car.getColor() + "',"
					+ car.getSeating() + ",'" + car.getUser() + "','" + car.getBrand() + "','"
					+ car.getCategory() +  "'" + ")";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		
			String sqlTow = "select * from coche where usuario=" + "'" + car.getUser() + "'";
			Statement stmtTow = connection.createStatement();
			ResultSet rs= stmtTow.executeQuery(sqlTow);
			if (rs.next()) {
				carResult = new Car();
				System.out.println("resultado dao:" + rs.getString("matricula"));
				carResult.setRegistration(rs.getString("matricula"));
				carResult.setModel(rs.getString("modelo"));
				carResult.setColor(rs.getString("color"));
				carResult.setSeating(rs.getInt("acientos"));
				carResult.setUser(rs.getString("usuario"));
				carResult.setBrand(rs.getString("marca"));
				carResult.setCategory(rs.getString("categoria"));
				carResult.setImage(rs.getString("imageFoto"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		return carResult;
	}
	
	
	public Car getCar(String email) throws ServiceException {
		System.out.println("call to get carDao");
		Connection connection = null;
		Car carResult = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			
			String sqlTow = "select * from coche where usuario=" + "'" + email + "'";
			Statement stmtTow = connection.createStatement();
			ResultSet rs= stmtTow.executeQuery(sqlTow);
			if (rs.next()) {
				carResult = new Car();
				System.out.println("resultado dao:" + rs.getString("matricula"));
				carResult.setRegistration(rs.getString("matricula"));
				carResult.setModel(rs.getString("modelo"));
				carResult.setColor(rs.getString("color"));
				carResult.setSeating(rs.getInt("acientos"));
				carResult.setUser(rs.getString("usuario"));
				carResult.setBrand(rs.getString("marca"));
				carResult.setCategory(rs.getString("categoria"));
				carResult.setImage(rs.getString("imageFoto"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		return carResult;
	}
	
	public String getPathImage(String email) throws ServiceException {
		String path = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "select imageFoto from coche where usuario=" + "'" + email + "'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				path = rs.getString("imageFoto");
			}

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return path;
	}
	
	public void deleteCar(String email) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "delete from coche where usuario=" + "'" + email + "'";
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void updateImage(String email, String path) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update coche set " + "imageFoto='" + path + "'" + "where usuario=" + "'" + email + "'";
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
}
