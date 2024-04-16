package my.ecommerce.domain;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Getter;

public abstract class BaseDomain {
	@Nullable
	@Getter
	protected UUID id;

	public void persist(UUID id) {
		this.id = id;
	}

}
