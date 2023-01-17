package com.poll.mocks;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

public class PollMock {
	
	public static List<Poll> mockPollList () {
		List<Poll> polls = new ArrayList<>();
		polls.add(mockPoll(1l));
		polls.add(mockPoll(2l));
		return polls;
	}
	
	public static Poll mockPoll (Long id) {
		return new Poll(id, "poll mocked", StatusPollEnum.CREATED, Instant.now());
	}
	
}
