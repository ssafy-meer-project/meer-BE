package com.meer.model.dto;

public class Condition {
	private String userId;
	private String id;
	private String Subject;
	private String Condition1;
	private String Condition2;
	private String Condition3;
	
	public Condition(String userId, String id, String subject, String condition1, String condition2, String condition3) {
		this.userId = userId;
		this.id = id;
		this.Subject = subject;
		this.Condition1 = condition1;
		this.Condition2 = condition2;
		this.Condition3 = condition3;
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Condition() {}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getCondition1() {
		return Condition1;
	}
	public void setCondition1(String condition1) {
		Condition1 = condition1;
	}
	public String getCondition2() {
		return Condition2;
	}
	public void setCondition2(String condition2) {
		Condition2 = condition2;
	}
	public String getCondition3() {
		return Condition3;
	}
	public void setCondition3(String condition3) {
		Condition3 = condition3;
	}
	
	
}
