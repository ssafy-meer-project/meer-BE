package com.meer.model.dao;

import com.meer.model.dto.ChangePassword;
import com.meer.model.dto.Condition;
import com.meer.model.dto.User;

public interface UserDao {
	
	public int insertUser(User user);

	public User selectOneById(String userId);

	public User selectOneByNickname(String userNickname);
	
	public int updateRandomNumber();

	public User login(User user);

	public Condition selectConditionById(String userId);

	public int updateMissionCondition(Condition condition);
	
	public boolean checkFortune(String userId);
	
	public int updateFortuneCheck(String userId);
	
	public int resetFortuneCheck();
	
	public User findPassword(String userId);
	
	public int updatePassword(ChangePassword changePassword);
}
