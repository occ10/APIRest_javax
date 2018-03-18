package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
	
	public Zona getZone(String code) {
        System.out.println("codigo zona " + code);
		Zona zona = null;

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM zonas WHERE id = "+ code + ")";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				zona = new Zona();
				
				zona.setId(rs.getInt("id"));
				zona.setLon(rs.getDouble("lon"));
				zona.setLat(rs.getDouble("lat"));
				zona.setAparcamiento(rs.getString("aparcamiento"));
				zona.setOcupada(rs.getInt("ocupada"));

			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			return null;
		}
		return zona;
	}
	
	//UPDATE `zonass` SET `ocupada` = '0' WHERE `id` = '461'
	
	
	public boolean updateZone(int code) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update zonas set " + "ocupada='0' where id=" + code ;
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}else{
				//INSERT INTO `usuarioaparcacochee` (`zona`, `coche`, `fecha`) VALUES ('461', '3214LLL', '2018-03-11 09:29:27')
				//String sql2 = "update zonas set " + "ocupada=0 where id=" + "'" + code + "'";
		        long millis=System.currentTimeMillis();  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		        java.sql.Date date=new java.sql.Date(millis);  
				String sql2 = "insert into usuarioaparcacoche (zona, coche, fecha) values ("
						+  code + ",'4444kkk','" + sdf.format(date) + "'" +")";
				System.out.println(sql2);
				Statement stmt2 = connection.createStatement();
				stmt2.execute(sql2);
				if (stmt2.getUpdateCount() != 1) {
					return false;

				}			
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	public boolean desocuppyZone(int code) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update zonas set " + "ocupada='1' where id=" + code ;
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt.getUpdateCount() != 1) {
				return false;

			}			
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
}
