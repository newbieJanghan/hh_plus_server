package my.ecommerce.datasource.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import my.ecommerce.utils.UUIDGenerator;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
	@Id
	protected UUID id;

	@CreationTimestamp
	@Column(nullable = false, name = "created_at")
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false, name = "updated_at")
	protected LocalDateTime updatedAt;

	public BaseEntity() {
		this.id = generatePK();
	}

	private UUID generatePK() {
		return UUIDGenerator.generate();
	}
}
