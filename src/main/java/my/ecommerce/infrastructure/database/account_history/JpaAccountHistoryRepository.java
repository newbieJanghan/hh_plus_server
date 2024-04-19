package my.ecommerce.infrastructure.database.account_history;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountHistoryRepository extends JpaRepository<AccountHistoryEntity, UUID> {

}
