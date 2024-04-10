package my.ecommerce.domain.user;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository {
    @Nullable
    User findOneById(UUID id);
}
