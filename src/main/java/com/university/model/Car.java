package com.university.model;

public class Car {
	
	private String registration;
	private String model;
	private String color;
	private int seating;//acientos
	private String user;
	private String brand;//marca
	private String category;
	private String image;
	
	public Car(){};
	
	public Car(String registration, String model, String color, int seating, String user, String brand,
			String category, String image) {
		super();
		this.registration = registration;
		this.model = model;
		this.color = color;
		this.seating = seating;
		this.user = user;
		this.brand = brand;
		this.category = category;
		this.image = image;
	}
	
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getSeating() {
		return seating;
	}
	public void setSeating(int seating) {
		this.seating = seating;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}


}
