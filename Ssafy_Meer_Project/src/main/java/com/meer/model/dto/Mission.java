package com.meer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mission DTO")
public class Mission {
	private String userId;
	private String missionId;
	private String missionTitle;
	private String missionContent;
	private boolean missionCheck;

	public Mission(String userId, String missionId, String missionTitle, String missionContent, boolean missionCheck) {
		this.userId = userId;
		this.missionId = missionId;
		this.missionTitle = missionTitle;
		this.missionContent = missionContent;
		this.missionCheck = missionCheck;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getMissionTitle() {
		return missionTitle;
	}

	public void setMissionTitle(String missionTitle) {
		this.missionTitle = missionTitle;
	}

	public String getMissionContent() {
		return missionContent;
	}

	public void setMissionContent(String missionContent) {
		this.missionContent = missionContent;
	}

	public boolean isMissionCheck() {
		return missionCheck;
	}

	public void setMissionCheck(boolean missionCheck) {
		this.missionCheck = missionCheck;
	}	
}