package my.ecommerce.application.orders;

import my.ecommerce.application.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.orders.dto.OrderResponseDto;
import my.ecommerce.domain.order.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
    @Captor
    ArgumentCaptor<Long> userIdCaptor;
    @Captor
    ArgumentCaptor<CreateOrderRequestDto> createRequestDtoCaptor;
    private MockMvc mockMvc;
    @MockBean
    private OrdersService orderService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrdersController(orderService)).build();
    }

    @Test
    @DisplayName("주문 성공")
    @WithMockUser
    public void order_success() throws Exception {
        // given
        long userId = 1;

        // when
        when(orderService.create(anyLong(), any(CreateOrderRequestDto.class))).thenReturn(mockOrderResponseDto());

        // then
        mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCreateRequestJson(1, 1, 1).toString()))
                .andExpect(status().isOk());

        verify(orderService).create(userIdCaptor.capture(), createRequestDtoCaptor.capture());
        assertEquals(userId, userIdCaptor.getValue());
        assertEquals(1, createRequestDtoCaptor.getValue().getItems().getFirst().getProductId());
    }

    private JSONObject mockCreateRequestJson(long productId, long quantity, long totalPrice) throws JSONException {
        JSONObject body = new JSONObject();

        body.put("items", mockOrderItemsRequestJson(productId, quantity));
        body.put("totalPrice", totalPrice);

        return body;
    }

    private JSONArray mockOrderItemsRequestJson(long productId, long quantity) throws JSONException {
        JSONArray orderItems = new JSONArray();
        orderItems.put(new JSONObject().put("productId", productId).put("quantity", quantity));

        return orderItems;
    }


    private OrderResponseDto mockOrderResponseDto() {
        return new OrderResponseDto(new Order());
    }
}
