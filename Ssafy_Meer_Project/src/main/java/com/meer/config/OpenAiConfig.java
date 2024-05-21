package com.meer.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.meer.model.service.MissionService;
import com.meer.model.service.UserService;
import com.meer.model.service.WordService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class OpenAiConfig {
    @Value("${openai.api.key}")
    private String openAiKey;
    
    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate template(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openAiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
