package com.university.model;

public class Parking {

	private int id;
	private String codigo;
	private int idParking;
	private int plazas;
	private double superficie;
	private double lon;
	private double lat;

	public Parking() {
	}

	public Parking(int id, String codigo, int idParking, int plazas, double superficie, double lon, double lat) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.idParking = idParking;
		this.plazas = plazas;
		this.superficie = superficie;
		this.lon = lon;
		this.lat = lat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getIdParking() {
		return idParking;
	}

	public void setIdParking(int idParking) {
		this.idParking = idParking;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
