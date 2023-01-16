package com.poll.model.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PollDto {
	
	private Long id;

	private String subject;

	private LocalDateTime expirationDateTime;

	private StatusPollEnum status;

	public PollDto(Poll poll) {
		id = poll.getId();
		subject = poll.getSubject();
		expirationDateTime = poll.getExpirationDateTime() == null ? null
				: LocalDateTime.ofInstant(poll.getExpirationDateTime(), ZoneId.systemDefault());
		status = poll.getStatus();
	}
}
