package com.controllers;

import com.request.response.AddUserRequest;
import com.request.response.AddUserResponse;
import com.request.response.AddVehicleRequest;
import com.request.response.AddVehicleResponse;
import com.entitymanagers.*;
public class RegistrationController {
		
		UserManager userManager = new UserManager(); 
		VehicleManager vehicleManager = new VehicleManager();
		
		public AddUserResponse addUser(AddUserRequest user,AddUserResponse response) {
			try {
				response  = userManager.addUser(user);
			}catch (Exception e) {
				response.username = null;
				response.status = "failed";
				response.reason = e.getMessage();
			}
			return response;
		}
		
		public AddVehicleResponse addVehicle(AddVehicleRequest vehicle,AddVehicleResponse response) {
			try {
				response = vehicleManager.addVehicle(vehicle);
			}catch(Exception e) {
				response.vehicleId = null;
				response.status = "failed";
				response.reason = e.getMessage();
			}
			return response;
		}
}
