package my.ecommerce.application.api.balance;

import my.ecommerce.application.api.abstracts.BaseAuthenticatedController;
import my.ecommerce.application.api.balance.dto.BalanceResponseDto;
import my.ecommerce.application.api.balance.dto.ChargeBalanceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public BalanceResponseDto myBalance() {
        return balanceService.myBalance(getAuthenticatedUser().getId());
    }

    @PatchMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public BalanceResponseDto charge(@RequestBody @Validated ChargeBalanceRequestDto chargeBalanceRequestDto) {
        return balanceService.charge(getAuthenticatedUser().getId(), chargeBalanceRequestDto.getAmount());
    }
}
