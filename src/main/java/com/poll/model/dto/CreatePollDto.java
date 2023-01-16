package com.poll.model.dto;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePollDto {
	
	@NotBlank
	private String subject;
	
	public CreatePollDto (Poll poll) {
		subject = poll.getSubject();
	}
	
	public Poll toEntity () {
		Poll poll = new Poll();
		poll.setSubject(subject);
		poll.setStatus(StatusPollEnum.CREATED);
		return poll;
	}

}
