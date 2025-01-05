package com.example.demo.controller;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.watsonx.WatsonxAiChatModel;
import org.springframework.ai.watsonx.WatsonxAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChatAnswer;
import com.example.demo.dto.Question;

@RestController
@RequestMapping("/api/v1")
public class WatsonxController {

	private final ChatModel chat;
	
	private final WatsonxAiEmbeddingModel embedding;
	
	private final String stringTemplate = """
			<|system|>
			You are Granite Chat, an AI language model developed by IBM.
			You are a cautious assistant. You carefully follow instructions.
			You are helpful and harmless and you follow ethical guidelines and promote positive behavior.
			{input}				
			""";
	
	private final PromptTemplate template = new PromptTemplate(stringTemplate);
	
	@Autowired
	public WatsonxController(WatsonxAiChatModel chat, WatsonxAiEmbeddingModel embedding) {
		this.chat = chat;
		this.embedding = embedding;
	}
	
	@GetMapping(value = "/text")
	public ResponseEntity<ChatAnswer> chat(@RequestParam Question question) {
		Prompt prompt = this.template.create(Map.of("input", question.question()));
		ChatResponse genAiResponse = this.chat.call(prompt);
		
		ChatAnswer chatAnswer = ChatAnswer.of(genAiResponse.getResult().getOutput().getContent());
		
		return ResponseEntity.ok(chatAnswer);
	}
	
	
	
}
