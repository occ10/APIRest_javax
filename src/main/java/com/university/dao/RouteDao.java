package com.university.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.university.model.*;

import Exception.ServiceException;

public class RouteDao {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static RouteDao getInstance() {
		return new RouteDao();
	}
	//SELECT count(*) as totalCount FROM `usuarioo` JOIN `realizaruta` ON `usuario`.`correo` = `realizaruta`.`usuario` JOIN `ruta` ON `realizaruta`.`ruta` = `ruta`.`id` WHERE `usuario`.`correo` = 'usuario10@gamail.com'

	//obtener las rutas de un usuario dado por su email
	// obtener todas las rutas publicadas
	public List<Route> getUserRoutes(String email) throws ServiceException {

		List<Route> lista = new ArrayList<Route>();

		Route route;

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM `ruta` "
					+ "JOIN `realizaruta` ON `realizaruta`.`ruta` = `ruta`.`id` "
					+ "WHERE `realizaruta`.`usuario` = '" + email + "')";
			System.out.println("consulta:" + sql);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				route = new Route();
				route.setId(rs.getInt("id"));
				route.setPlazas(rs.getInt("plazas"));
				route.setPlazasOcupadas(rs.getInt("plazasOcupadas"));
				route.setOrigen(rs.getString("origen"));
				route.setDetalles(rs.getString(5));
				route.setPrecio(rs.getDouble("precio"));
				route.setFechaPublicacion(rs.getDate("fechaPublicacion"));
				route.setOpcion(rs.getInt(8));
				

				lista.add(route);

			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			throw new ServiceException(e.getMessage());
		}
		return lista;
	}
	// obtener todas las rutas publicadas
	public List<Route> getAllRoutes(String email) throws ServiceException {

		List<Route> lista = new ArrayList<Route>();

		Route route;
		User user;
		Car car;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT ruta.*, usuario.* FROM ruta JOIN realizaruta ON realizaruta.ruta = ruta.id JOIN usuario ON usuario.correo = realizaruta.usuario WHERE (ruta.plazas - ruta.plazasOcupadas) > 0 AND ruta.opcion = '0' AND usuario.correo != '"
					+ email + "' order by fechaPublicacion)";
			System.out.println("consulta:" + sql);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				route = new Route();
				user = new User();
				System.out.println(rs.getInt("id"));
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

				route.setUser(new UserDTO(user));

				lista.add(route);

			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			throw new ServiceException(e.getMessage());
		}
		return lista;
	}

	// insertar una ruta y poner campo opcion a 1 a las rutas publicadas
	// anteriormente
	public Route insertRoute(Route route) throws ServiceException {
		String sqlTree = "";
		String sqlOne = "";
		int lastId = 0;
		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			connection.setAutoCommit(false);

			sqlOne = "update ruta t2 JOIN realizaruta t1 ON t1.ruta = t2.id JOIN usuario t3 ON t3.correo = t1.usuario set t2.opcion='1' where t3.correo = "
					+ "'" + route.getUser().getCorreo() + "'";
			Statement stmtOne = connection.createStatement();
			stmtOne.execute(sqlOne);

			long millis = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.sql.Date date = new java.sql.Date(millis);
			String sqlTow = "INSERT INTO ruta (origen, precio, plazas, detalles, fechaPublicacion) VALUES (" + "'"
					+ route.getOrigen() + "'," + route.getPrecio() + "," + route.getPlazas() + ",'"
					+ route.getDetalles() + "','" + sdf.format(date) + "')";

			PreparedStatement ps = connection.prepareStatement(sqlTow, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				lastId = rs.getInt(1);

				sqlTree = "INSERT INTO realizaruta (coche, usuario, ruta, opcion) VALUES (" + "'4444kkk','"
						+ route.getUser().getCorreo() + "'," + lastId + ",'1')";

				Statement stmtTree = connection.createStatement();
				stmtTree.execute(sqlTree);
			}
			connection.commit();
			// }
		} catch (SQLException e) {
			System.out.println(e.getMessage().toString());
			System.out.println(sqlOne);
			System.out.println(sqlTree);
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
		return getRoute(lastId);
	}

	// obtener rutas a partir de un origen
	public List<Route> getAllRoutesFromOrigin(String email, String origin) throws ServiceException {

		List<Route> lista = new ArrayList<Route>();

		Route route;
		User user;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT ruta.*, usuario.* FROM ruta JOIN realizaruta ON realizaruta.ruta = ruta.id JOIN usuario ON usuario.correo = realizaruta.usuario WHERE (ruta.plazas - ruta.plazasOcupadas) >0 AND ruta.origen='"
					+ origin + "' AND ruta.opcion = '0' AND usuario.correo != '" + email + "')";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				route = new Route();
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

				route.setUser(new UserDTO(user));
				route.setId(rs.getInt("id"));
				route.setPlazas(rs.getInt("plazas"));
				route.setPlazasOcupadas(rs.getInt("plazasOcupadas"));
				route.setOrigen(rs.getString("origen"));
				route.setDetalles(rs.getString("detalles"));
				route.setPrecio(rs.getDouble("precio"));
				route.setFechaPublicacion(rs.getDate("fechaPublicacion"));
				route.setOpcion(rs.getInt("opcion"));
				lista.add(route);

			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage() + "Error aqui");
			throw new ServiceException(e.getMessage());
		}
		return lista;
	}

	// obtener una ruta dada por su id
	public Route getRoute(int idRoute) throws ServiceException {

		Route route = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(SELECT * FROM ruta WHERE id=" + idRoute + ")";
			System.out.println("consulta sql: " + sql);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				route = new Route();

				route.setId(rs.getInt("id"));
				route.setPlazas(rs.getInt("plazas"));
				route.setPlazasOcupadas(rs.getInt("plazasOcupadas"));
				route.setOrigen(rs.getString("origen"));
				route.setDetalles(rs.getString("detalles"));
				route.setPrecio(rs.getDouble("precio"));
				route.setFechaPublicacion(rs.getDate("fechaPublicacion"));
				route.setOpcion(rs.getInt("opcion"));

			}
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
		return route;
	}
}
