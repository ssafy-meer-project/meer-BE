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

import io.swagger.v3.oas.annotations.Operation;
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
	@Operation(summary = "회원가입", description = "유저ID, 유저PW, 유저Nickname을 넘겨주면 유저를 생성하고, 그 유저의 달력레이아웃을 생성함")
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
	@Operation(summary = "ID중복확인", description = "ID를 넘겨줬을 때 기존 db와 비교하여 매칭되는 id가 없으면 true, 있으면 false 반환")
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
	@Operation(summary = "닉네임중복확인", description = "닉네임을 넘겨줬을 때 기존 db와 비교하여 매칭되는 닉네임이 없으면 true, 있으면 false 반환")
	public ResponseEntity<?> checkNickname(@RequestBody Map<String, String> map) {
		String userNickname = map.get("userNickname");
		if (userService.readUserByNickname(userNickname) == null) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

	// 로그인
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "아이디, 비밀번호를 넘겨주면 db와 비교해서 매칭되는 유저가 있을 시 true & access-token을 만들어서 넘겨줌.")
	public ResponseEntity<?> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		
		User tmp = userService.login(user);
		if(tmp.getUserId() != null) {
			result.put("message", "SUCCESS");
			result.put("access-token", jwtUtil.createAccessToken(user.getUserId()));
			result.put("userId", user.getUserId());
			status = HttpStatus.ACCEPTED;
		}else {
			result.put("message", "FAIL");
			status = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<>(result, status);		
	}

	// fortuneNumber, sentenceNumber 새롭게 갱신
	// 매일 밤 12시에 실행되는 메서드
	@Scheduled(cron = "0 0 0 * * ?")
	@Operation(summary = "포츈쿠키, 글귀 문구 번호 리롤", description = "매일 자정 실행되며, 유저들의 포츈쿠키와 글귀의 문구 번호를 랜덤리롤함")
	public void doRandomNumber() {
		System.out.println("FortuneNumber, SentenceNumber 업데이트 되었습니다");
		userService.doRandomNumber();
	}

	// 계정 찾기
	@PostMapping("find-id")
	@Operation(summary = "계정찾기", description = "닉네임을 받아서 db와 비교하여 맞는 유저가 있으면 해당 유저의 ID를 반환. 없으면 404Error")
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
	@Operation(summary = "비밀번호찾기", description = "아이디를 받아서 db와 비교하여 맞는 유저가 있으면 200, 없으면 404")
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
	@Operation(summary = "비밀번호변경(비밀번호찾기)", description = "유저아이디, 새로운 비밀번호를 받아서 새로운 비밀번호로 변경(이 떄는 기존 비밀번호와 같아도 됨)")
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
		int result = userService.modifyPassword(changePassword);
		if(result == 1) {
			return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

}
