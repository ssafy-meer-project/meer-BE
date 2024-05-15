package com.meer.controller;

import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ChatController {
	private final ChatClient chatClient;
	
	public ChatController(final ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@GetMapping("/generate")
	public Map generate(@RequestParam(value="message", defaultValue =
	"tell me a dad joke") String message){
		return Map.of("generation", chatClient.call(message));
	}
	
	@PostMapping("/call")
	public String call(@RequestBody String message) {
		return chatClient.call(message);
	}
	
	@GetMapping("/simple-prompt")
	public String simple() {
		return chatClient.call(
				new Prompt("what is the spring?"))
				.getResult().getOutput().getContent();
				
	}
	
}
