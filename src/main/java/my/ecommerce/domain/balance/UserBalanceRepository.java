package my.ecommerce.domain.balance;

import java.util.UUID;

import org.springframework.lang.Nullable;

public interface UserBalanceRepository {
	@Nullable
	UserBalance findByUserId(UUID userId);

	UserBalance save(UserBalance userBalance);

	void destroy(UUID id);
}
