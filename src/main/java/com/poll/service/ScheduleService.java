package com.poll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

@EnableAsync
@Component
public class ScheduleService {
	
	@Autowired
	private PollService pollService;
	
	@Scheduled(fixedDelay = 60000)
	public void closeExpiredOngoinPolls () {
		List<Poll> expiredPolls = pollService.getAllExpiredPolls();
		expiredPolls.stream().forEach(poll -> {
			poll.setStatus(StatusPollEnum.CLOSED);
			pollService.updatePoll(poll);
		});
	}
}
