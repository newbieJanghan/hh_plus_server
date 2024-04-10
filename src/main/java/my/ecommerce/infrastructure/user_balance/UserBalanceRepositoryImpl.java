package my.ecommerce.infrastructure.user_balance;

import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
    public UserBalance findByUserId(UUID userId) {
        return UserBalance.builder()
                .amount(1000)
                .build();
    }

    public void save(UserBalance userBalance) {
        // save user balance
    }
}
