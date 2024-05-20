package com.meer.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.meer.model.dao.MissionDao;
import com.meer.model.dto.MainPage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MissionServiceImpl implements MissionService{
	
	private final MissionDao missionDao;
	
	@Override
	public int makeMission(MainPage mainPage) {		
		return missionDao.insertMission(mainPage);
		
		
	}

	@Override
	public List<MainPage> getMission(String userId) {		
		return missionDao.selectMission(userId);
	}

	@Override
	public MainPage getMissionById(String userId, String id) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("id", id);		
		return missionDao.selectMissionById(map);
	}

	@Override
	public boolean removeMission(String userId) {		
		return missionDao.deleteMission(userId)==1;
	}

	@Override
	public int modifyMissionById(MainPage mainPage) {
		return missionDao.updateMissionById(mainPage);
	}

	@Override
	public int resetMissionCheck(){
		return missionDao.resetMissionCheck();
	}
}
