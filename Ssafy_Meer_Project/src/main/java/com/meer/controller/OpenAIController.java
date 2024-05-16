package com.meer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.meer.model.dto.ChatRequest;
import com.meer.model.dto.ChatResponse;
import com.meer.model.dto.Condition;
import com.meer.model.dto.Mission;
import com.meer.model.service.MissionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api-chat")
@Tag(name = "OpenAIController", description = "OpenAI info")
public class OpenAIController {

//	private MissionService missionService;
//
//	@Qualifier("openaiRestTemplate")
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Value("${openai.model}")
//	private String model;
//
//	@Value("${openai.api.url}")
//	private String apiURL;
//
//	@Autowired
//	private RestTemplate template;
//
////	@GetMapping("/chat")
////	public String chat(@RequestParam(name = "prompt") String prompt) {
////		ChatRequest request = new ChatRequest(model, prompt);
////		ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
////		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
////			return "No response";
////		}
////		return response.getChoices().get(0).getMessage().getContent();
////	}
//
//	
//	// 미션 만들기
//	@PostMapping("/chat")
//	public ResponseEntity<?> makeMission(@RequestBody Condition condition) {
//		String prompt = condition.getSubject() + "을 개선시킬 데일리 미션을 5개 만들려고 해. " + "세부조건은 다음과 같아. 1. 행동양식은 "
//				+ condition.getCondition1() + "이야. 2. 미션을 하는 공간은 " + condition.getCondition2() + "이야. 3. 미션을 하는 시간대는 "
//				+ condition.getCondition3()
//				+ "이야. 미션마다 첫번 째 줄에는 미션번호를 숫자로 적어줘(ex) 1,2,3,4,5) 두번째 줄에는 미션제목을 한 줄 적어줘(ex)따뜻한 우유 마시기), 다음 줄에는 미션에 대한 구체적인 행동양식을 한 줄로 정리해줘.(ex)자기 전 따뜻한 우유 150ml를 마시기.) 미션과 미션 사이는 한 줄 띄어줘. 다른 미사여구는 다 빼고 미션에 대한 내용만 담아줘";
//
//		ChatRequest request = new ChatRequest(model, prompt);
//		ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
//		String result = response.getChoices().get(0).getMessage().getContent();
//
//		StringTokenizer st = new StringTokenizer(result, "\n");
//
//		List<Mission> list = new ArrayList<>();
//		for (int i = 0; i < 5; i++) {
//			Mission mission = new Mission(condition.getUserId(), st.nextToken(), st.nextToken(), st.nextToken(), false);
//			list.add(mission);
//		}
//
//		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
//			return new ResponseEntity<>("미션생성에 실패하였습니다", HttpStatus.BAD_GATEWAY);
//		}
//
//		return new ResponseEntity<List<Mission>>(list, HttpStatus.OK);
//	}
}