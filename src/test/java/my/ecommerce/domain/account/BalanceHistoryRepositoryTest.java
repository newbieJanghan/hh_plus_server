package my.ecommerce.domain.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class BalanceHistoryRepositoryTest {
	@Autowired
	private AccountHistoryRepository balanceHistoryRepository;

	@Autowired
	private AccountRepository balanceRepository;

	@Test
	@DisplayName("save()는 영속성 저장 후 도메인 객체를 반환")
	public void save() {
		// given
		Account balance = balanceRepository.save(Account.newAccount(UUIDGenerator.generate()));

		// when
		AccountHistory history = AccountHistory.newChargeHistory(balance, 1000);
		AccountHistory persisted = balanceHistoryRepository.save(history);

		// then
		assertInstanceOf(AccountHistory.class, persisted);
		assertInstanceOf(UUID.class, persisted.getId());

		// cleanup
		balanceHistoryRepository.destroy(persisted.getId());
		balanceRepository.destroy(balance.getId());
	}
}
