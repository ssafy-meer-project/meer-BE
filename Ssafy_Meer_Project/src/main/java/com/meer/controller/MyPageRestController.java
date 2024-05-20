package com.meer.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meer.model.dto.MyPage;
import com.meer.model.service.CalendarService;
import com.meer.model.service.UserService;
import com.meer.model.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mypage")
@Tag(name = "MypageRestController", description = "달력 정보")
public class MyPageRestController {

	LocalDate now = LocalDate.now();
	private final CalendarService calendarService;
	private final WordService wordService;
	private final UserService userService;

	// 전날의 달력 data를 갱신해줌
	@Scheduled(cron = "0 0 0 * * ?")
	@Operation(summary = "달력 갱신", description = "매일 밤 12시에 실행되며, 전날 완료한 최종 미션 갯수를 업데이트 함")
	public void updateCalendar() {
		int day = now.getDayOfMonth();
		// 1일이 아니면 전 일 날짜를 업데이트 해줍니다.
		// 오늘이 1일이라면 그냥 다 초기화 된 상태의 백지를 출력할 것이므로 day1의 값을 출력함.
		if (day != 1) {
			day -= 1;
		}
		calendarService.modifyCalendar(day);
	}

	// 월이 바뀌면서 달력 초기화
	@Scheduled(cron = "0 0 0 1 * ?")
	@Operation(summary = "달력 초기화", description = "매월 1일 자정에 실행되며, 모든 유저의 달력을 0으로 초기화함.")
	public void resetCalendar() {
		calendarService.resetCalendar();
	}

	@GetMapping("")
	@Operation(summary = "Mypage가 로드됐을 때 보여지는 정보", description = "mypage를 접속하는 순간 실행됨. calendar, fotuneCheck, fortuneWord값이 넘겨짐")
	public ResponseEntity<?> getCalendar(@RequestParam("userId") String userId) {
		MyPage mypage = new MyPage();
		List<Integer> list = new ArrayList<>();
		List<Integer> calendar = new ArrayList<>();
		// day1과 인덱스를 맞추기 위해 0번 인덱스에 더미데이터 넣기
		calendar.add(0);

		// day1 ~ day30 까지의 배열을 받아옴.
		list = calendarService.readCalendar(userId);

		// 조회하고자 하는 현재 달을 불러옴.
		int month = now.getMonthValue();
		// 해당 월의 말일 날짜에 맞게 데이터를 보내준다.
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			for (int i = 0; i <= 30; i++) {
				calendar.add(list.get(i));
			}
		} else if (month == 2) {
			for (int i = 0; i <= 27; i++) {
				calendar.add(list.get(i));
			}
		} else {
			for (int i = 0; i <= 29; i++) {
				calendar.add(list.get(i));
			}
		}

		Boolean fortuneCheck = userService.readFortuneCheck(userId);
		String fortuneWord = wordService.readFortune(userId);
		
		mypage.setCalendar(calendar);
		mypage.setFortuneCheck(fortuneCheck);
		mypage.setFortuneWord(fortuneWord);

		return new ResponseEntity<MyPage>(mypage, HttpStatus.OK);
	}	
}
