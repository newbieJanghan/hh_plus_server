package my.ecommerce.domain.balance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import my.ecommerce.domain.user.User;
import my.ecommerce.utils.UUIDGenerator;

public class BalanceServiceTest {
	@Mock
	private UserBalanceRepository userBalanceRepository;

	private BalanceService balanceService;

	@BeforeEach
	void setBalanceService() {
		openMocks(this);
		balanceService = new BalanceService(userBalanceRepository);
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
		UserBalance result = balanceService.myBalance(userId);

		// then
		assertEquals(id, result.getId());
		assertEquals(userId, result.getUserId());
		assertEquals(amount, result.getAmount());
	}

	@Test
	@DisplayName("UserBalance 가 존재하지 않아 새로운 UserBalance 생성")
	void success_withNewBalance() {
		// given
		UUID userId = UUIDGenerator.generate();
		User existUser = User.builder().id(userId).build();

		when(userBalanceRepository.findByUserId(userId)).thenReturn(null);

		doNothing().when(userBalanceRepository).save(any(UserBalance.class));

		// when
		UserBalance result = balanceService.myBalance(userId);

		// then
		verify(userBalanceRepository, times(1)).save(any(UserBalance.class));

		assertInstanceOf(UUID.class, result.getId());
		assertEquals(existUser.getId(), result.getUserId());
		assertEquals(0, result.getAmount());

	}

	@Test
	@DisplayName("충전 성공")
	void success_charge() {
		// given
		UUID userId = UUIDGenerator.generate();
		long amount = 1000;

		// when
		UserBalance result = balanceService.charge(userId, amount);

		// then
		assertEquals(userId, result.getUserId());
		assertEquals(amount, result.getAmount());
	}
}
