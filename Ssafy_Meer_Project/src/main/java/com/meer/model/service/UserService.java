package com.meer.model.service;

import com.meer.model.dto.ChangePassword;
import com.meer.model.dto.Condition;
import com.meer.model.dto.User;

public interface UserService {

	public boolean writeUser(User user);
	
	public User readUserById(String userId);
	
	public User readUserByNickname(String userNickname);
	
	public int doRandomNumber();
	
	public User login(User user);

	public Condition readConditionById(String userId);

	public int modifyMissionCondition(Condition condition);
	
	public boolean readFortuneCheck(String userId);
	
	public int modifyFortuneCheck(String userId);
	
	public int resetFortuneCheck();
	
	public User findPasswordById(String userId);
	
	public int modifyPassword(ChangePassword changePassword);
	
	
}
