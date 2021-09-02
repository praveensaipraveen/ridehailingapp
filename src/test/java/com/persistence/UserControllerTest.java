package com.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.entitymanagers.UserManager;
import com.request.response.AddUserRequest;
import com.request.response.AddUserResponse;


public class UserControllerTest {
	@Mock
	private UserManager userManager;
	
	@BeforeEach
	public void initMocks() {
        userManager = Mockito.mock(UserManager.class);
    }
	
	

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
				
		userManager.addUser(obj);
		
		AddUserRequest objWithExistingUser = new AddUserRequest();
		objWithExistingUser.age = 29;
		objWithExistingUser.sex = "Male";
		objWithExistingUser.username = "praveen";
		AddUserResponse actualResponse = userManager.addUser(objWithExistingUser);
		
		Assert.assertEquals(expectedResponse.username, actualResponse.username);
		Assert.assertEquals(expectedResponse.status, actualResponse.status);
		Assert.assertEquals(expectedResponse.reason, actualResponse.reason);
		
	}

}
