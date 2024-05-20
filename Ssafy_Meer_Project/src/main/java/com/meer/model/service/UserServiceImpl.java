package com.meer.model.service;

import org.springframework.stereotype.Service;

import com.meer.model.dao.UserDao;
import com.meer.model.dto.Condition;
import com.meer.model.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	@Override
	public boolean writeUser(User user) {
		return userDao.insertUser(user)==1;
	}

	@Override
	public User readUserById(String userId) {
		return userDao.selectOneById(userId);
	}

	@Override
	public int doRandomNumber() {
		return userDao.updateRandomNumber();
	}

	@Override
	public User readUserByNickname(String userNickname) {
		return userDao.selectOneByNickname(userNickname);
	}

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public Condition readConditionById(String userId){
		return userDao.selectConditionById(userId);
	}

	@Override
	public int modifyMissionCondition(Condition condition) {
		return userDao.updateMissionCondition(condition);
	}

	@Override
	public boolean readFortuneCheck(String userId) {		
		return userDao.checkFortune(userId);
	}

	@Override
	public User findPasswordById(String userId) {
		return userDao.findPassword(userId);
		
	}
}
