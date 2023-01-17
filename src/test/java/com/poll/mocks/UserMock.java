package com.poll.mocks;

import java.util.ArrayList;
import java.util.List;

import com.poll.model.entity.User;

public class UserMock {
	
	public static List<User> mockUserList () {
		List<User> users = new ArrayList<>();
		users.add(mockUser(1l));
		users.add(mockUser(2l));
		return users;
	}
	
	public static User mockUser (Long id) {
		return new User(id, "user mocked");
	}
	
}
