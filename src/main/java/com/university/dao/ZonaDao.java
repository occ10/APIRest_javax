package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Parking;
import com.university.model.Ruta;
import com.university.model.User;
import com.university.model.Zona;

public class ZonaDao {
	
	//SELECT * FROM `zonass` WHERE `aparcamiento` = '0000PB324' AND `ocupada` = '1'
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static ZonaDao getInstance() {
		return new ZonaDao();
	}
	
	public List<Zona> getAllZones(String code) {
        System.out.println("codigo zona " + code);
		List<Zona> lista = new ArrayList<Zona>();
		Zona zona;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM zonas WHERE aparcamiento = '"+ code + "' AND ocupada = '1')";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				zona = new Zona();
				
				zona.setId(rs.getInt("id"));
				zona.setLon(rs.getDouble("lon"));
				zona.setLat(rs.getDouble("lat"));
				zona.setAparcamiento(rs.getString("aparcamiento"));
				zona.setOcupada(rs.getInt("ocupada"));
				
				lista.add(zona);
			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			return null;
		}
		return lista;
	}
}
