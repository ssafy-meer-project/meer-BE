package com.meer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meer.model.dto.ChangePassword;
import com.meer.model.dto.User;
import com.meer.model.service.CalendarService;
import com.meer.model.service.UserService;
import com.meer.util.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Tag(name = "userRestController", description = "user info")
public class UserRestController {

	private final UserService userService;
	private final CalendarService calendarService;
	private final JwtUtil jwtUtil;
	
	// user 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> write(@RequestBody User user) {
		String userId = user.getUserId();
		// user DB에 유저 추가
		userService.writeUser(user);
		// calendar DB에 해당 유저의 이름으로 된 달력 db추가
		calendarService.makeCalendarLayout(userId);		
		return new ResponseEntity<>("가입 성공하였습니다.", HttpStatus.CREATED);
	}

	// ID 중복확인
	// ID가 중복으로 확인되면 409 error를 보냄
	@PostMapping("/id")
	public ResponseEntity<Boolean> checkId(@RequestBody Map<String, String> map) {
		String userId = map.get("userId");
		if (userService.readUserById(userId) == null) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

	// nickname 중복확인
	// nickname이 중복으로 확인되면 409 error를 보냄
	@PostMapping("/nickname")
	public ResponseEntity<?> checkNickname(@RequestBody Map<String, String> map) {
		String userNickname = map.get("userNickname");
		if (userService.readUserByNickname(userNickname) == null) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		
		User tmp = userService.login(user);
		if(tmp.getUserId() != null) {
			result.put("message", "SUCCESS");
			result.put("access-token", jwtUtil.createToken(user.getUserId()));
			result.put("userId", user.getUserId());
			status = HttpStatus.ACCEPTED;
		}else {
			result.put("message", "FAIL");
			status = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<>(result, status);		
	}
	
	@PostMapping("/test")
	public ResponseEntity<?> test(HttpServletRequest request, @RequestBody User user){
		String token = request.getHeader("access-token");
		String userId = jwtUtil.validate(token);		
		System.out.println(userId);
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	// fortuneNumber, sentenceNumber 새롭게 갱신
	// 매일 밤 12시에 실행되는 메서드
	@Scheduled(cron = "0 0 0 * * ?")
	public void doRandomNumber() {
		System.out.println("FortuneNumber, SentenceNumber 업데이트 되었습니다");
		userService.doRandomNumber();
	}

	// 계정 찾기
	@PostMapping("find-id")
	public ResponseEntity<?> findUser(@RequestBody Map<String, String> map) {
		String userNickname = map.get("userNickname");
		System.out.println(userNickname);
		User user = userService.readUserByNickname(userNickname);
		if (user == null) {
			return new ResponseEntity<>("닉네임을 다시 확인해주세요.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user.getUserId(), HttpStatus.OK);
	}

	// 비밀번호 찾기
	@PostMapping("find-pw")
	public ResponseEntity<?> findPassword(@RequestBody Map<String, String> map) {
		String userId = map.get("userId");
		User user = userService.findPasswordById(userId);
		if (user == null) {
			return new ResponseEntity<>("해당하는 아이디가 없습니다", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 비밀번호 변경
	@PutMapping("change-pw")
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
		int result = userService.modifyPassword(changePassword);
		if(result == 1) {
			return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

}
