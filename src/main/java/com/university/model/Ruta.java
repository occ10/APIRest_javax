package com.university.model;

import java.util.Date;
public class Ruta {

	
	private int id;
	private int plazas;
	private int plazasOcupadas;
	private String origen;
	private String detalles;
	private double precio;
	private java.sql.Date fechaPublicacion;
	private int opcion;
	private User user;
	
	public Ruta(){}
	public Ruta(int id, int plazas, int plazasOcupadas, String origen, String detalles, double precio,
			java.sql.Date fechaPublicacion, int opcion, User user) {
		super();
		this.id = id;
		this.plazas = plazas;
		this.plazasOcupadas = plazasOcupadas;
		this.origen = origen;
		this.detalles = detalles;
		this.precio = precio;
		this.fechaPublicacion = fechaPublicacion;
		this.opcion = opcion;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlazas() {
		return plazas;
	}
	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	public int getPlazasOcupadas() {
		return plazasOcupadas;
	}
	public void setPlazasOcupadas(int plazasOcupadas) {
		this.plazasOcupadas = plazasOcupadas;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public java.sql.Date getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(java.sql.Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public int getOpcion() {
		return opcion;
	}
	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
