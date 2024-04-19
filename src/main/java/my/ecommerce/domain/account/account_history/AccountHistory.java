package my.ecommerce.domain.account.account_history;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.account.Account;

@Getter
public class AccountHistory extends BaseDomain {
	private final Account balance;
	private final BalanceHistoryType type;
	private final long amount;

	@Builder
	public AccountHistory(UUID id, Account balance, BalanceHistoryType type, long amount) {
		this.id = id;
		this.balance = balance;
		this.type = type;
		this.amount = amount;
	}

	public static AccountHistory newChargeHistory(Account balance, long amount) {
		return AccountHistory.builder()
			.balance(balance)
			.type(BalanceHistoryType.CHARGE)
			.amount(amount)
			.build();
	}

	public static AccountHistory newUsageHistory(Account balance, long amount) {
		return AccountHistory.builder()
			.balance(balance)
			.type(BalanceHistoryType.USAGE)
			.amount(amount)
			.build();
	}

	public enum BalanceHistoryType {
		CHARGE, USAGE
	}
}
