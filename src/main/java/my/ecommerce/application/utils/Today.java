package my.ecommerce.application.utils;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Today {
	private static Clock _clock = Clock.systemDefaultZone();

	@Autowired
	public Today(Clock clock) {
		Today._clock = clock;
	}

	public static LocalDateTime getDateTime() {
		return LocalDateTime.now(_clock);
	}

	public static LocalDateTime beginningOfDay() {
		return LocalDateTime.now(_clock).withHour(0).withMinute(0).withSecond(0);
	}

	public static LocalDateTime endOfDay() {
		return LocalDateTime.now(_clock).withHour(23).withMinute(59).withSecond(59);
	}
}
