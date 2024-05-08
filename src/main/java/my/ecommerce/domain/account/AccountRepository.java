package my.ecommerce.domain.account;

import java.util.UUID;

import org.springframework.lang.Nullable;

public interface AccountRepository {
	@Nullable
	Account findByUserId(UUID userId);

	@Nullable
	Account findByIdForUpdate(UUID userId);

	Account save(Account account);

	void destroy(UUID id);
}
