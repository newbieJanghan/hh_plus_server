package my.ecommerce.domain.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final AccountHistoryRepository historyRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository, AccountHistoryRepository historyRepository) {
		this.accountRepository = accountRepository;
		this.historyRepository = historyRepository;

	}

	public Account myBalance(UUID userId) {
		Account balance = accountRepository.findByUserId(userId);
		if (balance != null) {
			return balance;
		}

		return accountRepository.save(Account.newAccount(userId));
	}

	public Account charge(UUID userId, long amount) {
		validateCharge(amount);

		Account balance = myBalance(userId);
		balance.charge(amount);

		Account persisted = accountRepository.save(balance);

		AccountHistory chargeHistory = AccountHistory.newChargeHistory(balance, amount);
		historyRepository.save(chargeHistory);

		return persisted;
	}

	public Account use(UUID userId, long amount) {
		Account balance = myBalance(userId);

		validateUsage(balance, amount);
		balance.use(amount);

		Account persisted = accountRepository.save(balance);

		AccountHistory usageHistory = AccountHistory.newUsageHistory(balance, amount);
		historyRepository.save(usageHistory);

		return persisted;
	}

	private void validateCharge(long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
		}
	}

	private void validateUsage(Account balance, long amount) {
		if (balance.getAmount() < amount) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
	}
}
