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

    private BalanceService service;

    @Autowired
    public BalanceController(BalanceService service) {
        this.service = service;
    }

    @GetMapping("")
    public Response<Balance> getBalance(@RequestHeader String authorization) {
        return new Response<>("ok", service.getBalance(parseUserId(authorization)));
    }

    @PostMapping("/charge")
    public Response<Balance> charge(@RequestHeader String authorization, @RequestBody ChargeRequestDto chargeRequestDto) {
        System.out.println(chargeRequestDto);
        return new Response<>("ok", service.charge(parseUserId(authorization), chargeRequestDto.getAmount()));
    }

    private long parseUserId(String userIdFromHeader) {
        return Long.parseLong(userIdFromHeader);
    }
}
