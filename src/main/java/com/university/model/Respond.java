package com.university.model;

public class Respond {

	private String clave;
	private String valor;

	public Respond(String clave, String valor) {
		super();
		this.clave = clave;
		this.valor = valor;
	}

	public Respond() {
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
