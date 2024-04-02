package my.ecommerce.domain.balance;

import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    public BalanceService() {}

    public Balance getBalance(long userId) {
        return Balance.builder()
            .amount(1000)
            .build();
    }

    public Balance charge(long userId, long amount) {
        return Balance.builder()
            .amount(amount)
            .build();
    }
}
