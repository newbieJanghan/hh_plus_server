package my.ecommerce.application.balance;

import my.ecommerce.application.Response;
import my.ecommerce.application.balance.request.ChargeRequestDto;
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
    public Response<Balance> myBalance(@RequestHeader String authorization) {
        return new Response<>("ok", balanceService.findOne(parseUserId(authorization)));
    }

    @PostMapping("/charge")
    public Response<Balance> charge(@RequestHeader String authorization, @RequestBody ChargeRequestDto chargeRequestDto) {
        System.out.println(chargeRequestDto);
        return new Response<>("ok", balanceService.charge(parseUserId(authorization), chargeRequestDto.getAmount()));
    }

    private long parseUserId(String userIdFromHeader) {
        return Long.parseLong(userIdFromHeader);
    }
}
