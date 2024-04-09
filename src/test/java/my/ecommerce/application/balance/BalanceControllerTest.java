package my.ecommerce.application.balance;

import my.ecommerce.application.api.balance.BalanceController;
import my.ecommerce.application.api.balance.BalanceService;
import my.ecommerce.application.api.balance.dto.BalanceResponseDto;
import my.ecommerce.application.utils.MockAuthentication;
import my.ecommerce.domain.balance.Balance;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(balanceService.myBalance(any(UUID.class))).thenReturn(emptyResponseDto());

        mockMvc.perform(get("/api/v1/balance"))
                .andExpect(status().isOk());

        verify(balanceService, times(1)).myBalance(any(UUID.class));
    }

    @Test
    @DisplayName("잔액 충전 성공")
    public void charge_success() throws Exception {
        long amount = 1000;

        when(balanceService.charge(any(UUID.class), anyLong())).thenReturn(emptyResponseDto());

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

    private BalanceResponseDto emptyResponseDto() {
        return new BalanceResponseDto(new Balance());
    }
}
