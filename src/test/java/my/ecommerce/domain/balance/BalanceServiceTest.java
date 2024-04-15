package my.ecommerce.domain.balance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;
import my.ecommerce.utils.UUIDGenerator;

public class BalanceServiceTest {
	@Mock
	private UserBalanceRepository userBalanceRepository;
	@Mock
	private UserBalanceHistoryRepository historyRepository;

	@Captor
	private ArgumentCaptor<UserBalance> balanceCaptor;
	@Captor
	private ArgumentCaptor<UserBalanceHistory> historyCaptor;

	private BalanceService balanceService;

	@BeforeEach
	void setBalanceService() {
		openMocks(this);
		balanceService = new BalanceService(userBalanceRepository, historyRepository);
	}

	@Test
	@DisplayName("UserBalance 조회 성공")
	void success_myBalance() {
		// given
		UserBalance balance = prepareBalance(1000);
		mockRepositoryFindById(balance);

		// when
		UserBalance result = balanceService.myBalance(balance.getUserId());

		// then
		assertEquals(balance.getUserId(), result.getUserId());
		assertEquals(balance.getAmount(), result.getAmount());
	}

	@Test
	@DisplayName("UserBalance 조회 성공 - 새로운 UserBalance 생성")
	void success_withNewBalance() {
		// given
		UUID userId = UUIDGenerator.generate();
		UserBalance newBalance = prepareNewBalance(userId);

		when(userBalanceRepository.findByUserId(userId)).thenReturn(null);
		when(userBalanceRepository.save(any(UserBalance.class))).thenReturn(newBalance);

		// when
		UserBalance result = balanceService.myBalance(userId);

		// then
		assertInstanceOf(UUID.class, result.getId());
		assertEquals(0, result.getAmount());

	}

	@Test
	@DisplayName("충전 성공")
	void success_charge() {
		// given
		long currentAmount = 1000;
		long chargeAmount = 1000;
		UserBalance balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when
		balanceService.charge(balance.getUserId(), chargeAmount);

		// then
		verify(userBalanceRepository).save(balanceCaptor.capture());
		assertEquals(currentAmount + chargeAmount, balanceCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(UserBalanceHistory.BalanceHistoryType.CHARGE, historyCaptor.getValue().getType());
		assertEquals(chargeAmount, historyCaptor.getValue().getAmount());

	}

	@Test
	@DisplayName("충전 실패 - 유효하지 않은 충전 금액")
	void fail_charge() {
		// given
		long currentAmount = 1000;
		long chargeAmount = -1000;
		UserBalance balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(UserBalanceHistory.class));
		assertThrows(IllegalArgumentException.class, () -> balanceService.charge(balance.getUserId(), chargeAmount));
	}

	@Test
	@DisplayName("사용 성공")
	void success_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 1000;
		UserBalance balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when
		balanceService.use(balance.getUserId(), useAmount);

		// then
		verify(userBalanceRepository).save(balanceCaptor.capture());
		assertEquals(currentAmount - useAmount, balanceCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(UserBalanceHistory.BalanceHistoryType.USAGE, historyCaptor.getValue().getType());
	}

	@Test
	@DisplayName("사용 실패 - 잔액 부족")
	void fail_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 2000;
		UserBalance balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(UserBalanceHistory.class));
		assertThrows(IllegalArgumentException.class, () -> balanceService.use(balance.getUserId(), useAmount));
	}

	private UserBalance prepareBalance(long amount) {
		return UserBalance.builder().userId(UUIDGenerator.generate()).amount(amount).build();
	}

	private UserBalance prepareNewBalance(UUID userId) {
		return UserBalance.newBalance(userId);
	}

	private void mockRepositoryFindById(UserBalance balance) {
		when(userBalanceRepository.findByUserId(balance.getUserId())).thenReturn(balance);
	}

	private void mockRepositorySaveMethods() {
		when(userBalanceRepository.save(any(UserBalance.class))).thenReturn(null);
		when(historyRepository.save(any(UserBalanceHistory.class))).thenReturn(null);
	}
}
