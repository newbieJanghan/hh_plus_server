package my.ecommerce.presentation.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.balance.UserBalance;

@Getter
public class BalanceResponse {
	private final UUID id;
	private final UUID userId;
	private final long amount;

	@Builder
	public BalanceResponse(UUID id, UUID userId, long amount) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
	}

	public static BalanceResponse fromDomain(UserBalance userBalance) {
		return BalanceResponse.builder()
			.id(userBalance.getId())
			.userId(userBalance.getUserId())
			.amount(userBalance.getAmount())
			.build();
	}

}
