package my.ecommerce.domain.account;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;

@Getter
public class Account extends BaseDomain {
	private final UUID userId;
	private long amount;

	@Builder
	public Account(UUID id, UUID userId, long amount) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
	}

	public static Account newAccount(UUID userId) {
		return new Account(null, userId, 0);
	}

	public void use(long amount) {
		this.amount -= amount;
	}

	public void charge(long amount) {
		this.amount += amount;
	}
}
