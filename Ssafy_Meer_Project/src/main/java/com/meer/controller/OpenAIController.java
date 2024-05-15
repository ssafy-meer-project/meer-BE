package com.meer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.meer.model.dto.ChatRequest;
import com.meer.model.dto.ChatResponse;
import com.meer.model.dto.Condition;

@RestController
public class OpenAIController {
	
	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;
	
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt")String prompt){
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response =  template.postForObject(apiURL, request, ChatResponse.class);
        if(response==null || response.getChoices() == null || response.getChoices().isEmpty()) {
        	return "No response";
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
    
    @PostMapping("/chat")
    public String postChat(@RequestBody Condition condition) {
    	String prompt = condition.getSubject()+"을 개선시킬 미션을 5개 만들려고 해. "
    			+ "세부조건은 다음과 같아. 1. 행동양식은 "+condition.getCondition1()
    			+"이야. 2. 미션을 하는 공간은 "+condition.getCondition2()
    			+"이야. 3. 미션을 하는 시간대는 "+condition.getCondition3()
    			+"이야. 미션을 한 문장으로 정리해주고, 그 미션에 대한 세부적인 행동양식을 아래에다 코멘트로 달아줘.";
    	
    	ChatRequest request = new ChatRequest(model, prompt);
    	ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
    	if(response==null || response.getChoices() == null || response.getChoices().isEmpty()) {
    		return "No response";
    	}
        return response.getChoices().get(0).getMessage().getContent();
    }
    

}
