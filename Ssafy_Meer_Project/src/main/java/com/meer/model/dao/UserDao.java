package com.meer.model.dao;

import com.meer.model.dto.User;

public interface UserDao {
	
	public int insertUser(User user);

	public User selectOne(String userId);
}
