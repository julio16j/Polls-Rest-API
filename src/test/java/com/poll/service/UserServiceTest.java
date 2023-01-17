package com.poll.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.poll.exception.NotFoundException;
import com.poll.mocks.LongMock;
import com.poll.mocks.UserMock;
import com.poll.model.dto.UserDto;
import com.poll.model.entity.User;
import com.poll.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void shouldGetUserList() {
		List<User> mockUserList = UserMock.mockUserList();
		when(userRepository.findAll()).thenReturn(mockUserList);
		List<User> serviceUserList = userService.getAll();
		assertEquals(mockUserList.size(), serviceUserList.size());
	}
	
	@Test
	public void shouldCreateUser() {
		UserDto userDto = new UserDto("user mocked");
		when(userRepository.save(any(User.class))).thenReturn(UserMock.mockUser(LongMock.defaultLong));
		assertEquals(userDto.getName(), userService.create(userDto).getName());
	}
	
	@Test
	public void shouldDeleteUserById() {
		userService.delete(LongMock.defaultLong);
	}
	
	@Test
	public void shouldFindUserById () {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserMock.mockUser(LongMock.defaultLong)));
		User user = userService.findUserById(LongMock.defaultLong);
		assertEquals(user, UserMock.mockUser(LongMock.defaultLong));
	}
	
	@Test
	public void shouldNotFindUserById () {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> userService.findUserById(LongMock.defaultLong));
	}
}
