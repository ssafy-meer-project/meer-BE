package com.meer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "chatGpt messgae DTO")
public class Message {

    private String role;
    private String content;
	// public Message(String role, String content) {
	// 	this.role = role;
	// 	this.content = content;
	// }
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

    
    // constructor, getters and setters
}

