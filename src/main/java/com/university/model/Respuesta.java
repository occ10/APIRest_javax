package com.university.model;

public class Respuesta {

	private String clave;
	private String valor;

	public Respuesta(String clave, String valor) {
		super();
		this.clave = clave;
		this.valor = valor;
	}

	public Respuesta() {
		super();
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
