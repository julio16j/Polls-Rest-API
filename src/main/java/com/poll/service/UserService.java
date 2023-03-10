package com.poll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poll.exception.NotFoundException;
import com.poll.model.dto.UserDto;
import com.poll.model.entity.User;
import com.poll.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> getAll() {
		return repository.findAll();
	}

	public User create(UserDto user) {
		return repository.save(user.toEntity());
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public User findUserById (Long id) {
		Optional<User> userFounded = repository.findById(id);
		if (userFounded.isEmpty()) {
			throw new NotFoundException("User");
		} return userFounded.get();
	}

}
