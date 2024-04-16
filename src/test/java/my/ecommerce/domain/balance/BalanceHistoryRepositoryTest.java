package my.ecommerce.domain.balance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class BalanceHistoryRepositoryTest {
	@Autowired
	private UserBalanceHistoryRepository balanceHistoryRepository;

	@Autowired
	private UserBalanceRepository balanceRepository;

	@Test
	@DisplayName("save()는 영속성 저장 후 도메인 객체를 반환")
	public void save() {
		// given
		UserBalance balance = balanceRepository.save(UserBalance.newBalance(UUIDGenerator.generate()));

		// when
		UserBalanceHistory history = UserBalanceHistory.newChargeHistory(balance, 1000);
		UserBalanceHistory persisted = balanceHistoryRepository.save(history);

		// then
		assertInstanceOf(UserBalanceHistory.class, persisted);
		assertInstanceOf(UUID.class, persisted.getId());

		// cleanup
		balanceHistoryRepository.destroy(persisted.getId());
		balanceRepository.destroy(balance.getId());
	}
}
