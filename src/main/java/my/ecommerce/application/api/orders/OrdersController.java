package my.ecommerce.application.api.orders;

import jakarta.validation.Valid;
import my.ecommerce.application.api.abstracts.BaseAuthenticatedController;
import my.ecommerce.application.api.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.api.orders.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController extends BaseAuthenticatedController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("")
    public OrderResponseDto createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        return ordersService.create(getAuthenticatedUser().getId(), requestDto);
    }
}
