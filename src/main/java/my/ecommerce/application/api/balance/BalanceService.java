package my.ecommerce.application.api.balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import my.ecommerce.application.api.balance.dto.BalanceResponseDto;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;
import my.ecommerce.domain.user.User;
import my.ecommerce.domain.user.UserRepository;

@Service
public class BalanceService {
	private final UserBalanceRepository userBalanceRepository;
	private final UserRepository userRepository;

	@Autowired
	public BalanceService(UserBalanceRepository userBalanceRepository, UserRepository userRepository) {
		this.userBalanceRepository = userBalanceRepository;
		this.userRepository = userRepository;

	}

	public BalanceResponseDto myBalance(UUID userId) {
		UserBalance userBalance = userBalanceRepository.findByUserId(userId);
		if (userBalance != null) {
			return BalanceResponseDto.fromDomain(userBalance);
		}

		User existUser = userRepository.findOneById(userId);
		if (existUser == null) {
			throw new EntityNotFoundException("User not found");
		}

		userBalance = UserBalance.newBalance(userId, 0);
		userBalanceRepository.save(userBalance);

		return BalanceResponseDto.fromDomain(userBalance);
	}

	public BalanceResponseDto charge(UUID userId, long amount) {
		return BalanceResponseDto.fromDomain(UserBalance.newBalance(userId, amount));
	}
}
