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

	private List<Mission> missionList;	
	private String sentenceWord;
	private int changeCount;

	public String getSentenceWord() {
		return sentenceWord;
	}

	public void setSentenceWord(String sentenceWord) {
		this.sentenceWord = sentenceWord;
	}

	public List<Mission> getMissionList() {
		return missionList;
	}

	public void setMissionList(List<Mission> missionList) {
		this.missionList = missionList;
	}

	public int getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}
}