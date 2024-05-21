package com.meer.model.dao;

public interface WordDao {
	public String selectFortune(String userId);

	public String selectSentence(String userId);
}
