package com.poll.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.poll.model.enums.StatusPollEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PollResultsDto implements Serializable {
	
	private static final long serialVersionUID = 7935393097325003557L;
	
	private String subject;
	private StatusPollEnum status;
	private LocalDateTime expirationDateTime;
	private Integer votes;
	private Integer yesVotes;
	private Integer noVotes;
	private Boolean yesWinning;
	
	
}
