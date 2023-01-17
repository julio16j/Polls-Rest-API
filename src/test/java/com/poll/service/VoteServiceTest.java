package com.poll.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.poll.exception.BadRequestException;
import com.poll.mocks.LongMock;
import com.poll.mocks.PollMock;
import com.poll.mocks.UserMock;
import com.poll.model.dto.PollResultsDto;
import com.poll.model.dto.VoteDto;
import com.poll.model.entity.Poll;
import com.poll.model.entity.Vote;
import com.poll.model.enums.StatusPollEnum;
import com.poll.repository.VoteRepository;

import utils.DateUtils;

@SpringBootTest
public class VoteServiceTest {

	@Mock
	private PollService pollService;

	@Mock
	private UserService userService;

	@Mock
	private VoteRepository voteRepository;

	@InjectMocks
	private VoteService voteService;

	@Test
	public void shouldGetResultsFromCreatedPoll() {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		PollResultsDto mockedResults = initializeEmptyResultsDto(mockedPoll);
		mockedResults.setYesWinning(false);
		when(pollService.findPollById(LongMock.defaultLong)).thenReturn(mockedPoll);
		assertEquals(mockedResults, voteService.getResultsFromPoll(LongMock.defaultLong));

	}

	@Test
	public void shouldGetResultsFromNonCreatedPoll() {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		mockedPoll.setStatus(StatusPollEnum.ONGOING);
		PollResultsDto mockedResults = initializeEmptyResultsDto(mockedPoll);
		mockedResults.setYesWinning(false);
		mockedResults.setNoVotes(1);
		mockedResults.setYesVotes(1);
		mockedResults.setVotes(2);
		when(pollService.findPollById(LongMock.defaultLong)).thenReturn(mockedPoll);
		when(voteRepository.getVotesByPollId(anyLong())).thenReturn(mockVoteList());
		assertEquals(mockedResults, voteService.getResultsFromPoll(LongMock.defaultLong));

	}

	@Test
	public void shouldNotValidateCreatedPoll () {
		when(pollService.findPollById(anyLong())).thenReturn(PollMock.mockPoll(LongMock.defaultLong));
		when(userService.findUserById(anyLong())).thenReturn(UserMock.mockUser(LongMock.defaultLong));
		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> voteService.voteInPoll(mockVoteDto(LongMock.defaultLong)));
	}

	@Test
	public void shouldNotValidatePollByDateTime() {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		mockedPoll.setExpirationDateTime(Instant.now().minusSeconds(2000));
		mockedPoll.setStatus(StatusPollEnum.ONGOING);
		when(pollService.findPollById(anyLong())).thenReturn(mockedPoll);
		when(userService.findUserById(anyLong())).thenReturn(UserMock.mockUser(LongMock.defaultLong));
		assertThatExceptionOfType(BadRequestException.class)
				.isThrownBy(() -> voteService.voteInPoll(mockVoteDto(LongMock.defaultLong)));
	}

	@Test
	public void shouldNotValidateUser() {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		mockedPoll.setStatus(StatusPollEnum.ONGOING);
		mockedPoll.setExpirationDateTime(Instant.now().plusSeconds(1000));
		when(pollService.findPollById(anyLong())).thenReturn(mockedPoll);
		when(userService.findUserById(anyLong())).thenReturn(UserMock.mockUser(LongMock.defaultLong));
		when(voteRepository.getVotesByPollUser(anyLong(), anyLong())).thenReturn(mockVoteList());
		assertThatExceptionOfType(BadRequestException.class)
				.isThrownBy(() -> voteService.voteInPoll(mockVoteDto(LongMock.defaultLong)));
	}

	@Test
	public void shouldVoteInPoll() {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		Vote mockedVote = mockVote(LongMock.defaultLong, LongMock.defaultLong);
		mockedPoll.setStatus(StatusPollEnum.ONGOING);
		mockedPoll.setExpirationDateTime(Instant.now().plusSeconds(1000));
		when(pollService.findPollById(anyLong())).thenReturn(mockedPoll);
		when(userService.findUserById(anyLong())).thenReturn(UserMock.mockUser(LongMock.defaultLong));
		when(voteRepository.getVotesByPollUser(anyLong(), anyLong())).thenReturn(List.of());
		when(voteRepository.save(any(Vote.class))).thenReturn(mockedVote);
		assertEquals(mockedVote, voteService.voteInPoll(mockVoteDto(LongMock.defaultLong)));
	}

	private PollResultsDto initializeEmptyResultsDto(Poll mockedPoll) {
		PollResultsDto results = new PollResultsDto();
		results.setSubject(mockedPoll.getSubject());
		results.setStatus(mockedPoll.getStatus());
		results.setExpirationDateTime(DateUtils.fromInstant(mockedPoll.getExpirationDateTime()));
		results.setVotes(0);
		results.setYesVotes(0);
		results.setNoVotes(0);
		return results;
	}

	private List<Vote> mockVoteList() {
		List<Vote> votes = new ArrayList<>();
		Vote yesMockVote = mockVote(1l, 1l);
		yesMockVote.setShouldPass(true);
		votes.add(yesMockVote);
		votes.add(mockVote(LongMock.defaultLong, LongMock.defaultLong));
		return votes;
	}

	private Vote mockVote(Long voteId, Long userId) {
		return new Vote(voteId, PollMock.mockPoll(LongMock.defaultLong), UserMock.mockUser(userId), false,
				Instant.now());
	}

	private VoteDto mockVoteDto(Long userId) {
		return new VoteDto(LongMock.defaultLong, userId, false);
	}

}
