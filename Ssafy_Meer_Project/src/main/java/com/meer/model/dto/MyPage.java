package com.meer.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "MyPage DTO")
public class MyPage {
	private List<Integer> calendar;
	private boolean fortuneCheck;
	private String fortuneWord;
	
	public List<Integer> getCalendar() {
		return calendar;
	}
	public void setCalendar(List<Integer> calendar) {
		this.calendar = calendar;
	}
	public boolean isFortuneCheck() {
		return fortuneCheck;
	}
	public void setFortuneCheck(boolean fortuneCheck) {
		this.fortuneCheck = fortuneCheck;
	}
	public String getFortuneWord() {
		return fortuneWord;
	}
	public void setFortuneWord(String fortuneWord) {
		this.fortuneWord = fortuneWord;
	}
	
}
