package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
	public UserBalance findByUserId(UUID userId) {
		return null;
	}

	public UserBalance save(UserBalance userBalance) {
		// save user balance
		return null;
	}
}
