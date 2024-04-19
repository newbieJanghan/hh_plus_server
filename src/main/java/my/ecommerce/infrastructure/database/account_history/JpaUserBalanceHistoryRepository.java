package my.ecommerce.infrastructure.database.account_history;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserBalanceHistoryRepository extends JpaRepository<UserBalanceHistoryEntity, UUID> {

}
