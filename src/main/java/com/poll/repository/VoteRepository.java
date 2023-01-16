package com.poll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poll.model.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	@Query("select vote from Vote vote where vote.poll.id = :pollId")
	List<Vote> getVotesByPollId(@Param("pollId") Long pollId);
	
	@Query("select vote from Vote vote where vote.poll.id = :pollId and vote.user.id = :userId")
	List<Vote> getVotesByPollUser(@Param("pollId") Long pollId, @Param("userId") Long userId);

}
