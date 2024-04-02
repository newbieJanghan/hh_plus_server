package my.ecommerce.application.balance;

import my.ecommerce.application.balance.dto.BalanceResponseDto;
import my.ecommerce.application.balance.dto.ChargeRequestDto;
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
        return balanceService.getOne(parseUserId(authorization));
    }

    @PostMapping("/charge")
    public BalanceResponseDto charge(@RequestHeader String authorization, @RequestBody ChargeRequestDto chargeRequestDto) {
        return balanceService.charge(parseUserId(authorization), chargeRequestDto.getAmount());
    }

    private long parseUserId(String userIdFromHeader) {
        return Long.parseLong(userIdFromHeader);
    }
}
