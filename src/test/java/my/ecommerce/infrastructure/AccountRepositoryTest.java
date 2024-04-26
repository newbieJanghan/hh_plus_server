package my.ecommerce.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;
import my.ecommerce.utils.Prepare;
import my.ecommerce.utils.UUIDGenerator;

public class AccountRepositoryTest extends AbstractRepositoryTest {
	@Autowired
	private AccountRepository accountRepository;

	@Test
	@DisplayName("Account 도메인을 저장한 후 영속성 저장된 Account 를 반환 성공")
	public void success_saveAndReturnPersistedAccount() {
		Account account = Prepare.account();

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
	@DisplayName("userId 로 Account 를 조회 성공")
	public void success_findByUserId() {
		// given
		Account persistence = accountRepository.save(Prepare.account());

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
	@DisplayName("userId 로 Account 를 조회 시 로우가 없는 경우 null 반환")
	public void success_findByUserId_withNoRow() {
		// given
		UUID userId = UUIDGenerator.generate();

		// when
		Account result = accountRepository.findByUserId(userId);

		// then
		assertNull(result);
	}

}
