package my.ecommerce.application;

import my.ecommerce.application.api.AuthController;
import my.ecommerce.application.security.AuthenticationFilter;
import my.ecommerce.utils.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController())
                .addFilter(new AuthenticationFilter())
                .alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
                .alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
                .build();
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
