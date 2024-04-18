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

import my.ecommerce.domain.balance.BalanceService;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.presentation.controller.abstracts.BaseAuthenticatedController;
import my.ecommerce.presentation.dto.request.BalanceChargeRequest;
import my.ecommerce.presentation.dto.response.BalanceResponse;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController extends BaseAuthenticatedController {

	private final BalanceService balanceService;

	@Autowired
	public BalanceController(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public BalanceResponse myBalance() {
		UserBalance userBalance = balanceService.myBalance(getAuthenticatedUser().getId());
		return BalanceResponse.fromDomain(userBalance);
	}

	@PatchMapping("/charge")
	@ResponseStatus(HttpStatus.OK)
	public BalanceResponse charge(@RequestBody @Validated BalanceChargeRequest balanceChargeRequest) {
		UserBalance userBalance = balanceService.charge(getAuthenticatedUser().getId(),
			balanceChargeRequest.getAmount());
		return BalanceResponse.fromDomain(userBalance);
	}
}
