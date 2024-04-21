package my.ecommerce.domain.account.account_history;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.account.Account;

@Getter
public class AccountHistory extends BaseDomain {
	private final Account account;
	private final TransactionType type;
	private final long amount;

	@Builder
	public AccountHistory(UUID id, Account account, TransactionType type, long amount) {
		this.id = id;
		this.account = account;
		this.type = type;
		this.amount = amount;
	}

	public static AccountHistory newChargeHistory(Account account, long amount) {
		return AccountHistory.builder()
			.account(account)
			.type(TransactionType.CHARGE)
			.amount(amount)
			.build();
	}

	public static AccountHistory newUsageHistory(Account account, long amount) {
		return AccountHistory.builder()
			.account(account)
			.type(TransactionType.USAGE)
			.amount(amount)
			.build();
	}

	public enum TransactionType {
		CHARGE, USAGE
	}
}
