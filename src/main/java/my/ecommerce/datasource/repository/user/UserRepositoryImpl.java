package my.ecommerce.datasource.repository.user;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.user.User;
import my.ecommerce.domain.user.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	public User findOneById(UUID id) {
		return User.builder().id(id).build();
	}
}
