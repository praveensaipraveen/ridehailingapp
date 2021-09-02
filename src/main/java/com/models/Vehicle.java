package com.models;

public class Vehicle {
	public long id;
	public String username;
	public String registrationNumber;
	public int noOfSeatsAvailable;
	public String brand;
	
	public Vehicle(String username, String brand, String registrationNumber, int noOfSeats) {
		this.username = username;
		this.brand = brand;
		this.registrationNumber = registrationNumber;
		this.noOfSeatsAvailable = noOfSeats;
	}
}
