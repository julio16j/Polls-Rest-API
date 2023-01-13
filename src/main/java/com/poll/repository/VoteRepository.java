package com.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.model.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
