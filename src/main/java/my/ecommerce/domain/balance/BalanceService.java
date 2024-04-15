package my.ecommerce.domain.balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
	private final UserBalanceRepository userBalanceRepository;

	@Autowired
	public BalanceService(UserBalanceRepository userBalanceRepository) {
		this.userBalanceRepository = userBalanceRepository;

	}

	public UserBalance myBalance(UUID userId) {
		UserBalance userBalance = userBalanceRepository.findByUserId(userId);
		if (userBalance != null) {
			return userBalance;
		}

		return userBalanceRepository.save(UserBalance.newBalance(userId));
	}

	public UserBalance charge(UUID userId, long amount) {
		validateCharge(amount);

		UserBalance userBalance = myBalance(userId);
		userBalance.charge(amount);

		return userBalanceRepository.save(userBalance);
	}

	public UserBalance use(UUID userId, long amount) {
		UserBalance userBalance = myBalance(userId);

		validateUsage(userBalance, amount);
		userBalance.use(amount);

		return userBalanceRepository.save(userBalance);
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
