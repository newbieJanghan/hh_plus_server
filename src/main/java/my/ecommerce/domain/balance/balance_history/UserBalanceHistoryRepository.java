package my.ecommerce.domain.balance.balance_history;

import java.util.UUID;

public interface UserBalanceHistoryRepository {
	UserBalanceHistory save(UserBalanceHistory history);

	void destroy(UUID id);
}
