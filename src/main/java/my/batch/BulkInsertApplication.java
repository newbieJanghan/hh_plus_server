package my.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import my.batch.service.BulkInsertOrderItemService;

@SpringBootApplication()
public class BulkInsertApplication implements CommandLineRunner {
	@Autowired
	private BulkInsertOrderItemService bulkInsertOrderItemService;

	public static void main(String[] args) {
		SpringApplication.run(BulkInsertApplication.class, args);
	}

	public void run(String... args) {
		bulkInsertOrderItemService.execute(1, 1, 0);
	}
}
