package com.meer.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "MyPage DTO")
public class MyPage {
	
	private String userNickname;
	private List<Integer> calendar;
	private boolean fortuneCheck;
	private String fortuneWord;	
	
}
