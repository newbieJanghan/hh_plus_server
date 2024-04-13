package my.ecommerce.application.api.balance.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.balance.UserBalance;

@Getter
public class BalanceResponseDto {
	private final UUID id;
	private final UUID userId;
	private final long amount;

	@Builder
	public BalanceResponseDto(UUID id, UUID userId, long amount) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
	}

	public static BalanceResponseDto fromDomain(UserBalance userBalance) {
		return BalanceResponseDto.builder()
			.id(userBalance.getId())
			.userId(userBalance.getUserId())
			.amount(userBalance.getAmount())
			.build();
	}

}
