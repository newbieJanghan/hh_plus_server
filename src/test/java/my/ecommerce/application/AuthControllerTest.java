package my.ecommerce.application;

import my.ecommerce.application.abstracts.AuthenticatedControllerTest;
import my.ecommerce.application.api.AuthController;
import my.ecommerce.utils.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends AuthenticatedControllerTest {
    @BeforeEach
    void setMockMvc() {
        buildAuthConfiguredMockMvc(new AuthController());
    }

    @Test
    @DisplayName("Authorization 토큰으로 유저 객체 획득")
    public void getAuthenticatedUser() throws Exception {
        UUID userId = UUIDGenerator.generate();
        mockMvc.perform(get("/auth").header("Authorization", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()));
    }

    @Test
    @DisplayName("Authorization 토큰 없음")
    public void noAuthorizationToken() throws Exception {
        mockMvc.perform(get("/auth"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Authorization header is missing"));
    }

    @Test
    @DisplayName("Authorization 토큰이 유효하지 않은 경우")
    public void invalidAuthorizationToken() throws Exception {
        mockMvc.perform(get("/auth").header("Authorization", "가나다"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Invalid token"));
    }
}
