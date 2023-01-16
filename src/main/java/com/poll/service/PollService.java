package com.poll.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poll.exception.BadRequestException;
import com.poll.exception.NotFoundException;
import com.poll.model.dto.CreatePollDto;
import com.poll.model.dto.LocalDateTimeDto;
import com.poll.model.dto.PollDto;
import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;
import com.poll.repository.PollRepository;

import utils.DateUtils;

@Service
public class PollService {
	
	@Autowired
	private PollRepository repository;
	
	public List<PollDto> getAll() {
		return repository.findAll().stream().map(PollDto::new).toList();
	}

	public PollDto create(CreatePollDto poll) {
		return new PollDto(repository.save(poll.toEntity()));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public PollDto openPoll(Long id, LocalDateTimeDto dateTime) {
		Poll pollFounded = findPollById(id);
		if (!pollFounded.getStatus().equals(StatusPollEnum.CREATED)) {
			throw new BadRequestException("Unable to open this poll");
		}
		if (dateTime == null || dateTime.getDate() == null ) {
			Instant instant = Instant.now().plusSeconds(60);
			pollFounded.setExpirationDateTime(instant);
		} else {
			if (dateTime.getDate().isBefore(LocalDateTime.now())) {
				throw new BadRequestException("Invalid Datetime");
			} Instant instant = DateUtils.fromLocalDateTime(dateTime.getDate());
			pollFounded.setExpirationDateTime(instant);
		}
		pollFounded.setStatus(StatusPollEnum.ONGOING);
		return new PollDto(repository.save(pollFounded));
	}
	
	public Poll findPollById (Long id) {
		Optional<Poll> pollFounded = repository.findById(id);
		if (pollFounded.isEmpty()) {
			throw new NotFoundException("Poll");
		} return pollFounded.get();
	}
	
	public Poll updatePoll (Poll poll) {
		if (!repository.existsById(poll.getId())) {
			throw new BadRequestException("You cannot update a not created poll");
		} return repository.save(poll);
	}

}
