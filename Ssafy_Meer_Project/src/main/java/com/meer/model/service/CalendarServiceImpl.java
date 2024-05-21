package com.meer.model.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.meer.model.dao.CalendarDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarServiceImpl implements CalendarService{

	private final CalendarDao calendarDao;
	
	@Override
	public void makeCalendarLayout(String userId) {
		calendarDao.insertCalendarLayout(userId);		
	}

	@Override
	public void modifyCalendar(int day) {
		calendarDao.updateCalendar(day);		
	}

	@Override
	public void resetCalendar() {
		calendarDao.resetCalendar();
		
	}

	@Override
	public List<Integer> readCalendar(String userId) {
		Map<String, Integer> map = calendarDao.selectCalendar(userId);
		return IntStream.rangeClosed(1, 31).mapToObj(day -> map.getOrDefault("day"+day, 0))
				.collect(Collectors.toList());
	}

}
