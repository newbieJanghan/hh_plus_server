package my.ecommerce.application.balance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import jakarta.persistence.EntityNotFoundException;
import my.ecommerce.application.api.balance.BalanceService;
import my.ecommerce.application.api.balance.dto.BalanceResponseDto;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;
import my.ecommerce.domain.user.User;
import my.ecommerce.domain.user.UserRepository;
import my.ecommerce.utils.UUIDGenerator;

public class BalanceServiceTest {
	@Mock
	private UserBalanceRepository userBalanceRepository;
	@Mock
	private UserRepository userRepository;

	private BalanceService balanceService;

	@BeforeEach
	void setBalanceService() {
		openMocks(this);
		balanceService = new BalanceService(userBalanceRepository, userRepository);
	}

	@Test
	@DisplayName("UserBalance 조회 성공 및 ResponseDto 생성 성공")
	void success_myBalance() {
		// given
		UUID id = UUIDGenerator.generate();
		UUID userId = UUIDGenerator.generate();
		long amount = 1000;
		UserBalance userBalance = UserBalance.builder().id(id).userId(userId).amount(amount).build();

		when(userBalanceRepository.findByUserId(userId)).thenReturn(userBalance);
		// when
		BalanceResponseDto responseDto = balanceService.myBalance(userId);

		// then
		assertEquals(id, responseDto.getId());
		assertEquals(userId, responseDto.getUserId());
		assertEquals(amount, responseDto.getAmount());
	}

	@Test
	@DisplayName("UserBalance 가 존재하지 않아 새로운 UserBalance 생성")
	void success_withNewBalance() {
		// given
		UUID userId = UUIDGenerator.generate();
		User existUser = User.builder().id(userId).build();

		when(userBalanceRepository.findByUserId(userId)).thenReturn(null);
		when(userRepository.findOneById(userId)).thenReturn(existUser);

		doNothing().when(userBalanceRepository).save(any(UserBalance.class));

		// when
		BalanceResponseDto responseDto = balanceService.myBalance(userId);

		// then
		verify(userBalanceRepository, times(1)).save(any(UserBalance.class));

		assertInstanceOf(UUID.class, responseDto.getId());
		assertEquals(existUser.getId(), responseDto.getUserId());
		assertEquals(0, responseDto.getAmount());

	}

	@Test
	@DisplayName("신규 UserBalance 생성을 시도했으나 user 가 존재하지 않음")
	void fail_forNotFoundUser() {
		// given
		UUID userId = UUIDGenerator.generate();

		when(userBalanceRepository.findByUserId(userId)).thenReturn(null);
		when(userRepository.findOneById(userId)).thenReturn(null);
		// when & then
		Throwable exception = assertThrows(EntityNotFoundException.class, () -> balanceService.myBalance(userId));
		assertEquals("User not found", exception.getMessage());
	}

	@Test
	@DisplayName("충전 성공")
	void success_charge() {
		// given
		UUID userId = UUIDGenerator.generate();
		long amount = 1000;

		// when
		BalanceResponseDto responseDto = balanceService.charge(userId, amount);

		// then
		assertEquals(userId, responseDto.getUserId());
		assertEquals(amount, responseDto.getAmount());
	}
}
