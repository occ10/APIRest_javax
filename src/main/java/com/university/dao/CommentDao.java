package com.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.university.model.Comment;
import com.university.model.UserDTO;

import Exception.ServiceException;

public class CommentDao {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Error primero aqui");
		}
	}

	public static CommentDao getInstance() {
		return new CommentDao();
	}
	
	public List<Comment> getCometnsUerCommented(String email) throws ServiceException {

		List<Comment> comments = new ArrayList<Comment>();
		Comment comment;
		UserDTO user;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(select * from comenta c,usuario u where c.usuarioComentado = '" + email + "' and c.usuarioComenta=u.correo)";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
						
				comment = new Comment();
				user = new UserDTO();
				
				comment.setUserCommented(rs.getString("usuarioComentado"));
				comment.setUserComment(rs.getString("usuarioComenta"));
				comment.setComment(rs.getString("comentario"));				
				user.setCorreo(rs.getString("correo"));
				user.setNombre(rs.getString("nombre"));
				user.setApellido(rs.getString("apellido"));
				user.setEdad(rs.getInt("edad"));
				user.setTelefono(rs.getString("telefono"));
				user.setFoto(rs.getString("foto"));
				comment.setUser(user);
				comments.add(comment);
			}
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());

		}
		return comments;
	}
	
	public List<Comment> getCometnsUerComment(String email) throws ServiceException {

		List<Comment> comments = new ArrayList<Comment>();
		Comment comment;
		UserDTO user;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeebd", "root", "");
			String sql = "(select * from comenta c, usuario u where usuarioComenta = '" + email + "' and c.usuarioComentado=u.correo)";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
						
				comment = new Comment();
				user = new UserDTO();
				
				comment.setUserCommented(rs.getString("usuarioComentado"));
				comment.setUserComment(rs.getString("usuarioComenta"));
				comment.setComment(rs.getString("comentario"));				
				user.setCorreo(rs.getString("correo"));
				user.setNombre(rs.getString("nombre"));
				user.setApellido(rs.getString("apellido"));
				user.setEdad(rs.getInt("edad"));
				user.setTelefono(rs.getString("telefono"));
				user.setFoto(rs.getString("foto"));
				comment.setUser(user);
				comments.add(comment);
			}
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());

		}
		return comments;
	}

}
