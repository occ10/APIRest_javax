package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Parking;
import com.university.model.User;

public class ParkingDao {

	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static ParkingDao getInstance() {
		return new ParkingDao();
	}
	
	public List<Parking> getAllParkings() {

		List<Parking> lista = new ArrayList<Parking>();
		Parking parking;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(select * from aparcamiento)";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				parking = new Parking();
				parking.setId(rs.getInt("id"));
				parking.setCodigo(rs.getString("codigo"));
				parking.setIdParking(rs.getInt("idParking"));
				parking.setPlazas(rs.getInt("plazas"));
				parking.setSuperficie(rs.getDouble("superficie"));
				parking.setLon(rs.getDouble("lon"));
				parking.setLat(rs.getDouble("lat"));
				lista.add(parking);
			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			return null;
		}
		return lista;
	}
	
}
