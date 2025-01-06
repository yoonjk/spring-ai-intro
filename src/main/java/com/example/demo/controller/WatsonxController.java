package com.example.demo.controller;

import java.util.List;

import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.watsonx.WatsonxAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Question;

@RestController
@RequestMapping("/api/v1")
public class WatsonxController {
	private final WatsonxAiEmbeddingModel embeddingModel;

	
	@Autowired
	public WatsonxController(WatsonxAiEmbeddingModel embeddingModel) {

		this.embeddingModel = embeddingModel;
	}
	
	@PostMapping("/ai/embedding")
	public ResponseEntity<Embedding>  embedding(@RequestBody Question question) {
		EmbeddingResponse response = this.embeddingModel.embedForResponse(List.of(question.getMessage()));
		
		return ResponseEntity.ok(response.getResult());
	}
	
	
	
}
