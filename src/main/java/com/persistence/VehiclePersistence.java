package com.persistence;


import java.util.concurrent.ConcurrentHashMap;

import com.models.*;
import com.request.response.*;

public class VehiclePersistence {
	private ConcurrentHashMap<String,Vehicle> vehicleData = new ConcurrentHashMap<>(); // user level vehicle list needs
	
	public AddVehicleResponse addVehicle(AddVehicleRequest vehicle) {
			AddVehicleResponse response = new AddVehicleResponse();
		
			Vehicle vehicleObj = new Vehicle(vehicle.username, vehicle.brand, vehicle.registrationNumber, vehicle.noOfSeats);
			vehicleData.putIfAbsent(vehicleObj.registrationNumber,vehicleObj);
			
			response.vehicleId = vehicleObj.registrationNumber;
			response.reason = "vehicle added";
			response.status = "success";
			
			return response;
	}
	
	public Vehicle getVehicle(long id) {
		return vehicleData.getOrDefault(id,null);
	}
	
	public boolean deleteVehicle(String id) {
		if(vehicleData.remove(id) != null)
			return true;
		else 
			return false;
	}
}
