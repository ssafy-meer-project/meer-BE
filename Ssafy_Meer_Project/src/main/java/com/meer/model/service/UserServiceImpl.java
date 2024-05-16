package com.meer.model.service;

import org.springframework.stereotype.Service;

import com.meer.model.dao.UserDao;
import com.meer.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean writeUser(User user) {
		return userDao.insertUser(user)==1;
	}

	@Override
	public User readUserById(String userId) {
		return userDao.selectOne(userId);
	}

	@Override
	public int modifyFotuneNumber() {
		return userDao.updateFortuneNumber();
	}

	@Override
	public User readUserByNickname(String userNickname) {
		return userDao.selectOneByNickname(userNickname);
	}

	@Override
	public User login(User user) {
		return userDao.login(user);
	}
	
	public User readUser(String userId) {
		return userDao.selectOne(userId);
	}
}
