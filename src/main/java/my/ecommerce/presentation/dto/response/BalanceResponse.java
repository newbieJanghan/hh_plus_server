package my.ecommerce.presentation.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.account.Account;

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

	public static BalanceResponse fromDomain(Account account) {
		return BalanceResponse.builder()
			.id(account.getId())
			.userId(account.getUserId())
			.amount(account.getAmount())
			.build();
	}

}
