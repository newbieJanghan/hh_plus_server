package my.ecommerce.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;
import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;
import my.ecommerce.utils.UUIDGenerator;

public class AccountHistoryRepositoryTest extends AbstractRepositoryTest {
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	@DisplayName("save()는 영속성 저장 후 도메인 객체를 반환")
	public void save() {
		// given
		Account account = accountRepository.save(Account.newAccount(UUIDGenerator.generate()));

		// when
		AccountHistory history = AccountHistory.newChargeHistory(account, 1000);
		AccountHistory persisted = accountHistoryRepository.save(history);

		// then
		assertInstanceOf(AccountHistory.class, persisted);
		assertInstanceOf(UUID.class, persisted.getId());

		// cleanup
		accountHistoryRepository.destroy(persisted.getId());
		accountRepository.destroy(account.getId());
	}
}
