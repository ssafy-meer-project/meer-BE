package com.meer.model.service;

import org.springframework.stereotype.Service;

import com.meer.model.dao.WordDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WordServiceImpl implements WordService {

	private final WordDao wordDao;
	
	@Override
	public String readFortune(String userId) {		
		return wordDao.selectFortune(userId);
	}

	@Override
	public String readSentence(String userId) {		
		return wordDao.selectSentence(userId);
	}

}
