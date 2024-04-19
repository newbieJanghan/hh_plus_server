package my.ecommerce.api.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.account.Account;

@Getter
public class AccountResponse {
	private final UUID id;
	private final UUID userId;
	private final long amount;

	@Builder
	public AccountResponse(UUID id, UUID userId, long amount) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
	}

	public static AccountResponse fromDomain(Account account) {
		return AccountResponse.builder()
			.id(account.getId())
			.userId(account.getUserId())
			.amount(account.getAmount())
			.build();
	}

}
