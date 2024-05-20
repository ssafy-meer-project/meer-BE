package com.meer.model.dao;

import java.util.List;
import java.util.Map;

import com.meer.model.dto.MainPage;

public interface MissionDao {
	
	public int insertMission(MainPage mainpage);
	
	public List<MainPage> selectMission(String userId);
	
	public MainPage selectMissionById(Map<String, String> map);
	
	public int deleteMission(String userId);

	public int updateMissionById(MainPage mainPage);

	public int resetMissionCheck();
}
