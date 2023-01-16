package com.poll.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteDto {
	
	@NotNull
	private Long pollId;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Boolean shouldPass;

}
