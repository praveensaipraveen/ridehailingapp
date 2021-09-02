package com.models;

import java.util.Date;


public class OfferedRide {
	public long id;
	public String username;
	public long vehicleId;
	public String source;
	public String destination;
	public int noOfSeatsOnOffer;
	public Date scheduledAt;
	public int noOfSeatsOccupied;
	public Status status;
	
	public enum Status  {PENDING, ONGOING, COMPLETED}; 
	public OfferedRide(String username, long vehicleId, String source, String destination, int noOfSeatsOnOffer, Date scheduledAt) {
		this.username = username;
		this.vehicleId = vehicleId;
		this.source = source;
		this.destination = destination;
		this.noOfSeatsOnOffer = noOfSeatsOnOffer;
		this.noOfSeatsOccupied = noOfSeatsOnOffer;
		this.scheduledAt = scheduledAt;
		this.status = Status.PENDING;
		this.id = new Date().hashCode();
	}
}
