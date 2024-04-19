package my.ecommerce.api.base;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import my.ecommerce.api.config.security.AuthenticationFilter;
import my.ecommerce.api.controller.AccountController;
import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountService;
import my.ecommerce.utils.UUIDGenerator;

@WebMvcTest(AccountController.class)
public class BaseControllerTest {
	@Captor
	ArgumentCaptor<UUID> userIdCaptor;
	private MockMvc mockMvc;
	@MockBean
	private AccountService accountService;

	@BeforeEach
	void setMockMvc() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService))
			.addFilter(new AuthenticationFilter())
			.alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
			.alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
			.build();
	}

	@Test
	@DisplayName("Authorization 토큰으로 유저 객체 획득")
	public void getAuthenticatedUser() throws Exception {
		// given
		UUID userId = UUIDGenerator.generate();
		when(accountService.myAccount(userId)).thenReturn(Account.newAccount(userId));

		// when
		mockMvc.perform(get("/api/v1/account").header("Authorization", userId));

		// then
		verify(accountService).myAccount(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());

	}

	@Test
	@DisplayName("Authorization 토큰 없음")
	public void noAuthorizationToken() throws Exception {
		mockMvc.perform(get("/api/v1/account"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Authorization header is missing"));
	}

	@Test
	@DisplayName("Authorization 토큰이 유효하지 않은 경우")
	public void invalidAuthorizationToken() throws Exception {
		mockMvc.perform(get("/api/v1/account").header("Authorization", "가나다"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Invalid token"));
	}
}
