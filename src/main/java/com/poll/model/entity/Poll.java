package com.poll.model.entity;

import java.time.Instant;

import com.poll.model.enums.StatusPollEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "polls")
public class Poll { 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String subject;
	
	@Enumerated(EnumType.STRING)
	private StatusPollEnum status;
	
	private Instant expirationDateTime;
	
}
