package my.ecommerce.domain.balance;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.utils.UUIDGenerator;

@Getter
public class UserBalance {
	private final UUID id;
	private final UUID userId;
	private long amount;

	@Builder
	public UserBalance(UUID id, UUID userId, long amount) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
	}

	public static UserBalance newBalance(UUID userId) {
		return new UserBalance(UUIDGenerator.generate(), userId, 0);
	}

	public void use(long amount) {
		this.amount -= amount;
	}

	public void charge(long amount) {
		this.amount += amount;
	}
}
