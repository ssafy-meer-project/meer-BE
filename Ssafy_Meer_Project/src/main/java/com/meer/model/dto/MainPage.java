package com.meer.model.dto;

import java.util.List;

import com.meer.model.dto.ChatResponse.Choice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "MainPage DTO")
public class MainPage {
	private String userId;
	private String missionId;
	private String missionTitle;
	private String missionContent;
	private boolean missionCheck;
	private String sentenceWord;
		
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

	public String getSentenceWord() {
		return sentenceWord;
	}

	public void setSentenceWord(String sentenceWord) {
		this.sentenceWord = sentenceWord;
	}

}