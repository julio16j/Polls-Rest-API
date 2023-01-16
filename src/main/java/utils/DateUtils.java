package utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	public static LocalDateTime fromInstant (Instant instant) {
		return instant == null ? null
				: LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}
	
	public static Instant fromLocalDateTime (LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
	}
	
	public static long secondsFromLocalDateTime(LocalDateTime localDateTime) {
		return fromLocalDateTime(localDateTime).getEpochSecond();
	}
}
