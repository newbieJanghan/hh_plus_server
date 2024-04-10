package my.ecommerce.domain.balance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.utils.UUIDGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserBalance {
    private UUID id;
    private UUID userId;
    private long amount;

    @Builder
    private UserBalance(UUID id, UUID userId, long amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public static UserBalance newBalance(UUID userId) {
        UUID newId = UUIDGenerator.generate();
        return UserBalance.builder()
                .id(newId)
                .userId(userId)
                .amount(0)
                .build();
    }
}
