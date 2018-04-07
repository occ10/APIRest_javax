package com.university.model;

public class Zone {

	private int id;
	private double lon;
	private double lat;
	private String aparcamiento;
	private int ocupada;

	public Zone() {
	};

	public Zone(int id, double lon, double lat, String aparcamiento, int ocupada) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.aparcamiento = aparcamiento;
		this.ocupada = ocupada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAparcamiento() {
		return aparcamiento;
	}

	public void setAparcamiento(String aparcamiento) {
		this.aparcamiento = aparcamiento;
	}

	public int getOcupada() {
		return ocupada;
	}

	public void setOcupada(int ocupada) {
		this.ocupada = ocupada;
	}

}
