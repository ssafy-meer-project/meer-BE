package com.meer.model.dto;

public class Mission {
	private String userId;
	private String id;
	private String title;
	private String content;
	private boolean check;
	
	public Mission(String userId, String id, String title, String content, boolean check) {
		this.userId = userId;
		this.id = id;
		this.title = title;
		this.content = content;
		this.check = check;
	}

	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
