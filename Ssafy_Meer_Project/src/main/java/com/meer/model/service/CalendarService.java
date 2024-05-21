package com.meer.model.service;

import java.util.List;

public interface CalendarService {
	
	public void makeCalendarLayout(String userId);	
	
	public void modifyCalendar(int day);
	
	public void resetCalendar();
	
	public List<Integer> readCalendar(String userId);
}
