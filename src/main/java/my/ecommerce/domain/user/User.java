package my.ecommerce.domain.user;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;

@Getter
public class User extends BaseDomain {
	@Builder
	private User(UUID id) {
		this.id = id;
	}
}
