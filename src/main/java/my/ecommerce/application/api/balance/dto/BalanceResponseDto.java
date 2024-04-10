package my.ecommerce.application.api.balance.dto;

import lombok.Getter;
import my.ecommerce.domain.balance.UserBalance;

import java.util.UUID;

@Getter
public class BalanceResponseDto {
    private final UUID id;
    private final UUID userId;
    private final long amount;

    public BalanceResponseDto(UserBalance userBalance) {
        id = userBalance.getId();
        userId = userBalance.getUserId();
        amount = userBalance.getAmount();
    }

}
