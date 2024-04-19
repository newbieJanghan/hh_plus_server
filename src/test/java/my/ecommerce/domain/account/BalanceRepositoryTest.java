package my.ecommerce.domain.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class BalanceRepositoryTest {
	@Autowired
	private AccountRepository accountRepository;

	@Test
	@DisplayName("UserBalance 도메인을 저장한 후 영속성 저장된 UserBalance 를 반환 성공")
	public void success_saveAndReturnPersistedUserBalance() {
		Account account = Prepare.userBalance();

		// when
		Account result = accountRepository.save(account);

		// then
		assertInstanceOf(Account.class, result);
		assertInstanceOf(UUID.class, result.getId());
		assertNotNull(result.getId());
		assertEquals(account.getUserId(), result.getUserId());
		assertEquals(account.getAmount(), result.getAmount());

		// cleanup
		accountRepository.destroy(result.getId());
	}

	@Test
	@DisplayName("userId 로 UserBalance 를 조회 성공")
	public void success_findByUserId() {
		// given
		Account persistence = accountRepository.save(Prepare.userBalance());

		// when
		Account result = accountRepository.findByUserId(persistence.getUserId());

		// then
		assertNotNull(result);
		assertEquals(persistence.getId(), result.getId());
		assertEquals(persistence.getUserId(), result.getUserId());
		assertEquals(persistence.getAmount(), result.getAmount());

		// cleanup
		accountRepository.destroy(persistence.getId());
	}

	@Test
	@DisplayName("userId 로 UserBalance 를 조회 시 로우가 없는 경우 null 반환")
	public void success_findByUserId_withNoRow() {
		// given
		UUID userId = UUIDGenerator.generate();

		// when
		Account result = accountRepository.findByUserId(userId);

		// then
		assertNull(result);
	}

}
