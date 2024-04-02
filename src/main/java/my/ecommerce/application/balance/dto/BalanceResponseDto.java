package my.ecommerce.application.balance.dto;

import lombok.Getter;
import my.ecommerce.application.common.Response;
import my.ecommerce.domain.balance.Balance;

@Getter
public class BalanceResponseDto extends Response<Balance> {
    public BalanceResponseDto(Balance data) {
        super("OK", data);
    }

}
