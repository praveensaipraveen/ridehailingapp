package com.persistence;



import org.junit.*;

import com.request.response.AddUserRequest;
import com.request.response.AddUserResponse;


public class UserPersistenceTest {
	
	
	@Test
	public void TestAddUserWhenUserAlreadyExists() {
		AddUserRequest obj = new AddUserRequest();
		obj.age = 29;
		obj.sex = "Male";
		obj.username = "praveen";
		
		AddUserResponse expectedResponse = new AddUserResponse();
		expectedResponse.username = null;
		expectedResponse.status = "failed";
		expectedResponse.reason = "username not available";
		
		UserPersistence userPersistence = new UserPersistence();
		AddUserResponse receivedResponse0 = userPersistence.addUser(obj);
		AddUserResponse receivedResponse = userPersistence.addUser(obj);
		
		// Mockito
		
		Assert.assertEquals(expectedResponse.username, receivedResponse.username);
		Assert.assertEquals(expectedResponse.status, receivedResponse.status);
		Assert.assertEquals(expectedResponse.reason, receivedResponse.reason);
		
	}
	
	
	@Test
	public void TestAddUserWithNewUser() {
		AddUserRequest obj = new AddUserRequest();
		obj.age = 29;
		obj.sex = "Male";
		obj.username = "praveen";
		
		AddUserResponse expectedResponse = new AddUserResponse();
		expectedResponse.username = "praveen";
		expectedResponse.status = "success";
		expectedResponse.reason = "user added";
		
		UserPersistence userPersistence = new UserPersistence();
		AddUserResponse receivedResponse = userPersistence.addUser(obj);
	
		
		Assert.assertEquals(expectedResponse.username, receivedResponse.username);
		Assert.assertEquals(expectedResponse.status, receivedResponse.status);
		Assert.assertEquals(expectedResponse.reason, receivedResponse.reason);
		
	}

}
