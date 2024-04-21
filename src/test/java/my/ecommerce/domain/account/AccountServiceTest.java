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
	private ArgumentCaptor<Account> accountCaptor;
	@Captor
	private ArgumentCaptor<AccountHistory> historyCaptor;

	private AccountService accountService;

	@BeforeEach
	void setAccountService() {
		openMocks(this);
		accountService = new AccountService(accountRepository, historyRepository);
	}

	@Test
	@DisplayName("Account 조회 성공")
	void success_myAccount() {
		// given
		Account account = prepareAccount(1000);
		mockRepositoryFindById(account);

		// when
		Account result = accountService.myAccount(account.getUserId());

		// then
		assertEquals(account.getUserId(), result.getUserId());
		assertEquals(account.getAmount(), result.getAmount());
	}

	@Test
	@DisplayName("Account 조회 성공 - 새로운 Account 생성")
	void success_withNewAccount() {
		// given
		UUID userId = UUIDGenerator.generate();
		Account newAccount = prepareNewAccount(userId);

		when(accountRepository.findByUserId(userId)).thenReturn(null);
		when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

		// when
		Account result = accountService.myAccount(userId);

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
		Account account = prepareAccount(currentAmount);

		mockRepositoryFindById(account);
		mockRepositorySaveMethods();

		// when
		accountService.charge(account.getUserId(), chargeAmount);

		// then
		verify(accountRepository).save(accountCaptor.capture());
		assertEquals(currentAmount + chargeAmount, accountCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(AccountHistory.TransactionType.CHARGE, historyCaptor.getValue().getType());
		assertEquals(chargeAmount, historyCaptor.getValue().getAmount());

	}

	@Test
	@DisplayName("충전 실패 - 유효하지 않은 충전 금액")
	void fail_charge() {
		// given
		long currentAmount = 1000;
		long chargeAmount = -1000;
		Account account = prepareAccount(currentAmount);

		mockRepositoryFindById(account);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(AccountHistory.class));
		assertThrows(IllegalArgumentException.class, () -> accountService.charge(account.getUserId(), chargeAmount));
	}

	@Test
	@DisplayName("사용 성공")
	void success_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 1000;
		Account account = prepareAccount(currentAmount);

		mockRepositoryFindById(account);
		mockRepositorySaveMethods();

		// when
		accountService.use(account.getUserId(), useAmount);

		// then
		verify(accountRepository).save(accountCaptor.capture());
		assertEquals(currentAmount - useAmount, accountCaptor.getValue().getAmount());

		verify(historyRepository).save(historyCaptor.capture());
		assertEquals(AccountHistory.TransactionType.USAGE, historyCaptor.getValue().getType());
	}

	@Test
	@DisplayName("사용 실패 - 잔액 부족")
	void fail_use() {
		// given
		long currentAmount = 1000;
		long useAmount = 2000;
		Account account = prepareAccount(currentAmount);

		mockRepositoryFindById(account);
		mockRepositorySaveMethods();

		// when & then
		verify(historyRepository, never()).save(any(AccountHistory.class));
		assertThrows(IllegalArgumentException.class, () -> accountService.use(account.getUserId(), useAmount));
	}

	private Account prepareAccount(long amount) {
		return Account.builder().userId(UUIDGenerator.generate()).amount(amount).build();
	}

	private Account prepareNewAccount(UUID userId) {
		return Account.newAccount(userId);
	}

	private void mockRepositoryFindById(Account account) {
		when(accountRepository.findByUserId(account.getUserId())).thenReturn(account);
	}

	private void mockRepositorySaveMethods() {
		when(accountRepository.save(any(Account.class))).thenReturn(null);
		when(historyRepository.save(any(AccountHistory.class))).thenReturn(null);
	}
}
