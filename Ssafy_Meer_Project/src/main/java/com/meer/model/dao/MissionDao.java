package com.meer.model.dao;

import java.util.List;
import java.util.Map;

import com.meer.model.dto.Mission;

public interface MissionDao {
	
	public Mission insertMission();
	
	public List<Mission> selectMission(String userId);
	
	public Mission selectMissionById(Map<String, String> map);
	
	public int deleteMission(String userId);
}
