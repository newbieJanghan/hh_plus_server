package my.ecommerce.infrastructure.user_balance;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
	public UserBalance findByUserId(UUID userId) {
		return null;
	}

	public void save(UserBalance userBalance) {
		// save user balance
	}
}
