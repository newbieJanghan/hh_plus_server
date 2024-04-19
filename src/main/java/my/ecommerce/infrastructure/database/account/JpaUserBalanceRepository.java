package my.ecommerce.infrastructure.database.account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, UUID> {
	Optional<UserBalanceEntity> findByUserId(UUID userId);
}
