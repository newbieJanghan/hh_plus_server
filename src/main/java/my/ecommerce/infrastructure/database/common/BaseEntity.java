package my.ecommerce.infrastructure.database.common;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.utils.UUIDGenerator;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class BaseEntity {
	@Id
	protected UUID id;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, name = "created_at")
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false, name = "updated_at")
	protected LocalDateTime updatedAt;

	public BaseEntity(@Nullable UUID id) {
		this.id = id == null ? generatePK() : id;
	}

	private UUID generatePK() {
		return UUIDGenerator.generate();
	}
}
