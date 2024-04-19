package my.ecommerce.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountService;
import my.ecommerce.presentation.controller.abstracts.BaseAuthenticatedController;
import my.ecommerce.presentation.dto.request.BalanceChargeRequest;
import my.ecommerce.presentation.dto.response.BalanceResponse;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController extends BaseAuthenticatedController {

	private final AccountService accountService;

	@Autowired
	public BalanceController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public BalanceResponse myBalance() {
		Account account = accountService.myBalance(getAuthenticatedUser().getId());
		return BalanceResponse.fromDomain(account);
	}

	@PatchMapping("/charge")
	@ResponseStatus(HttpStatus.OK)
	public BalanceResponse charge(@RequestBody @Validated BalanceChargeRequest balanceChargeRequest) {
		Account account = accountService.charge(getAuthenticatedUser().getId(),
			balanceChargeRequest.getAmount());
		return BalanceResponse.fromDomain(account);
	}
}
