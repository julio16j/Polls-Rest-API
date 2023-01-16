package com.poll.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poll.exception.BadRequestException;
import com.poll.model.dto.PollResultsDto;
import com.poll.model.dto.VoteDto;
import com.poll.model.entity.Poll;
import com.poll.model.entity.User;
import com.poll.model.entity.Vote;
import com.poll.model.enums.StatusPollEnum;
import com.poll.repository.VoteRepository;

import utils.DateUtils;

@Service
public class VoteService {
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VoteRepository repository;
	
	public PollResultsDto getResultsFromPoll(Long pollId) {
		Poll pollFounded = pollService.findPollById(pollId);
		PollResultsDto results = initializeEmptyResultsDto(pollFounded);
		if (results.getStatus() != StatusPollEnum.CREATED) {
			List<Vote> votes = repository.getVotesByPollId(pollId);
			modifyResultsFromVotes(results, votes);
		}
		results.setYesWinning(results.getYesVotes() > results.getNoVotes());
		return results;
	}

	public Vote voteInPoll(VoteDto vote) {
		Vote newVote = new Vote();
		Poll pollFounded = pollService.findPollById(vote.getPollId());
		User userFounded = userService.findUserById(vote.getUserId());
		validatePoll(pollFounded);
		validateUser(pollFounded, userFounded);
		newVote.setPoll(pollFounded);
		newVote.setUser(userFounded);
		newVote.setShouldPass(vote.getShouldPass());
		return repository.save(newVote);
	}

	private void validateUser(Poll poll, User user) {
		List<Vote> votesByUser = repository.getVotesByPollUser(poll.getId(), user.getId());
		if (votesByUser.size() > 0) {
			throw new BadRequestException("This user already voted in this poll");
		}
	}

	private void validatePoll(Poll pollFounded) {
		if (pollFounded.getStatus() != StatusPollEnum.ONGOING) {
			throw new BadRequestException("This poll cannot receive votes");
		} if (pollFounded.getExpirationDateTime().isBefore(Instant.now())) {
			pollFounded.setStatus(StatusPollEnum.CLOSED);
			pollService.updatePoll(pollFounded);
			throw new BadRequestException("This poll was closed");
		}
	}
	
	private PollResultsDto initializeEmptyResultsDto(Poll pollFounded) {
		PollResultsDto results = new PollResultsDto ();
		results.setSubject(pollFounded.getSubject());
		results.setStatus(pollFounded.getStatus());
		results.setExpirationDateTime(DateUtils.fromInstant(pollFounded.getExpirationDateTime()));
		results.setVotes(0);
		results.setYesVotes(0);
		results.setNoVotes(0);
		return results;
	}
	
	private void modifyResultsFromVotes (PollResultsDto results, List<Vote> votes) {
		results.setVotes(votes.size());
		for (Vote vote : votes) {
			if (vote.getShouldPass()) {
				results.setYesVotes(results.getYesVotes() + 1);
			} else {
				results.setNoVotes(results.getNoVotes() + 1);
			}
		}
	}

}
