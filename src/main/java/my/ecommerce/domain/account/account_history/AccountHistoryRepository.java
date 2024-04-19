package my.ecommerce.domain.account.account_history;

import java.util.UUID;

public interface AccountHistoryRepository {
	AccountHistory save(AccountHistory history);

	void destroy(UUID id);
}
