package com.meer.model.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ChatRequest DTO")
public class ChatRequest {

    private String model;
    private List<Message> messages;
    private int n=1;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;        
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

    // getters and setters
}

