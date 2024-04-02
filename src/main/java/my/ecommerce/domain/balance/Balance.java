package my.ecommerce.domain.balance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Balance {
    private long amount;

    @Builder
    private Balance(long amount) {
        this.amount = amount;
    }
}
