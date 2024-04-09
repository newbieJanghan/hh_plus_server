package my.ecommerce.application.api.balance;

import my.ecommerce.application.api.balance.dto.BalanceResponseDto;
import my.ecommerce.domain.balance.Balance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BalanceService {
    public BalanceService() {
    }

    public BalanceResponseDto myBalance(UUID userId) {
        return new BalanceResponseDto(Balance.builder()
                .amount(1000)
                .build());
    }

    public BalanceResponseDto charge(UUID userId, long amount) {
        return new BalanceResponseDto(Balance.builder()
                .amount(amount)
                .build());
    }
}
