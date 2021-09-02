package com.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.models.*;
import com.request.response.*;


public class OfferedRidePersistence {
	
	private static ConcurrentHashMap<Long, OfferedRide> offeredRides = new ConcurrentHashMap<>(); 
	private static ConcurrentHashMap<String, List<Long>> userRidesMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,List<OfferedRide>>  sourceDestOfferedRides = new ConcurrentHashMap<>(); 
	
	
	public static OfferRideResponse storeRide(OfferRideRequest offerRide) {
		OfferRideResponse response = new OfferRideResponse();
		
		OfferedRide offerRideObj = new OfferedRide(offerRide.username, offerRide.vehicleId, offerRide.source, offerRide.destination, offerRide.noOfSeats, offerRide.scheduledAt);
		
		if(offeredRides.putIfAbsent(offerRideObj.id,offerRideObj) == null) {
		
			if(userRidesMap.containsKey(offerRideObj.username))
				userRidesMap.get(offerRideObj.username).add(offerRideObj.id);
			else {
				List<Long> offeredRideIdList = new ArrayList<>();
				offeredRideIdList.add(offerRideObj.id);
				userRidesMap.put(offerRideObj.username,offeredRideIdList);
			}
			
			if(sourceDestOfferedRides.containsKey(offerRide.source+"-"+offerRide.destination))
				sourceDestOfferedRides.get(offerRide.source+"-"+offerRide.destination).add(offerRideObj);
			else {
				List<OfferedRide> offeredRideIdList = new ArrayList<>();
				offeredRideIdList.add(offerRideObj);
				sourceDestOfferedRides.put(offerRideObj.username,offeredRideIdList);
			}
		}
		response.offerId = offerRideObj.id;
		response.status = "success";
		response.reason = "ride offered";
		
		return response;
	}
	
	public static boolean updateRide(OfferedRide offeredRide) {
		offeredRides.put(offeredRide.id, offeredRide);
		sourceDestOfferedRides.get(offeredRide.source+"-"+offeredRide.destination).forEach( ride -> {
			if(ride.id == offeredRide.id) {
				ride.noOfSeatsOccupied = offeredRide.noOfSeatsOccupied;
				ride.noOfSeatsOnOffer = offeredRide.noOfSeatsOnOffer;
			}
		});
		return true;
	}
	
	public static OfferedRide getRideById(Long rideId) {
		return offeredRides.getOrDefault(rideId,null);
	}
	
	public static List<OfferedRide> getRideByUser(String username) {
		List<OfferedRide> offeredRidesRes = new ArrayList<>();
		List<Long> ids = userRidesMap.getOrDefault(username, new ArrayList<>());
		for(Long id : ids) {
			offeredRidesRes.add(offeredRides.get(id));
		}
		return offeredRidesRes;
	}
	
	public static List<OfferedRide> getRides(){
		return (List<OfferedRide>) offeredRides.values();
	}
	
	public static List<OfferedRide> getRides(String source, String destination, int noOfSeatsNeeded){
		return (List<OfferedRide>) sourceDestOfferedRides.getOrDefault(source+"-"+destination, new ArrayList<>())
									.stream().filter(ride ->
													ride.noOfSeatsOnOffer - ride.noOfSeatsOccupied >= noOfSeatsNeeded && ride.status != OfferedRide.Status.COMPLETED)
									.collect(Collectors.toList());
	}
	public static int getTotalOfferedRides() {
		return offeredRides.size();
	}
}
