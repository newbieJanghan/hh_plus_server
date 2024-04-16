package my.ecommerce.datasource.user_balance_history;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;

@Repository
public class UserBalanceHistoryRepositoryImpl implements UserBalanceHistoryRepository {
	public UserBalanceHistory save(UserBalanceHistory history) {
		return null;
	}
}
