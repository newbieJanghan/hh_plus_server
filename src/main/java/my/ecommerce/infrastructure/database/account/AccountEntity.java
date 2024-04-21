package my.ecommerce.infrastructure.database.account;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.account.Account;
import my.ecommerce.infrastructure.database.account_history.AccountHistoryEntity;
import my.ecommerce.infrastructure.database.common.BaseEntity;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
public class AccountEntity extends BaseEntity {
	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(columnDefinition = "BIGINT DEFAULT 0")
	private long amount;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
	private List<AccountHistoryEntity> histories;

	@Builder
	public AccountEntity(UUID id, UUID userId, long amount, List<AccountHistoryEntity> histories) {
		super(id);
		this.userId = userId;
		this.amount = amount;
		this.histories = histories;
	}

	public static AccountEntity fromDomain(Account domain) {
		return AccountEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.amount(domain.getAmount())
			.build();
	}

	public Account toDomain() {
		return Account.builder()
			.id(getId())
			.userId(userId)
			.amount(amount)
			.build();
	}
}
