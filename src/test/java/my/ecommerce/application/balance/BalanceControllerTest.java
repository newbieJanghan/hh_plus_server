package my.ecommerce.application.balance;

import my.ecommerce.Mocks;
import my.ecommerce.domain.balance.BalanceService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private BalanceService balanceService;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BalanceController(balanceService)).build();

    }

    @Test
    @DisplayName("잔액 조회 성공")
    @WithMockUser
    public void findBalance_success() throws Exception {
        long userId = 1;
        long amount = 1000;
        when(balanceService.findOne(userId))
                .thenReturn(Mocks.mockBalance(amount));

        mockMvc
                .perform(get("/api/v1/balance").header("Authorization", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.amount").value(amount));
    }

    @Test
    @DisplayName("잔액 충전 성공")
    @WithMockUser
    public void charge_success() throws Exception {
        long userId = 1;
        long amount = 1000;
        when(balanceService.charge(userId, amount))
                .thenReturn(Mocks.mockBalance(amount));

        JSONObject body = new JSONObject();
        body.put("amount", amount);

        mockMvc
                .perform(
                        post("/api/v1/balance/charge")
                                .header("Authorization", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.amount").value(amount));
    }
}
