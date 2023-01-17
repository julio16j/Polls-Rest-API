package com.poll.model.dto;

import java.time.LocalDateTime;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.DateUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollDto {
	
	private Long id;

	private String subject;

	private LocalDateTime expirationDateTime;

	private StatusPollEnum status;

	public PollDto(Poll poll) {
		id = poll.getId();
		subject = poll.getSubject();
		expirationDateTime = DateUtils.fromInstant(poll.getExpirationDateTime());
		status = poll.getStatus();
	}
}
