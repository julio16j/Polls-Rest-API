package com.poll.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poll.model.entity.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {
	
	@Query("select poll from Poll poll "
			+ "where poll.status = com.poll.model.enums.StatusPollEnum.ONGOING "
			+ "and poll.expirationDateTime <= :instant")
	List<Poll> getExpiredPollsByInstant(@Param("instant") Instant instant);

}
