package com.entitymanagers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.models.AvailableRide;
import com.models.BookedRide;
import com.models.OfferedRide;
import com.persistence.*;
import com.request.response.*;

public class RideManager {
	VehiclePersistence vehiclePersistence = new VehiclePersistence(); 
	public OfferRideResponse offerRide(OfferRideRequest offerRide) {
		OfferRideResponse response = OfferedRidePersistence.storeRide(offerRide);
		if (response.status.equals("success") ) {
			RideStatisticsPersistence.incrementOfferRide(offerRide.username);
		}
		return response;
	}
	
	public SearchRideResponse searchRide(SearchRideRequest searchRide) {
		SearchRideResponse response = new SearchRideResponse();
		try {
				List<AvailableRide> availableRides = new ArrayList<>();
				List<OfferedRide> offerRides = OfferedRidePersistence.getRides(searchRide.source, searchRide.destination, searchRide.noOfSeatsNeeded);
				
				for(OfferedRide ride : offerRides) {
						AvailableRide availableRide = new AvailableRide();
						
						if( !searchRide.preferenceBrand.equals("") 
								&& searchRide.preferenceBrand == vehiclePersistence.getVehicle(ride.vehicleId).brand ) {
							availableRide.Brand = vehiclePersistence.getVehicle(ride.vehicleId).brand;
							availableRide.noOfSeats = ride.noOfSeatsOnOffer - ride.noOfSeatsOccupied;
							availableRide.offerId = ride.id;
							availableRides.add(availableRide);
						}else { //if(searchRide.preferenceSeatNo != 0) 
							availableRide.Brand = vehiclePersistence.getVehicle(ride.vehicleId).brand;
							availableRide.noOfSeats = ride.noOfSeatsOnOffer - ride.noOfSeatsOccupied;
							availableRide.offerId = ride.id;
							availableRides.add(availableRide);
						}
				}
				
				Collections.sort(availableRides, (AvailableRide a, AvailableRide b) -> {
													return    (b.noOfSeats) - (a.noOfSeats); 
												}
				);
				response.ridesAvailable = availableRides;
				
				OfferedRide offeredRide = OfferedRidePersistence.getRideById(availableRides.get(0).offerId);
				offeredRide.noOfSeatsOnOffer -= searchRide.noOfSeatsNeeded;
				OfferedRidePersistence.updateRide(offeredRide);
				
				BookedRide bookedRide = new BookedRide(searchRide.username,availableRides.get(0).offerId, availableRides.get(0).noOfSeats);
				
				response.bookingId = BookedRidePersistence.storeBookedRide(bookedRide);
				if(response.bookingId != -1) {
					RideStatisticsPersistence.incrementOptedRide(searchRide.username);
				}
		}catch(Exception e) {
			response.bookingId = -1;
			response.ridesAvailable = null;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public EndRideResponse endRide(EndRideRequest endRide) {
		EndRideResponse response = new EndRideResponse();
		try {
			BookedRide bookedRide = BookedRidePersistence.getBookedRide(endRide.bookingId);
			bookedRide.status = BookedRide.Status.COMPLETED;
			BookedRidePersistence.updateBookedRide(bookedRide);
			
			OfferedRide offeredRide = OfferedRidePersistence.getRideById(bookedRide.offeredRideId);
			offeredRide.status = OfferedRide.Status.COMPLETED;
			
			response.status = "success";
			response.reason = "ride ended";
			response.referenceId = endRide.bookingId;
		}catch(Exception e) {
			response.referenceId = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public EndRideResponse cancelRide(EndRideRequest endRide) {
		EndRideResponse response = new EndRideResponse();
		try {
			BookedRide bookedRide = BookedRidePersistence.getBookedRide(endRide.bookingId);
			bookedRide.status = BookedRide.Status.CANCELLED;
			BookedRidePersistence.updateBookedRide(bookedRide);
			
			OfferedRide offeredRide = OfferedRidePersistence.getRideById(bookedRide.offeredRideId);
			offeredRide.noOfSeatsOccupied -= BookedRidePersistence.getBookedRide(endRide.bookingId).noOfSeatsBooked;
			
			response.status = "success";
			response.reason = "ride cancelled";
			response.referenceId = endRide.bookingId;
		}catch(Exception e) {
			response.referenceId = -1;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public int getOfferedRides() {
		return OfferedRidePersistence.getTotalOfferedRides();
	}
	
	public int getOptedRides() {
		return BookedRidePersistence.getTotalBookedRides();
	}
	
	public Long[] getStats(String username) {
		return RideStatisticsPersistence.getStats(username);
	}
}
