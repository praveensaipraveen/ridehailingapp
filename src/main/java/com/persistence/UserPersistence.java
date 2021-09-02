package com.persistence;


import java.util.concurrent.ConcurrentHashMap;

import com.models.*;
import com.request.response.*;

public class UserPersistence {
	private static ConcurrentHashMap<String,User> usersData = new ConcurrentHashMap<>();
	
	public AddUserResponse  addUser(AddUserRequest user) {
		AddUserResponse response = new AddUserResponse();
		try {
			if(usersData.containsKey(user.username)) {
				response.username = null;
				response.status = "failed";
				response.reason = "username not available";
			}else {
				usersData.put(user.username, new User(user.username, user.sex, user.age));
				response.username = user.username;
				response.status = "success";
				response.reason = "user added";
			}
		}catch (Exception e) {
			response.username = null;
			response.status = "failed";
			response.reason = e.getMessage();
		}
		return response;
	}
	
	public User getUser(String username) {
		return usersData.getOrDefault(username, null);
	}
	public int getUsersCount() {
		return usersData.size();
	}
}
