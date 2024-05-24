package my.ecommerce;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "my.ecommerce")
public class ECommerceApplication {

	public static void main(String[] args) {
		LocalDateTime from = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
		LocalDateTime to = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
		LocalDateTime randomDate = LocalDateTime.ofEpochSecond(
			ThreadLocalRandom.current().nextLong(from.toEpochSecond(ZoneOffset.UTC), to.toEpochSecond(ZoneOffset.UTC)),
			0, ZoneOffset.UTC);
		System.out.println(randomDate);

		SpringApplication.run(ECommerceApplication.class, args);
	}

}
