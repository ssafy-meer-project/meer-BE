package com.meer.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.meer.model.dao.MissionDao;
import com.meer.model.dto.Mission;
import com.meer.model.dto.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MissionServiceImpl implements MissionService{
	
	private final MissionDao missionDao;
	
	@Override
	public int makeMission(Mission mission) {		
		return missionDao.insertMission(mission);
		
		
	}

	@Override
	public List<Mission> getMission(String userId) {		
		return missionDao.selectMission(userId);
	}

	@Override
	public Mission getMissionById(String userId, String missionId) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("missionId", missionId);		
		return missionDao.selectMissionById(map);
	}

	@Override
	public boolean removeMission(String userId) {		
		return missionDao.deleteMission(userId)==1;
	}

	@Override
	public int modifyMissionById(Mission mission) {
		return missionDao.updateMissionById(mission);
	}

	@Override
	public int resetMissionCheck(){
		return missionDao.resetMissionCheck();
	}

	@Override
	public int modifyMissionCheck(Mission mission) {		
		return missionDao.updateMissionCheck(mission);
	}

	@Override
	public int modifyChangeCount(User user) {		
		return missionDao.updateChangeCount(user);
	}

	@Override
	public int resetChangeCount() {
		return missionDao.resetChangeCount();
	}
}
