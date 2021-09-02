package com.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.models.BookedRide;


public class BookedRidePersistence {
		private static Map<String,List<Long>> userBookedRidesMap = new HashMap<>();
		private static Map<Long,BookedRide> bookedRides = new HashMap<>();
		
		public static long storeBookedRide(BookedRide bookedRide) {
			if(bookedRides.putIfAbsent(bookedRide.id, bookedRide) == null){
				if (userBookedRidesMap.containsKey(bookedRide.username)) {
					userBookedRidesMap.get(bookedRide.username).add(bookedRide.id);
					
				}else {
					List<Long> rideIds = new ArrayList<>();
					rideIds.add(bookedRide.id);
					userBookedRidesMap.put(bookedRide.username, rideIds);
				}
			}
			return bookedRide.id;
		}
		
		public static BookedRide getBookedRide(long id) {
				return bookedRides.getOrDefault(id, null);
		}
		
		public static boolean updateBookedRide(BookedRide bookedRide) {
			if(bookedRides.putIfAbsent(bookedRide.id, bookedRide) == null){
				return true;
			}
			return false;
		}
		
		public static int getTotalBookedRides() {
			return bookedRides.size();
		}
}
