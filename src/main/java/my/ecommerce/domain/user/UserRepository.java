package my.ecommerce.domain.user;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Nullable;

@Repository
public interface UserRepository {
	@Nullable
	User findById(UUID id);
}
