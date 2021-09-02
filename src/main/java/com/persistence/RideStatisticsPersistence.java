package com.persistence;

import com.models.RideStatistics;

public class RideStatisticsPersistence {
	public static boolean incrementOfferRide(String username) {
		return RideStatistics.addOfferedRide(username);
	}
	
	public static boolean incrementOptedRide(String username) {
		return RideStatistics.addOptedRide(username);
	}
	
	public static Long[] getStats(String username) {
		return RideStatistics.getStats(username);
	}
}
