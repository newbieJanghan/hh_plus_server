package my.ecommerce.application.balance.dto;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.application.common.Response;
import my.ecommerce.domain.balance.Balance;

@Getter
public class BalanceResponseDto extends Response<Balance> {
    @Builder
    public BalanceResponseDto(Balance data) {
        super("OK", data);
    }

}
