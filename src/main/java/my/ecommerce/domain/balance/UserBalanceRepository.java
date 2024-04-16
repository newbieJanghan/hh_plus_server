package my.ecommerce.domain.balance;

import java.util.UUID;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository {
	@Nullable
	UserBalance findByUserId(UUID userId);

	UserBalance save(UserBalance userBalance);

	void destroy(UUID id);
}
