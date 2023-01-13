package com.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.model.entity.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

}
