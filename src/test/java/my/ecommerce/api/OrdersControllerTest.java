package my.ecommerce.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import my.ecommerce.api.controller.OrdersController;
import my.ecommerce.api.dto.request.OrderCreateRequest;
import my.ecommerce.api.utils.MockAuthentication;
import my.ecommerce.domain.order.Order;
import my.ecommerce.usecase.order.OrderCommand;
import my.ecommerce.usecase.order.OrderUseCase;
import my.ecommerce.utils.UUIDGenerator;

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
	@Captor
	ArgumentCaptor<OrderCreateRequest> createRequestDtoCaptor;

	private MockMvc mockMvc;

	@MockBean
	private OrderUseCase orderUseCase;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new OrdersController(orderUseCase))
			.alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
			.alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
			.build();
		MockAuthentication.setAuthenticatedContext();
	}

	@Test
	@DisplayName("주문 성공")
	public void order_success() throws Exception {
		// when
		UUID userId = UUIDGenerator.generate();
		when(orderUseCase.run(any(OrderCommand.class))).thenReturn(Order.newOrder(userId));

		// then
		mockMvc.perform(post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createRequestContent()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userId").value(userId.toString()))
		;

		verify(orderUseCase, times(1)).run(any(OrderCommand.class));
	}

	@Test
	@DisplayName("주문 실패 - 요청 데이터가 누락된 경우")
	public void order_fail_whenInvalidRequest_thenThrow_BadRequestException() throws Exception {
		// then
		mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content("{}"))
			.andExpect(
				result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

		verify(orderUseCase, never()).run(any(OrderCommand.class));
	}

	private String createRequestContent() throws JSONException {
		return mockCreateRequestJson().toString();
	}

	private JSONObject mockCreateRequestJson() throws JSONException {
		JSONObject order = new JSONObject();

		JSONArray orderItems = new JSONArray();
		orderItems.put(new JSONObject().put("productId", UUIDGenerator.generate()).put("quantity", 1));

		order.put("items", orderItems);

		return order;
	}
}
