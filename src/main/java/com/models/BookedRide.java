package com.models;

import java.util.Date;


public class BookedRide {
	public long id;
	public long offeredRideId;
	public String username;
	public int noOfSeatsBooked;
	public Status status;
	
	public static enum Status  {PENDING, ONGOING, COMPLETED, CANCELLED};
	public BookedRide(String username, long offeredRideId, int noOfSeatsBooked) {
		this.offeredRideId = offeredRideId;
		this.username = username;
		this.noOfSeatsBooked = noOfSeatsBooked;
		this.status = Status.PENDING;
		this.id = new Date().hashCode();
	}
}
