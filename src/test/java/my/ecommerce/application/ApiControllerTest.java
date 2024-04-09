package my.ecommerce.application;

import my.ecommerce.application.common.AuthenticatedControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class ApiControllerTest extends AuthenticatedControllerTest {
    @BeforeEach
    void setMockMvc() {
        buildAuthConfiguredMockMvc(new TestController());
    }

    @Test
    @DisplayName("Authorization 토큰으로 유저 객체 획득")
    public void getAuthenticatedUser() throws Exception {
        long userId = 1;
        mockMvc.perform(get("/test/auth").header("Authorization", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    @DisplayName("Authorization 토큰 없음")
    public void noAuthorizationToken() throws Exception {
        mockMvc.perform(get("/test/auth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Authorization header is missing"));
    }

    @Test
    @DisplayName("Authorization 토큰이 숫자가 아닌 경우")
    public void invalidAuthorizationToken() throws Exception {
        mockMvc.perform(get("/test/auth").header("Authorization", "가나다"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Invalid userId"));
    }
}
