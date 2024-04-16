package my.ecommerce.domain.balance.balance_history;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.balance.UserBalance;

@Getter
public class UserBalanceHistory extends BaseDomain {
	private final UserBalance balance;
	private final BalanceHistoryType type;
	private final long amount;

	@Builder
	public UserBalanceHistory(UUID id, UserBalance balance, BalanceHistoryType type, long amount) {
		this.id = id;
		this.balance = balance;
		this.type = type;
		this.amount = amount;
	}

	public static UserBalanceHistory newChargeHistory(UserBalance balance, long amount) {
		return UserBalanceHistory.builder()
			.balance(balance)
			.type(BalanceHistoryType.CHARGE)
			.amount(amount)
			.build();
	}

	public static UserBalanceHistory newUsageHistory(UserBalance balance, long amount) {
		return UserBalanceHistory.builder()
			.balance(balance)
			.type(BalanceHistoryType.USAGE)
			.amount(amount)
			.build();
	}

	public enum BalanceHistoryType {
		CHARGE, USAGE
	}
}
