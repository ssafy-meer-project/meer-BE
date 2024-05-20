package com.meer.model.service;

import java.util.List;

import com.meer.model.dto.MainPage;

public interface MissionService {
	
	public int makeMission(MainPage mainPage);
	
	public List<MainPage> getMission(String userId);

	public MainPage getMissionById(String userId, String id);
	
	public boolean removeMission(String userId);
	
	public int modifyMissionById(MainPage mainPage);

	public int resetMissionCheck();
	
}
