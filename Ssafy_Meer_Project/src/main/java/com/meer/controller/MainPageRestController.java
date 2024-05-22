package com.meer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.meer.model.dto.ChatRequest;
import com.meer.model.dto.ChatResponse;
import com.meer.model.dto.Condition;
import com.meer.model.dto.MainPage;
import com.meer.model.dto.Mission;
import com.meer.model.dto.User;
import com.meer.model.service.MissionService;
import com.meer.model.service.UserService;
import com.meer.model.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mainpage")
@Tag(name = "MainpageRestController", description = "미션정보")
public class MainPageRestController {

	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.api.url}")
	private String apiURL;

	@Autowired
	private RestTemplate template;

	private final MissionService missionService;
	private final UserService userService;
	private final WordService wordService;

	// 메인페이지 접속	
	@GetMapping("")
	@Operation(summary = "메인페이지 로드API", description = "유저ID를 받아서 미션목록과 글귀를 반환함")
	public ResponseEntity<?> enterMainPage(@RequestParam("userId") String userId) {
		MainPage mainPage = new MainPage();

		User user = userService.readUserById(userId);
		int changeCount = user.getChangeCount();

		mainPage.setChangeCount(changeCount);
		mainPage.setMissionList(missionService.getMission(userId));
		mainPage.setSentenceWord(wordService.readSentence(userId));
//		if (mainPage.getMissionList() == null || mainPage.getMissionList().size() < 0) {
//			return new ResponseEntity<>("미션이 없습니다.", HttpStatus.NO_CONTENT);
//		}
		
		return new ResponseEntity<MainPage>(mainPage, HttpStatus.OK);
	}

	// 미션 만들기
	@PostMapping("/mission")
	@Operation(summary = "미션을 새로 만드는 API", description = "subject에는 주제가, condition1에는 행동양식, condition2에는 행동장소, condition3에는 행동시간을 넣으면 생성한 미션 리스트 반환")
	public ResponseEntity<?> makeMission(@RequestBody Condition condition) {
		String userId = condition.getUserId(); 

		// 기존에 미션이 있으면은 기존 미션은 다 지우기
		List<Mission> checkExistMission = missionService.getMission(userId);
		if(checkExistMission != null) {
			missionService.removeMission(userId);
		}
		
		
		// 프롬프트 작성부
		String prompt = "너는 이제부터 사람들의 고민을 해결해주는 상담사야. 누군가 너에게 상담을 요청해왔어. 이 사람의 " + condition.getSubject()
				+ "을 개선시킬 데일리 미션을 5개 만들려고 해. " + "세부조건은 다음과 같아. 1. 행동양식은 " + condition.getCondition1()
				+ "이야. 2. 미션을 하는 공간은 " + condition.getCondition2() + "이야. 3. 미션을 하는 시간대는 " + condition.getCondition3()
				+ "이야. 미션마다 첫번 째 줄에는 미션번호를 숫자로 적어줘(ex) 1,2,3,4,5) 두번째 줄에는 미션제목을 '~하기'의 형태로 한 줄 적어줘, 다음 줄에는 미션에 대한 구체적인 행동양식을 '~하기'의 형태로 한 줄 정리해줘. "
				+ "미션과 미션 사이는 한 줄 띄어줘. 미션제목이나, 구체적인 행동양식 같은 어구들은 빼고 딱 미션에 대한 내용만 담아줘";
		// gpt명령부
		ChatRequest request = new ChatRequest(model, prompt);
		ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
		String result = response.getChoices().get(0).getMessage().getContent();

		// userDB에 subject와 condition1,2,3을 저장해두는 작업
		userService.modifyMissionCondition(condition);

		// gpt응답을 내가 원하는대로 parsing
		StringTokenizer st = new StringTokenizer(result, "\n");

		// mission DB 에 넣는과정
		List<Mission> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {

			Mission mission = new Mission();
			mission.setUserId(userId);
			mission.setMissionId(st.nextToken().trim());
			mission.setMissionTitle(st.nextToken().trim());
			mission.setMissionContent(st.nextToken().trim());
			mission.setMissionCheck(false);
			missionService.makeMission(mission);
			list.add(mission);
		}

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			return new ResponseEntity<>("미션생성에 실패하였습니다", HttpStatus.BAD_GATEWAY);
		}

		return new ResponseEntity<List<Mission>>(list, HttpStatus.OK);
	}

	// 미션 개별 업데이트
	@PutMapping("/mission")
	@Operation(summary = "미션 개별 변경 메서드", description = "로그인한 유저id와 변경하고자 하는 미션번호를 넘겨주면 기존의 미션과 다른 미션을 반환함")
	public ResponseEntity<?> updateMission(@RequestBody Mission mission) {
		String userId = mission.getUserId();
		String missionId = mission.getMissionId();
		List<Mission> list = new ArrayList<>();
		list = missionService.getMission(userId);
		Condition condition = userService.readConditionById(userId);
		User user = userService.readUserById(userId);

		// 변경 할 때마다 changeCount 하나씩 감소
		int changeCount = user.getChangeCount();
		changeCount--;
		user.setChangeCount(changeCount);
		missionService.modifyChangeCount(user);

		condition.setUserId(userId);
		String prompt = "너는 이제부터 사람들의 고민을 해결해주는 상담사야. 누군가 너에게 상담을 요청해왔어. 이 사람의 " + condition.getSubject()
				+ "을 개선시킬 또 다른 데일리 미션을 1개 만들려고 해. " + "기존의 미션들은 제외하고 다른 미션으로 만들어줘." + "기존의 미션은 \n"
				+ list.get(0).getMissionTitle() + " " + list.get(1).getMissionTitle() + " "
				+ list.get(2).getMissionTitle() + " " + list.get(3).getMissionTitle() + " "
				+ list.get(4).getMissionTitle() + "가 있어." + "데일리 미션을 만드는데 있어 세부조건은 다음과 같아. 1. 행동양식은 "
				+ condition.getCondition1() + "이야. 2. 미션을 하는 공간은 " + condition.getCondition2() + "이야. 3. 미션을 하는 시간대는 "
				+ condition.getCondition3()
				+ "이야. 미션은 한개만 만들면 되고, 첫번 째 줄에는 미션제목을 '~하기'의 형태로 한 줄 적어줘, 다음 줄에는 미션에 대한 구체적인 행동양식을 '~하기'의 형태로 한 줄 정리해줘. 미션과 미션 사이는 한 줄 띄어줘. 미션제목이나, 구체적인 행동양식 같은 어구들은 빼고 딱 미션에 대한 내용만 담아줘";

		ChatRequest request = new ChatRequest(model, prompt);
		ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
		String result = response.getChoices().get(0).getMessage().getContent();

		StringTokenizer st = new StringTokenizer(result, "\n");

		mission = new Mission(condition.getUserId(), missionId, st.nextToken(), st.nextToken(), false);
		missionService.modifyMissionById(mission);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()
				|| missionService.modifyMissionById(mission) == 0) {
			return new ResponseEntity<>("미션생성에 실패하였습니다", HttpStatus.BAD_GATEWAY);
		}

		return new ResponseEntity<Mission>(mission, HttpStatus.OK);
	}

	// 미션다시 만들기(전체 다시 만들기)
	@PostMapping("/mission/allChange")
	@Operation(summary = "미션 전체 다시 만들기", description = "userId를 보내주면 처음 선택한 subejct와 condition에 맞는 다른 미션 5가지를 반환함. 단, 미션을 한번 생성한 후에 실행되어야 함.")
	public ResponseEntity<?> updateAllMission(@RequestBody Map<String, String> map) {
		// front로부터 userid를 받기
		String userId = map.get("userId");
		Condition condition = userService.readConditionById(userId);
		List<Mission> pastMissionList = new ArrayList<>();
		pastMissionList = missionService.getMission(userId);

		// 기존에 가지고 있던 Mission 데이터 삭제
		missionService.removeMission(userId);

		String prompt = "너는 이제부터 사람들의 고민을 해결해주는 상담사야. 누군가 너에게 상담을 요청해왔어. 이 사람의 " + condition.getSubject()
				+ "을 개선시킬 데일리 미션을 5개 만들려고 해. " + "세부조건은 다음과 같아. 1. 행동양식은 " + condition.getCondition1()
				+ "이야. 2. 미션을 하는 공간은 " + condition.getCondition2() + "이야. 3. 미션을 하는 시간대는 " + condition.getCondition3()
				+ "이야. 미션마다 첫번 째 줄에는 미션번호를 숫자로 적어줘(ex) 1,2,3,4,5) 두번째 줄에는 미션제목을 '~하기'의 형태로 한 줄 적어줘, 다음 줄에는 미션에 대한 구체적인 행동양식을 '~하기'의 형태로 한 줄 정리해줘. "
				+ "대신, 다음의 5가지 미션은 빼고 새로운 미션으로 만들어줘야해. 빼야할 5가지 미션은 다음과 같아."
				+ pastMissionList.get(0).getMissionTitle()+", "+pastMissionList.get(1).getMissionTitle()+", "
				+pastMissionList.get(2).getMissionTitle()+", "+pastMissionList.get(3).getMissionTitle()+", "+pastMissionList.get(4).getMissionTitle()
				+ "미션과 미션 사이는 한 줄 띄어줘. 미션제목이나, 구체적인 행동양식 같은 어구들은 빼고 딱 미션에 대한 내용만 담아줘";
		
		// gpt명령부
		ChatRequest request = new ChatRequest(model, prompt);
		ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
		String result = response.getChoices().get(0).getMessage().getContent();

		// userDB에 subject와 condition1,2,3을 저장해두는 작업
		userService.modifyMissionCondition(condition);

		// gpt응답을 내가 원하는대로 parsing
		StringTokenizer st = new StringTokenizer(result, "\n");

		// mission DB 에 넣는과정
		List<Mission> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {

			Mission mission = new Mission();
			mission.setUserId(userId);
			mission.setMissionId(st.nextToken().trim());
			mission.setMissionTitle(st.nextToken().trim());
			mission.setMissionContent(st.nextToken().trim());
			mission.setMissionCheck(false);
			missionService.makeMission(mission);
			list.add(mission);
		}

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			return new ResponseEntity<>("미션생성에 실패하였습니다", HttpStatus.BAD_GATEWAY);
		}

		return new ResponseEntity<List<Mission>>(list, HttpStatus.OK);
	}
	

	// 미션 새로 만들기(새로 만들기 버튼을 누르면 기존 데이터를 삭제함)
	@DeleteMapping("/mission")
	@Operation(summary = "만들어진 미션 전체 삭제", description = "미션을 처음부터 새로 만들기 위해 기존의 미션을 삭제함.")
	public ResponseEntity<?> deleteMission(@RequestParam String userId) {
		// 기존에 미션이 있는지 없는지 탐색 후 있으면 delete 함.
		List<Mission> list = missionService.getMission(userId);
		if (list != null) {
			missionService.removeMission(userId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// 미션 체크 T/F
	@PutMapping("/mission/check")
	@Operation(summary = "미션 T,F 체크", description = "유저 id와 T/F변경하고자 하는 미션ID를 넘겨주면 T일때는 F로, F일때는 T로 변경해줌")
	public ResponseEntity<?> updateMissionCheck(@RequestBody Mission mission) {
		Mission tmp = missionService.getMissionById(mission.getUserId(), mission.getMissionId());
		mission.setMissionCheck(tmp.isMissionCheck());

		int result = missionService.modifyMissionCheck(mission);
		tmp = missionService.getMissionById(mission.getUserId(), mission.getMissionId());
		mission.setMissionCheck(tmp.isMissionCheck());
		boolean check = mission.isMissionCheck();
		if (result >= 1) {
			return new ResponseEntity<>(check, HttpStatus.OK);
		}
		return new ResponseEntity<>("갱신 실패", HttpStatus.BAD_GATEWAY);
	}

	// 매일 자정에 mission Check 모두 false로 변경
	// 달력과 겹치지 않기 위해 매 자정 3초 후에 실행
	@Scheduled(cron = "3 0 0 * * ?")
	@Operation(summary = "미션 T,F 체크를 다 F로 초기화", description = "매일 자정에 모든 유저의 모슨 미션체크 값을 다 false로 초기화함")
	public void doResetMissionCheck() {
		missionService.resetMissionCheck();
		missionService.resetChangeCount();
	}
}
