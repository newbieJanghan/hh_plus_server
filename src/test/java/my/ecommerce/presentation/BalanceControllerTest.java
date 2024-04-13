package my.ecommerce.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

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

import my.ecommerce.domain.balance.BalanceService;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.presentation.controller.BalanceController;
import my.ecommerce.presentation.utils.MockAuthentication;

@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {
	@Captor
	ArgumentCaptor<Long> amountCaptor;

	private MockMvc mockMvc;

	@MockBean
	private BalanceService balanceService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BalanceController(balanceService)).build();
		MockAuthentication.setAuthenticatedContext();
	}

	@Test
	@DisplayName("잔액 조회 성공")
	public void myBalance_success() throws Exception {
		when(balanceService.myBalance(any(UUID.class))).thenReturn(emptyUserBalance());

		mockMvc.perform(get("/api/v1/balance"))
			.andExpect(status().isOk());

		verify(balanceService, times(1)).myBalance(any(UUID.class));
	}

	@Test
	@DisplayName("잔액 충전 성공")
	public void charge_success() throws Exception {
		long amount = 1000;

		when(balanceService.charge(any(UUID.class), anyLong())).thenReturn(emptyUserBalance());

		JSONObject body = new JSONObject();
		body.put("amount", amount);

		mockMvc.perform(patch("/api/v1/balance/charge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString()))
			.andExpect(status().isOk());

		verify(balanceService).charge(any(UUID.class), amountCaptor.capture());
		assertEquals(amount, amountCaptor.getValue());
	}

	@Test
	@DisplayName("잔액 충전 실패 - 충전 금액이 0 이하")
	public void charge_fail_amountIsZero() throws Exception {
		long amount = 0;

		JSONObject body = new JSONObject();
		body.put("amount", amount);

		mockMvc.perform(patch("/api/v1/balance/charge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString()))
			.andExpect(status().isBadRequest());
	}

	private UserBalance emptyUserBalance() {
		return UserBalance.newBalance(UUID.randomUUID(), 0);
	}
}
