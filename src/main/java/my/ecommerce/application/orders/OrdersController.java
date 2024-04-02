package my.ecommerce.application.orders;

import my.ecommerce.application.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.orders.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("")
    public OrderResponseDto createOrder(@RequestHeader String authorization, @RequestBody CreateOrderRequestDto requestDto) {
        return ordersService.create(parseUserId(authorization), requestDto);
    }

    private long parseUserId(String userIdFromHeader) {
        return Long.parseLong(userIdFromHeader);
    }
}
