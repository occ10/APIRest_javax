package com.university.model;

public class Announcement {
	
	private Route route;
	//private UserDTO user;
	//private Car car;
	
	public Announcement() {}
	
	public Announcement(Route route) {
		super();
		this.route = route;
		//this.user = user;
		//this.car = car;
	}
	
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}

}
