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

import Exception.ServiceException;

public class ZonaDao {

	// SELECT * FROM `zonass` WHERE `aparcamiento` = '0000PB324' AND `ocupada` =
	// '1'
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

	public List<Zona> getAllZones(String code) throws ServiceException {
		System.out.println("codigo zona " + code);
		List<Zona> lista = new ArrayList<Zona>();
		Zona zona;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM zonas WHERE aparcamiento = '" + code + "' AND ocupada = '1')";

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
			throw new ServiceException(e.getMessage());

		}
		return lista;
	}

	public Zona getZone(String code) throws ServiceException {
		System.out.println("codigo zona " + code);
		Zona zona = null;

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM zonas WHERE id = " + code + ")";

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
			throw new ServiceException(e.getMessage());
		}
		return zona;
	}

	// UPDATE `zonass` SET `ocupada` = '0' WHERE `id` = '461'

	public void updateZone(int code) throws ServiceException {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			connection.setAutoCommit(false);
			String sql = "update zonas set " + "ocupada='0' where id=" + code;
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

			long millis = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.sql.Date date = new java.sql.Date(millis);
			String sql2 = "insert into usuarioaparcacoche (zona, coche, fecha) values (" + code + ",'4444kkk','"
					+ sdf.format(date) + "'" + ")";
			System.out.println(sql2);
			Statement stmt2 = connection.createStatement();
			stmt2.execute(sql2);
			connection.commit();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					// throw new ServiceException(ex.getMessage());
				}
			}
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.toString());
			}

		}
	}

	public void desocuppyZone(int code) throws ServiceException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "update zonas set " + "ocupada='1' where id=" + code;
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// SELECT `zonass`.`aparcamiento` FROM `zonas` JOIN `usuarioaparcacoche` ON
	// `usuarioaparcacoche`.`zona` = `zonas`.`id` JOIN `coche` ON
	// `usuarioaparcacoche`.`coche` =`coche`.`matricula` WHERE `coche`.`usuario`
	// = 'infochamit@gmail.com' AND `zonas`.`ocupada` = '0'
	public Zona userOcuppyZone(String email) throws ServiceException {

		Zona zone = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");

			String sql = "SELECT * FROM zonas JOIN usuarioaparcacoche "
					+ "ON usuarioaparcacoche.zona = zonas.id JOIN coche "
					+ "ON usuarioaparcacoche.coche =coche.matricula " + "WHERE coche.usuario = '" + email
					+ "' AND zonas.ocupada = '0' "
					+ "and fecha = (select max(fecha) from usuarioaparcacoche where zona = zonas.id)";
			System.out.println(sql);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				zone = new Zona();

				zone.setId(rs.getInt("id"));
				zone.setLon(rs.getDouble("lon"));
				zone.setLat(rs.getDouble("lat"));
				zone.setAparcamiento(rs.getString("aparcamiento"));
				zone.setOcupada(rs.getInt("ocupada"));

			}
		} catch (SQLException e) {
			System.out.println("error:" + e.getMessage());
			throw new ServiceException(e.getMessage());
		}

		return zone;
	}
}
