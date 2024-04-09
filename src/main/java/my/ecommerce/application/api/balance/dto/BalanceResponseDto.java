package my.ecommerce.application.api.balance.dto;

import lombok.Getter;
import my.ecommerce.domain.balance.Balance;

@Getter
public class BalanceResponseDto {
    private final long amount;

    public BalanceResponseDto(Balance balance) {
        amount = balance.getAmount();
    }

}
