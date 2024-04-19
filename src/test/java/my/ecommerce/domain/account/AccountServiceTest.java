package my.ecommerce.domain.account;

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

import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;
import my.ecommerce.utils.UUIDGenerator;

public class AccountServiceTest {
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private AccountHistoryRepository historyRepository;

	@Captor
	private ArgumentCaptor<Account> balanceCaptor;
	@Captor
	private ArgumentCaptor<AccountHistory> historyCaptor;

	private AccountService accountService;

	@BeforeEach
	void setBalanceService() {
		openMocks(this);
		accountService = new AccountService(accountRepository, historyRepository);
	}

	@Test
	@DisplayName("UserBalance 조회 성공")
	void success_myBalance() {
		// given
		Account balance = prepareBalance(1000);
		mockRepositoryFindById(balance);

		// when
		Account result = accountService.myBalance(balance.getUserId());

		// then
		assertEquals(balance.getUserId(), result.getUserId());
		assertEquals(balance.getAmount(), result.getAmount());
	}

	@Test
	@DisplayName("UserBalance 조회 성공 - 새로운 UserBalance 생성")
	void success_withNewBalance() {
		// given
		UUID userId = UUIDGenerator.generate();
		Account newAccount = prepareNewBalance(userId);

		when(accountRepository.findByUserId(userId)).thenReturn(null);
		when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

		// when
		Account result = accountService.myBalance(userId);

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
		Account balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when
		accountService.charge(balance.getUserId(), chargeAmount);

		// then
		verify(accountRepository).save(balanceCaptor.capture());
		assertEquals(currentAmount + chargeAmount, balanceCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(AccountHistory.BalanceHistoryType.CHARGE, historyCaptor.getValue().getType());
		assertEquals(chargeAmount, historyCaptor.getValue().getAmount());

	}

	@Test
	@DisplayName("충전 실패 - 유효하지 않은 충전 금액")
	void fail_charge() {
		// given
		long currentAmount = 1000;
		long chargeAmount = -1000;
		Account balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(AccountHistory.class));
		assertThrows(IllegalArgumentException.class, () -> accountService.charge(balance.getUserId(), chargeAmount));
	}

	@Test
	@DisplayName("사용 성공")
	void success_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 1000;
		Account balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when
		accountService.use(balance.getUserId(), useAmount);

		// then
		verify(accountRepository).save(balanceCaptor.capture());
		assertEquals(currentAmount - useAmount, balanceCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(AccountHistory.BalanceHistoryType.USAGE, historyCaptor.getValue().getType());
	}

	@Test
	@DisplayName("사용 실패 - 잔액 부족")
	void fail_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 2000;
		Account balance = prepareBalance(currentAmount);

		mockRepositoryFindById(balance);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(AccountHistory.class));
		assertThrows(IllegalArgumentException.class, () -> accountService.use(balance.getUserId(), useAmount));
	}

	private Account prepareBalance(long amount) {
		return Account.builder().userId(UUIDGenerator.generate()).amount(amount).build();
	}

	private Account prepareNewBalance(UUID userId) {
		return Account.newAccount(userId);
	}

	private void mockRepositoryFindById(Account balance) {
		when(accountRepository.findByUserId(balance.getUserId())).thenReturn(balance);
	}

	private void mockRepositorySaveMethods() {
		when(accountRepository.save(any(Account.class))).thenReturn(null);
		when(historyRepository.save(any(AccountHistory.class))).thenReturn(null);
	}
}
