package com.meer.model.dto;

import java.util.List;

import com.meer.model.dto.ChatResponse.Choice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User DTO")
public class User {
	private String userId;
	private String userPassword;
	private String userNickname;
	private String fortuneNumber;
	private String sentenceNumber;
	private boolean fortuneCheck;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getFortuneNumber() {
		return fortuneNumber;
	}

	public void setFortuneNumber(String fortuneNumber) {
		this.fortuneNumber = fortuneNumber;
	}

	public String getSentenceNumber() {
		return sentenceNumber;
	}

	public void setSentenceNumber(String sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}

	public boolean isFortuneCheck() {
		return fortuneCheck;
	}

	public void setFortuneCheck(boolean fortuneCheck) {
		this.fortuneCheck = fortuneCheck;
	}
	
	
	
}
