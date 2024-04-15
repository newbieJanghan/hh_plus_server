package my.ecommerce.domain.balance.balance_history;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserBalanceHistory {
	private final UUID balanceId;
	private final BalanceHistoryType type;
	private final long amount;
	@Setter
	private UUID id;

	@Builder
	public UserBalanceHistory(UUID id, UUID balanceId, BalanceHistoryType type, long amount) {
		this.id = id;
		this.balanceId = balanceId;
		this.type = type;
		this.amount = amount;
	}

	public static UserBalanceHistory newChargeHistory(UUID balanceId, long amount) {
		return UserBalanceHistory.builder()
			.balanceId(balanceId)
			.type(BalanceHistoryType.CHARGE)
			.amount(amount)
			.build();
	}

	public static UserBalanceHistory newUsageHistory(UUID balanceId, long amount) {
		return UserBalanceHistory.builder()
			.balanceId(balanceId)
			.type(BalanceHistoryType.USAGE)
			.amount(amount)
			.build();
	}

	public enum BalanceHistoryType {
		CHARGE, USAGE
	}
}
