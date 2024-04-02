package my.ecommerce;

import my.ecommerce.domain.balance.Balance;

public class Mocks {
    public static Balance mockBalance(long amount) {
        return Balance.builder()
            .amount(amount)
            .build();
    }
}
