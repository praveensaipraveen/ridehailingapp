package com.entitymanagers;

import com.persistence.*;
import com.request.response.*;

import com.models.*;

public class UserManager {
	private UserPersistence userPersistence = new UserPersistence();
	public AddUserResponse addUser(AddUserRequest request) {
		return userPersistence.addUser(request);
	}
	
	public User getUser(String username) {
		return userPersistence.getUser(username);
	}
	
	public int getUsersCount() {
		return userPersistence.getUsersCount();
	}
}
