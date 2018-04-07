package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.university.model.Announcement;
import com.university.model.Car;
import com.university.model.Route;
import com.university.model.User;
import com.university.model.UserDTO;

import Exception.ServiceException;

public class AnnouncementDao {

	//SELECT `ruta`.*, `usuario`.*, `coche`.* FROM `usuario` JOIN `coche` ON `usuario`.`correo` = `coche`.`usuario` JOIN `realizaruta` ON `usuario`.`correo` = `realizaruta`.`usuario` JOIN `ruta` ON `realizaruta`.`ruta` = `ruta`.`id` WHERE `usuario`.`correo` = 'usuario10@gamail.com' AND `ruta` = '21'

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static AnnouncementDao getInstance() {
		return new AnnouncementDao();
	}
	
	// obtener una ruta dada por su id
	public Announcement getAnounce(int idRoute, String email) throws ServiceException {

		Announcement anounce = null;
		Route route = null;
		User user = null;
		Car car = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT ruta.*, usuario.*, coche.* FROM usuario JOIN coche ON usuario.correo = coche.usuario "
					+ "JOIN realizaruta ON usuario.correo = realizaruta.usuario "
					+ "JOIN ruta ON realizaruta.ruta = ruta.id "
					+ "WHERE usuario.correo ='" + email + "' AND ruta = " + idRoute + ")";
			System.out.println("consulta sql: " + sql);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				anounce = new Announcement();
				route = new Route();
				user = new User();
				car = new Car();

				route.setId(rs.getInt("id"));
				route.setPlazas(rs.getInt("plazas"));
				route.setPlazasOcupadas(rs.getInt("plazasOcupadas"));
				route.setOrigen(rs.getString("origen"));
				route.setDetalles(rs.getString(5));
				route.setPrecio(rs.getDouble("precio"));
				route.setFechaPublicacion(rs.getDate("fechaPublicacion"));
				route.setOpcion(rs.getInt(8));
				
				user.setCorreo(rs.getString("correo"));
				user.setNombre(rs.getString("nombre"));
				user.setApellido(rs.getString("apellido"));
				user.setEdad(rs.getInt("edad"));
				user.setContraseña(rs.getString("contraseña"));
				user.setTelefono(rs.getString("telefono"));
				user.setSalt(rs.getString("salt"));
				user.setFoto(rs.getString("foto"));
				user.setDetalles(rs.getString(17));
				user.setConfirmado(rs.getString("confirmado"));
				user.setOpcion(rs.getInt(19));
				
				car.setRegistration(rs.getString("matricula"));
				car.setModel(rs.getString("modelo"));
				car.setColor(rs.getString("color"));
				car.setSeating(rs.getInt("acientos"));
				car.setUser(rs.getString("usuario"));
				car.setBrand(rs.getString("marca"));
				car.setCategory(rs.getString("categoria"));
				car.setImage(rs.getString("imageFoto"));
				
				route.setUser(new UserDTO(user));
				route.setCar(car);
				anounce.setRoute(route);
				//anounce.setUser(new UserDTO(user));
				//anounce.setCar(car);

			}
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return anounce;
	}
}
