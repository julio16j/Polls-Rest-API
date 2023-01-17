package com.poll.model.dto;

import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePollDto {
	
	@NotBlank
	private String subject;
	
	public Poll toEntity () {
		Poll poll = new Poll();
		poll.setSubject(subject);
		poll.setStatus(StatusPollEnum.CREATED);
		return poll;
	}

}
