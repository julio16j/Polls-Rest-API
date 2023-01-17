package com.poll.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.poll.exception.BadRequestException;
import com.poll.exception.NotFoundException;
import com.poll.mocks.LongMock;
import com.poll.mocks.PollMock;
import com.poll.model.dto.CreatePollDto;
import com.poll.model.dto.LocalDateTimeDto;
import com.poll.model.dto.PollDto;
import com.poll.model.entity.Poll;
import com.poll.model.enums.StatusPollEnum;
import com.poll.repository.PollRepository;

@SpringBootTest
public class PollServiceTest {
	
	@Mock
	private PollRepository pollRepository;
	
	@InjectMocks
	private PollService pollService;
	
	@Test
	public void shouldGetPollList() {
		List<Poll> mockPollList = PollMock.mockPollList();
		when(pollRepository.findAll()).thenReturn(mockPollList);
		List<PollDto> servicePollList = pollService.getAll();
		assertEquals(mockPollList.size(), servicePollList.size());
		mockPollList.forEach(poll -> {
			assertTrue(servicePollList.contains(new PollDto(poll)));
		});
	}
	
	@Test
	public void shouldCreatePoll () {
		when(pollRepository.save(any(Poll.class))).thenReturn(PollMock.mockPoll(LongMock.defaultLong));
		CreatePollDto poll = new CreatePollDto("poll mocked");
		PollDto pollCreated = pollService.create(poll);
		assertEquals(pollCreated.getStatus(), StatusPollEnum.CREATED);
		assertEquals(pollCreated.getSubject(), poll.getSubject());
	}
	
	@Test
	public void shouldDeletePollById() {
		pollService.delete(LongMock.defaultLong);
	}
	
	@Test
	public void shouldNotOpenWhenNotCreatedStatus () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		LocalDateTimeDto dateTimeDto = new LocalDateTimeDto(LocalDateTime.now());
		mockedPoll.setStatus(StatusPollEnum.CLOSED);
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> pollService.openPoll(LongMock.defaultLong, dateTimeDto));
	}
	
	@Test
	public void shouldNotOpenWhenExpirationDateTimeIsBeforeNow () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		LocalDateTimeDto dateTimeDto = new LocalDateTimeDto(LocalDateTime.now());
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> pollService.openPoll(LongMock.defaultLong, dateTimeDto));
	}
	
	@Test
	public void shouldOpenWhenExpirationDateTimeDtoIsNull () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		Poll onGoingMockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		onGoingMockedPoll.setStatus(StatusPollEnum.ONGOING);
		LocalDateTimeDto dateTimeDto = null;
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		when(pollRepository.save(any(Poll.class))).thenReturn(onGoingMockedPoll);
		assertEquals(new PollDto(onGoingMockedPoll), pollService.openPoll(LongMock.defaultLong, dateTimeDto));
	}
	
	@Test
	public void shouldOpenWhenExpirationDateTimeIsNull () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		LocalDateTimeDto dateTimeDto = new LocalDateTimeDto();
		Poll onGoingMockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		onGoingMockedPoll.setStatus(StatusPollEnum.ONGOING);
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		when(pollRepository.save(any(Poll.class))).thenReturn(onGoingMockedPoll);
		assertEquals(new PollDto(onGoingMockedPoll), pollService.openPoll(LongMock.defaultLong, dateTimeDto));
	}
	
	@Test
	public void shouldOpenPoll () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		LocalDateTimeDto dateTimeDto = new LocalDateTimeDto(LocalDateTime.now().plusMinutes(5));
		Poll onGoingMockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		onGoingMockedPoll.setStatus(StatusPollEnum.ONGOING);
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		when(pollRepository.save(any(Poll.class))).thenReturn(onGoingMockedPoll);
		assertEquals(new PollDto(onGoingMockedPoll), pollService.openPoll(LongMock.defaultLong, dateTimeDto));
	}
	
	@Test
	public void shouldFindPollById () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		when(pollRepository.findById(anyLong())).thenReturn(Optional.of(mockedPoll));
		assertEquals(mockedPoll, pollService.findPollById(LongMock.defaultLong));
	}
	
	@Test
	public void shouldNotFindPollById () {
		when(pollRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> pollService.findPollById(LongMock.defaultLong));
	}
	
	@Test
	public void shouldUpdatePoll () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		when(pollRepository.save(any(Poll.class))).thenReturn(mockedPoll);
		when(pollRepository.existsById(anyLong())).thenReturn(true);
		assertEquals(mockedPoll, pollService.updatePoll(mockedPoll));
	}
	
	@Test
	public void shouldNotUpdatePoll () {
		Poll mockedPoll = PollMock.mockPoll(LongMock.defaultLong);
		when(pollRepository.existsById(anyLong())).thenReturn(false);
		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> pollService.updatePoll(mockedPoll));
	}
	
	@Test
	public void shouldGetExpiredPolls () {
		List<Poll> mockPollList = PollMock.mockPollList();
		when(pollRepository.getExpiredPollsByInstant(any(Instant.class))).thenReturn(mockPollList);
		List<Poll> servicePollList = pollService.getAllExpiredPolls();
		assertEquals(mockPollList.size(), servicePollList.size());
		mockPollList.forEach(poll -> {
			assertTrue(servicePollList.contains(poll));
		});
	}

}
