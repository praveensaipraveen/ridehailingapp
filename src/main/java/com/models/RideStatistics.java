package com.models;

import java.util.concurrent.ConcurrentHashMap;

public class RideStatistics {
	private static ConcurrentHashMap<String,Long> userOfferedRidesCount = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,Long> userOptedRidesCount = new ConcurrentHashMap<>();
	
	public static boolean addOfferedRide(String username) {
		userOfferedRidesCount.put(username, userOfferedRidesCount.getOrDefault(username, Long.valueOf(0) ) +1);
		return true;
	}
	
	public static boolean addOptedRide(String username) {
		userOptedRidesCount.put(username, userOptedRidesCount.getOrDefault(username, Long.valueOf(0) ) +1);
		return true;
	}
	
	public static Long[] getStats(String username) {
		Long[] stats = new Long[2];
		stats[0] = userOfferedRidesCount.getOrDefault(username, Long.valueOf(0));
		stats[1] = userOptedRidesCount.getOrDefault(username, Long.valueOf(0));
		return stats;
	}
}
