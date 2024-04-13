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
		if (userBalance == null) {
			userBalance = UserBalance.newBalance(userId, 0);
			userBalanceRepository.save(userBalance);
		}

		return userBalance;
	}

	public UserBalance charge(UUID userId, long amount) {
		return UserBalance.newBalance(userId, amount);
	}
}
