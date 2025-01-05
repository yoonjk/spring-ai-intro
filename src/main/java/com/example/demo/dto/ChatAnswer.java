package com.example.demo.dto;

public record ChatAnswer(String answer) {
	public static ChatAnswer of(String answer) {
		return new ChatAnswer(answer);
	}
}
