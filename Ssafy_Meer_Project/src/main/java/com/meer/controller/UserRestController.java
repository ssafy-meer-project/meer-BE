package com.meer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meer.model.dto.User;
import com.meer.model.service.CalendarService;
import com.meer.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api-user")
@Tag(name = "userRestController", description = "user info")
public class UserRestController {

	private final UserService userService;
	private final CalendarService calendarService;

	// user 회원가입	
	@PostMapping("/user/signup")
	public ResponseEntity<?> write(@RequestBody User user) {
		String userId = user.getUserId();
		// user DB에 유저 추가
		userService.writeUser(user);
		// calendar DB에 해당 유저의 이름으로 된 달력 db추가
		calendarService.makeCalendarLayout(userId);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	// ID 중복확인
	// ID가 중복으로 확인되면 409 error를 보냄
	@PostMapping("/user/id")
	public ResponseEntity<Boolean> checkId(@RequestBody User user){
		if(userService.readUserById(user.getUserId())==null) {
			return ResponseEntity.ok(true);			
		}
		return ResponseEntity.ok(false);
	}
	
	// nickname 중복확인
	// nickname이 중복으로 확인되면 409 error를 보냄
	@PostMapping("/user/nickname")
	public ResponseEntity<?> checkNickname(@RequestBody User user){
		if(userService.readUserByNickname(user.getUserNickname())==null) {
			return ResponseEntity.ok(true);			
		}
		return ResponseEntity.ok(false);
	}
	
	//로그인
	@PostMapping("/user")
	public ResponseEntity<?> login(@RequestBody User user){
		User result = userService.login(user);
		if(result!=null) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}
	
	//
	
//	@GetMapping("/user/{id}")
//	public ResponseEntity<?> getMethodName(@PathVariable("id") String userId) {
//		User user = userService.readUser(userId);
//		if (user == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}

	// fortuneNumber, sentenceNumber 새롭게 갱신
	// 매일 밤 12시에 실행되는 메서드
	@Scheduled(cron = "0 0 0 * * ?")
	public void doRandomNumber() {
		System.out.println("FortuneNumber, SentenceNumber 업데이트 되었습니다");
        userService.doRandomNumber();
	}
	
	
}
