package com.poll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poll.model.dto.PollResultsDto;
import com.poll.model.dto.VoteDto;
import com.poll.model.entity.Vote;
import com.poll.service.VoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("votes")
public class VoteController {
	
	@Autowired
	private VoteService service;

	@GetMapping("{pollId}")
	private ResponseEntity<PollResultsDto> getPollResults (@PathVariable Long pollId) {
		return ResponseEntity.ok(service.getResultsFromPoll(pollId));
	}
	
	@PostMapping
	private ResponseEntity<Vote> voteInPoll (@RequestBody @Valid VoteDto vote) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.voteInPoll(vote));
	}
}
