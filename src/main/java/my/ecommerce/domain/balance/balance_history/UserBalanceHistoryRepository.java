package my.ecommerce.domain.balance.balance_history;

import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceHistoryRepository {
	UserBalanceHistory save(UserBalanceHistory history);
}
