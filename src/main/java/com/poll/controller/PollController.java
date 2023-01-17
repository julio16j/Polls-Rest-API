package com.poll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poll.model.dto.CreatePollDto;
import com.poll.model.dto.LocalDateTimeDto;
import com.poll.model.dto.PollDto;
import com.poll.service.PollService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("polls")
@Tag(name = "Poll Controller")
public class PollController {
	
	@Autowired
	private PollService service;
	
	@GetMapping
	private ResponseEntity<List<PollDto>> getAllPolls () {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PostMapping
	private ResponseEntity<PollDto> createPoll (@RequestBody @Valid CreatePollDto poll) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(poll));
	}
	
	@Operation(summary = "Open poll by poll id and an optional dateTime")
	@PostMapping("{id}")
	private ResponseEntity<PollDto> openPoll (@PathVariable Long id, @RequestBody(required = false) LocalDateTimeDto dateTime) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.openPoll(id, dateTime));
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity<?> deletePoll (@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
