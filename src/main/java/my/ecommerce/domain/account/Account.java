package my.ecommerce.domain.account;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;

@Getter
public class Account extends BaseDomain {
	private final UUID userId;
	private long balance;

	@Builder
	public Account(UUID id, UUID userId, long balance) {
		this.id = id;
		this.userId = userId;
		this.balance = balance;
	}

	public static Account newAccount(UUID userId) {
		return new Account(null, userId, 0);
	}

	public void use(long balance) {
		this.balance -= balance;
	}

	public void charge(long balance) {
		this.balance += balance;
	}
}
