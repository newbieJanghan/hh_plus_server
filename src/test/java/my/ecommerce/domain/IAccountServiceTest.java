package my.ecommerce.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;
import my.ecommerce.domain.account.AccountService;
import my.ecommerce.utils.AbstractTestWithDatabase;
import my.ecommerce.utils.Prepare;

public class IAccountServiceTest extends AbstractTestWithDatabase {
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepository;

	@Test
	@DisplayName("포인트 사용 성공")
	void success_use() {
		// given
		long currentAmount = 500;
		long useAmount = 100;
		Account account = Prepare.account(currentAmount);
		accountRepository.save(account);

		// when
		Account result = accountService.use(account.getId(), useAmount);

		// then
		assertEquals(currentAmount - useAmount, result.getBalance());
	}

	@Test
	@DisplayName("동시성 제어 성공 - 동시의 사용 요청에 절반은 잔액 부족 에러 발생")
	void success_concurrently_use() throws InterruptedException {
		// given
		long currentAmount = 500;
		long useAmount = 100;
		int threadsCount = 10;

		Account account = Prepare.account(currentAmount);
		accountRepository.save(account);

		// when
		CountDownLatch latch = new CountDownLatch(threadsCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		for (int i = 0; i < threadsCount; i++) {
			executor.execute(() -> {
				try {
					accountService.use(account.getId(), useAmount);
					System.out.println("Success thread" + Thread.currentThread().getName());
					successCount.incrementAndGet();
				} catch (IllegalArgumentException e) {
					System.out.println("Fail thread" + Thread.currentThread().getName());
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		// then
		assertEquals(threadsCount / 2, successCount.get());
		assertEquals(threadsCount / 2, failCount.get());
	}
}
