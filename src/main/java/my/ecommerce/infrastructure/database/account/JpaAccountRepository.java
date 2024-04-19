package my.ecommerce.infrastructure.database.account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
	Optional<AccountEntity> findByUserId(UUID userId);
}
