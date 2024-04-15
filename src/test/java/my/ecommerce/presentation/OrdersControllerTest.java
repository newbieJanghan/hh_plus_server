package my.ecommerce.presentation;

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

import my.ecommerce.application.OrderApplication;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.presentation.controller.OrdersController;
import my.ecommerce.presentation.request.OrderCreateRequest;
import my.ecommerce.presentation.utils.MockAuthentication;
import my.ecommerce.utils.UUIDGenerator;

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
	@Captor
	ArgumentCaptor<OrderCreateRequest> createRequestDtoCaptor;

	private MockMvc mockMvc;

	@MockBean
	private OrderApplication orderApplication;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new OrdersController(orderApplication))
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
		when(orderApplication.run(any(CreateOrderDto.class))).thenReturn(Order.newOrder(userId, 0));

		// then
		mockMvc.perform(post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createRequestContent()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userId").value(userId.toString()))
		;

		verify(orderApplication, times(1)).run(any(CreateOrderDto.class));
	}

	@Test
	@DisplayName("주문 실패 - 요청 데이터가 누락된 경우")
	public void order_fail_whenInvalidRequest_thenThrow_BadRequestException() throws Exception {
		// then
		mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content("{}"))
			.andExpect(
				result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

		verify(orderApplication, never()).run(any(CreateOrderDto.class));
	}

	private String createRequestContent() throws JSONException {
		return mockCreateRequestJson().toString();
	}

	private JSONObject mockCreateRequestJson() throws JSONException {
		JSONObject body = new JSONObject();

		body.put("items", mockOrderItemsRequestJson());
		body.put("totalPrice", 10);

		return body;
	}

	private JSONArray mockOrderItemsRequestJson() throws JSONException {
		JSONArray orderItems = new JSONArray();
		UUID productId = UUIDGenerator.generate();
		orderItems.put(new JSONObject().put("productId", productId).put("quantity", 1));

		return orderItems;
	}
}
