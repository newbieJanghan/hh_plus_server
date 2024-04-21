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

	public Account myAccount(UUID userId) {
		Account account = accountRepository.findByUserId(userId);
		if (account != null) {
			return account;
		}

		return accountRepository.save(Account.newAccount(userId));
	}

	public Account charge(UUID userId, long amount) {
		validateCharge(amount);

		Account account = myAccount(userId);
		account.charge(amount);

		AccountHistory chargeHistory = AccountHistory.newChargeHistory(account, amount);
		historyRepository.save(chargeHistory);

		return accountRepository.save(account);
	}

	public Account use(UUID userId, long amount) {
		Account account = myAccount(userId);

		validateUsage(account, amount);
		account.use(amount);

		AccountHistory usageHistory = AccountHistory.newUsageHistory(account, amount);
		historyRepository.save(usageHistory);

		return accountRepository.save(account);
	}

	private void validateCharge(long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
		}
	}

	private void validateUsage(Account account, long amount) {
		if (account.getAmount() < amount) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
	}
}
