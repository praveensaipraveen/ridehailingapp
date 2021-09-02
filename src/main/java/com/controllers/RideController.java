package com.controllers;

import com.request.response.EndRideRequest;
import com.request.response.EndRideResponse;
import com.request.response.GetStatisticsResponse;
import com.request.response.OfferRideRequest;
import com.request.response.OfferRideResponse;
import com.request.response.SearchRideRequest;
import com.request.response.SearchRideResponse;
import com.entitymanagers.*;
import com.persistence.RideStatisticsPersistence;

public class RideController {
	UserManager userManager = new UserManager(); 
	RideManager rideManager = new RideManager();
	public OfferRideResponse offerRide(OfferRideRequest offerRide, OfferRideResponse response) {
		try {
			response = rideManager.offerRide(offerRide);
		}catch(Exception e) {
			response.offerId = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public SearchRideResponse searchRide(SearchRideRequest searchRide, SearchRideResponse response) {
		try {
			response = rideManager.searchRide(searchRide);
		}catch(Exception e) {
			response.bookingId = -1;
			response.ridesAvailable = null;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public EndRideResponse endRide(EndRideRequest endRide, EndRideResponse response) {
		try {
			response = rideManager.endRide(endRide);
		}catch(Exception e) {
			response.referenceId = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public EndRideResponse cancelRide(EndRideRequest endRide, EndRideResponse response) {
		try {
			response = rideManager.cancelRide(endRide);
		}catch(Exception e) {
			response.referenceId = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public GetStatisticsResponse getStats() {
		GetStatisticsResponse response = new GetStatisticsResponse();
		try {
			response.noOfUsers = userManager.getUsersCount();
			response.ridesOfferedPerUser = rideManager.getOfferedRides()/response.noOfUsers;
			response.ridesOptedPerUser = rideManager.getOptedRides()/response.noOfUsers;
		}catch(Exception e) {
			response.noOfUsers = -1;
			response.ridesOfferedPerUser = -1;
			response.ridesOptedPerUser = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public GetStatisticsResponse getStats(String username) {
		GetStatisticsResponse response = new GetStatisticsResponse();
		try {
			response.noOfUsers = 1;
			Long[] stats = RideStatisticsPersistence.getStats(username);
			response.ridesOfferedPerUser =  stats[0].intValue();
			response.ridesOptedPerUser = stats[1].intValue();
		}catch(Exception e) {
			response.noOfUsers = -1;
			response.ridesOfferedPerUser = -1;
			response.ridesOptedPerUser = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
}
