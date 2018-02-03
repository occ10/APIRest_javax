package com.university.model;

import java.io.Serializable;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L; 
	   private String correo;
	   private String nombre; private String apellido;
	   private int edad;
	   private String telefono;
	   private String foto;
	   private String detalles;
	   private String Confirmado ;
	   private int opcion;
	   
		public UserDTO(User user){		
			super();
			  this.correo = user.getCorreo();
			  this.nombre = user.getNombre();
			  this.apellido  = user.getApellido();
			  this.edad = user.getEdad();
			  this.telefono = user.getTelefono();
			  this.foto = user.getFoto();
			  this.detalles = user.getDetalles();
			  this.Confirmado = user.getConfirmado() ;
			  this.opcion = user.getOpcion();		
		}
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getConfirmado() {
		return Confirmado;
	}

	public void setConfirmado(String confirmado) {
		Confirmado = confirmado;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
