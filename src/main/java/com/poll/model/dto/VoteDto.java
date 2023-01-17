package com.poll.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	
	@NotNull
	private Long pollId;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Boolean shouldPass;

}
