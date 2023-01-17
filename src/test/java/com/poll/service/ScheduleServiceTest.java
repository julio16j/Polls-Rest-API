package com.poll.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.poll.mocks.PollMock;

@SpringBootTest
public class ScheduleServiceTest {
	
	@Mock
	private PollService pollService;
	
	@InjectMocks
	private ScheduleService scheduleService;
	
	@Test
	public void shouldUpdateExpiredPolls () {
		when(pollService.getAllExpiredPolls()).thenReturn(PollMock.mockPollList());
		scheduleService.closeExpiredOngoinPolls();
	}
}
