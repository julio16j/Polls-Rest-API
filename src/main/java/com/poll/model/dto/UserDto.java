package com.poll.model.dto;

import org.hibernate.validator.constraints.Length;

import com.poll.model.entity.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	@NotEmpty
	@Length(min = 2)
	private String name;

	public User toEntity() {
		User user = new User ();
		user.setName(name);
		return user;
	}

}
