package com.entitymanagers;

import com.persistence.*;
import com.request.response.AddVehicleRequest;
import com.request.response.AddVehicleResponse;
import com.models.*;

public class VehicleManager {
	VehiclePersistence vehiclePersistence = new VehiclePersistence();
	public AddVehicleResponse addVehicle(AddVehicleRequest vehicle) {
			return vehiclePersistence.addVehicle(vehicle);
	}
	
	public Vehicle getVehicle(int id) {
		return vehiclePersistence.getVehicle(id);
	}
	
	public boolean deleteVehicle(String id) {
		return vehiclePersistence.deleteVehicle(id);
	}
}
