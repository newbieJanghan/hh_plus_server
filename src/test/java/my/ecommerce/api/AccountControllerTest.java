package my.ecommerce.api;

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

import my.ecommerce.api.config.interceptor.LoggerInterceptor;
import my.ecommerce.api.controller.AccountController;
import my.ecommerce.api.utils.MockAuthentication;
import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountService;
import my.ecommerce.utils.UUIDGenerator;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	@Captor
	ArgumentCaptor<Long> amountCaptor;

	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).addInterceptors(
			new LoggerInterceptor()).build();
		MockAuthentication.setAuthenticatedContext();
	}

	@Test
	@DisplayName("잔액 조회 성공")
	public void myAccount_success() throws Exception {
		when(accountService.myAccount(any(UUID.class))).thenReturn(emptyAccount());

		mockMvc.perform(get("/api/v1/account"))
			.andExpect(status().isOk());

		verify(accountService, times(1)).myAccount(any(UUID.class));
	}

	@Test
	@DisplayName("잔액 충전 성공")
	public void charge_success() throws Exception {
		long amount = 1000;

		when(accountService.charge(any(UUID.class), anyLong())).thenReturn(emptyAccount());

		JSONObject body = new JSONObject();
		body.put("amount", amount);

		mockMvc.perform(patch("/api/v1/account/charge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString()))
			.andExpect(status().isOk());

		verify(accountService).charge(any(UUID.class), amountCaptor.capture());
		assertEquals(amount, amountCaptor.getValue());
	}

	@Test
	@DisplayName("잔액 충전 실패 - 충전 금액이 0 이하")
	public void charge_fail_amountIsZero() throws Exception {
		long amount = 0;

		JSONObject body = new JSONObject();
		body.put("amount", amount);

		mockMvc.perform(patch("/api/v1/account/charge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString()))
			.andExpect(status().isBadRequest());
	}

	private Account emptyAccount() {
		return Account.newAccount(UUIDGenerator.generate());
	}
}
