package com.meer.model.service;

import com.meer.model.dto.User;

public interface UserService {

	public boolean writeUser(User user);
	
	public User readUserById(String userId);
	
	public User readUserByNickname(String userNickname);
	
	public int modifyFotuneNumber();
	
	public User login(User user);
}
