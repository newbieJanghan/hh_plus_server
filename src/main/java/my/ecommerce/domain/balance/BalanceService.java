package my.ecommerce.domain.balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;

@Service
public class BalanceService {
	private final UserBalanceRepository userBalanceRepository;
	private final UserBalanceHistoryRepository historyRepository;

	@Autowired
	public BalanceService(UserBalanceRepository userBalanceRepository, UserBalanceHistoryRepository historyRepository) {
		this.userBalanceRepository = userBalanceRepository;
		this.historyRepository = historyRepository;

	}

	public UserBalance myBalance(UUID userId) {
		UserBalance balance = userBalanceRepository.findByUserId(userId);
		if (balance != null) {
			return balance;
		}

		return userBalanceRepository.save(UserBalance.newBalance(userId));
	}

	public UserBalance charge(UUID userId, long amount) {
		validateCharge(amount);

		UserBalance balance = myBalance(userId);
		balance.charge(amount);

		UserBalance persisted = userBalanceRepository.save(balance);

		UserBalanceHistory chargeHistory = UserBalanceHistory.newChargeHistory(balance.getId(), amount);
		historyRepository.save(chargeHistory);

		return persisted;
	}

	public UserBalance use(UUID userId, long amount) {
		UserBalance balance = myBalance(userId);

		validateUsage(balance, amount);
		balance.use(amount);

		UserBalance persisted = userBalanceRepository.save(balance);

		UserBalanceHistory usageHistory = UserBalanceHistory.newUsageHistory(balance.getId(), amount);
		historyRepository.save(usageHistory);

		return persisted;
	}

	private void validateCharge(long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
		}
	}

	private void validateUsage(UserBalance balance, long amount) {
		if (balance.getAmount() < amount) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
	}
}
