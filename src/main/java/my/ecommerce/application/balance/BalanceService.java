package my.ecommerce.application.balance;

import my.ecommerce.application.balance.dto.BalanceResponseDto;
import my.ecommerce.domain.balance.Balance;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    public BalanceService() {
    }

    public BalanceResponseDto getOne(long userId) {
        Balance result = Balance.builder()
                .amount(1000)
                .build();

        return new BalanceResponseDto(result);
    }

    public BalanceResponseDto charge(long userId, long amount) {
        Balance result = Balance.builder()
                .amount(amount)
                .build();

        return new BalanceResponseDto(result);
    }
}
