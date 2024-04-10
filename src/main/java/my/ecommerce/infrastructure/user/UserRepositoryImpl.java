package my.ecommerce.infrastructure.user;

import my.ecommerce.domain.user.User;
import my.ecommerce.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public User findOneById(UUID id) {
        return User.builder().id(id).build();
    }
}
