package com.meer.model.service;

import com.meer.model.dto.User;

public interface UserService {

	public boolean writeUser(User user);
	
	public User readUser(String userId);
}
