package com.meer.model.dao;

import java.util.Map;

public interface CalendarDao {
	public void insertCalendarLayout(String userId);
	
	public void updateCalendar(int day);
	
	public void resetCalendar();
	
	public Map<String, Integer> selectCalendar(String userId);
}
