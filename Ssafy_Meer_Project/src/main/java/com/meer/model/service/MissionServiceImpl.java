package com.meer.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.meer.model.dao.MissionDao;
import com.meer.model.dto.Mission;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MissionServiceImpl implements MissionService{
	
	private MissionDao missionDao;
	
	@Override
	public Mission makeMissionLayout() {
		missionDao.insertMission();
		return null;
	}

	@Override
	public List<Mission> getMission(String userId) {		
		return missionDao.selectMission(userId);
	}

	@Override
	public Mission getMissionById(String userId, String id) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("id", id);		
		return missionDao.selectMissionById(map);
	}

	@Override
	public boolean removeMission(String userId) {		
		return missionDao.deleteMission(userId)==1;
	}
}
