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

import com.poll.model.dto.UserDto;
import com.poll.model.entity.User;
import com.poll.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping
	private ResponseEntity<List<User>> getAllUsers () {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PostMapping
	private ResponseEntity<User> createUser (@RequestBody @Valid UserDto user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(user));
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity<?> deleteUser (@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
