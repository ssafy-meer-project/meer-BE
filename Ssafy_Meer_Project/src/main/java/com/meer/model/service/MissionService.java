package com.meer.model.service;

import java.util.List;

import com.meer.model.dto.Mission;

public interface MissionService {
	
	public Mission makeMissionLayout();
	
	public List<Mission> getMission(String userId);

	public Mission getMissionById(String userId, String id);
	
	public boolean removeMission(String userId);
	
	
}
