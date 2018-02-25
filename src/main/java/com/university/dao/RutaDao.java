package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Ruta;
import com.university.model.User;

public class RutaDao {

	//SELECT `ruta`.*, `usuario`.* FROM `rutaa` JOIN `realizaruta` ON `realizaruta`.`ruta` = `ruta`.`id` JOIN `usuario` ON `usuario`.`correo` = `realizaruta`.`usuario` WHERE `ruta`.`origen` = 'Elche' AND (ruta.plazas - ruta.plazasOcupadas) >0 AND `ruta`.`opcion` = '0' AND `usuario`.`correo` != 'usuario30@gmail.com'

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static RutaDao getInstance() {
		return new RutaDao();
	}
	
	public List<Ruta> getAllRoutes(String correo) {

		List<Ruta> lista = new ArrayList<Ruta>();
		
		Ruta ruta;
		User user;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT ruta.*, usuario.* FROM ruta JOIN realizaruta ON realizaruta.ruta = ruta.id JOIN usuario ON usuario.correo = realizaruta.usuario WHERE (ruta.plazas - ruta.plazasOcupadas) >0 AND ruta.opcion = '0' AND usuario.correo != '"+correo+"')";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ruta = new Ruta();
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
				
				ruta.setUser(user);
				ruta.setId(rs.getInt("id"));
				ruta.setPlazas(rs.getInt("plazas"));
				ruta.setPlazasOcupadas(rs.getInt("plazasOcupadas"));
				ruta.setOrigen(rs.getString("origen"));
				ruta.setDetalles(rs.getString("detalles"));
				ruta.setPrecio(rs.getDouble("precio"));
				ruta.setFechaPublicacion(rs.getDate("fechaPublicacion"));
				ruta.setOpcion(rs.getInt("opcion"));
				lista.add(ruta);
				
			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage() + "Error aqui");
			return null;
		}
		return lista;
	}
}
