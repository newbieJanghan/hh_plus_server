package my.ecommerce.infrastructure.database.account;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.account.Account;
import my.ecommerce.infrastructure.database.common.BaseEntity;

@Entity
@Table(
	name = "account",
	indexes = {@Index(name = "idx_user_id", columnList = "user_id", unique = true)})
@Getter
@NoArgsConstructor
public class AccountEntity extends BaseEntity {
	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(columnDefinition = "BIGINT DEFAULT 0")
	private long balance;

	@Builder
	public AccountEntity(UUID id, UUID userId, long balance) {
		super(id);
		this.userId = userId;
		this.balance = balance;
	}

	public static AccountEntity fromDomain(Account domain) {
		return AccountEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.balance(domain.getBalance())
			.build();
	}

	public Account toDomain() {
		return Account.builder().id(getId()).userId(userId).balance(balance).build();
	}
}
