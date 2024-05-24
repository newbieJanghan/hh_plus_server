package my.batch.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDate {
	public static LocalDateTime between(LocalDateTime from, LocalDateTime to) {
		return LocalDateTime.ofEpochSecond(
			ThreadLocalRandom.current().nextLong(from.toEpochSecond(ZoneOffset.UTC), to.toEpochSecond(ZoneOffset.UTC)),
			0, ZoneOffset.UTC);
	}
}
