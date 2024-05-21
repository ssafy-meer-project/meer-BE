package com.meer.model.dao;

import java.util.List;
import java.util.Map;

import com.meer.model.dto.Mission;
import com.meer.model.dto.User;

public interface MissionDao {
	
	public int insertMission(Mission mission);
	
	public List<Mission> selectMission(String userId);
	
	public Mission selectMissionById(Map<String, String> map);
	
	public int deleteMission(String userId);

	public int updateMissionById(Mission mission);

	public int updateMissionCheck(Mission mission);
	
	public int resetMissionCheck();
	
	public int updateChangeCount(User user);
	
	public int resetChangeCount();
}
