package my.ecommerce.domain.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

	@Transactional
	public Account charge(UUID accountId, long amount) {
		validateCharge(amount);

		Account account = prepareAccountForUpdate(accountId);
		account.charge(amount);
		account = accountRepository.save(account);

		AccountHistory chargeHistory = AccountHistory.newChargeHistory(account, amount);
		historyRepository.save(chargeHistory);

		return account;
	}

	@Transactional
	public Account use(UUID accountId, long amount) {
		Account account = prepareAccountForUpdate(accountId);

		validateUsage(account, amount);
		account.use(amount);
		account = accountRepository.save(account);

		AccountHistory usageHistory = AccountHistory.newUsageHistory(account, amount);
		historyRepository.save(usageHistory);

		return account;
	}

	public Account prepareAccountForUpdate(UUID accountId) {
		Account account = accountRepository.findByIdForUpdate(accountId);
		if (account == null) {
			throw new IllegalArgumentException("계좌가 존재하지 않습니다.");
		}

		return account;
	}

	private void validateCharge(long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
		}
	}

	private void validateUsage(Account account, long amount) {
		if (account.getBalance() < amount) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
	}
}
