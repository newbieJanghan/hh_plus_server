package my.ecommerce.application.balance;

import my.ecommerce.application.balance.request.ChargeRequestDto;
import my.ecommerce.application.balance.response.BalanceResponseDto;
import my.ecommerce.domain.balance.Balance;
import my.ecommerce.domain.balance.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("")
    public BalanceResponseDto myBalance(@RequestHeader String authorization) {
        Balance balance = balanceService.findOne(parseUserId(authorization));
        return respond(balance);
    }

    @PostMapping("/charge")
    public BalanceResponseDto charge(@RequestHeader String authorization, @RequestBody ChargeRequestDto chargeRequestDto) {
        Balance balance = balanceService.charge(parseUserId(authorization), chargeRequestDto.getAmount());
        return respond(balance);
    }

    private long parseUserId(String userIdFromHeader) {
        return Long.parseLong(userIdFromHeader);
    }

    private BalanceResponseDto respond(Balance balance) {
        return BalanceResponseDto.builder().data(balance).build();
    }
}
