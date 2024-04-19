package my.ecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import my.ecommerce.api.controller.abstracts.BaseAuthenticatedController;
import my.ecommerce.api.dto.request.AccountChargeRequest;
import my.ecommerce.api.dto.response.AccountResponse;
import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController extends BaseAuthenticatedController {

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public AccountResponse myAccount() {
		Account account = accountService.myAccount(getAuthenticatedUser().getId());
		return AccountResponse.fromDomain(account);
	}

	@PatchMapping("/charge")
	@ResponseStatus(HttpStatus.OK)
	public AccountResponse charge(@RequestBody @Validated AccountChargeRequest accountChargeRequest) {
		Account account = accountService.charge(getAuthenticatedUser().getId(),
			accountChargeRequest.getAmount());
		return AccountResponse.fromDomain(account);
	}
}
