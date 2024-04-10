package my.ecommerce.domain.balance;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserBalanceRepository {
    @Nullable
    UserBalance findByUserId(UUID userId);

    void save(UserBalance userBalance);
}
