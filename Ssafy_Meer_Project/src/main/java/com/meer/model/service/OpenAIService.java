package com.meer.model.service;

public interface OpenAIService {
    String getResponse(String prompt, String apiKey);
    
    String generatePicture(String prompt);
}




