package com.meer.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
	
	private String message;

    private List<Choice> choices;
    
    // public ChatResponse() {};

    public ChatResponse(List<Choice> choices) {
		this.choices = choices;
	}

    public ChatResponse(String message) {
    	this.message = message;
    }
    
	// constructors, getters and setters
    
    public List<Choice> getChoices() {
		return choices;
	}


	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Choice {

        private int index;
        private Message message;
		// public Choice(int index, Message message) {
		// 	this.index = index;
		// 	this.message = message;
		// }
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public Message getMessage() {
			return message;
		}
		public void setMessage(Message message) {
			this.message = message;
		}

        
        // constructors, getters and setters
    }
}

