package my.ecommerce.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class ApiControllerTest extends AbstractControllerTest {
    @BeforeEach
    void setMockMvc() {
        buildConfiguredMockMvc(new TestController());
    }

    @Test
    @DisplayName("Authorization 토큰으로 유저 객체 획득")
    @WithMockUser
    public void getAuthenticatedUser() throws Exception {
        long userId = 1;
        mockMvc.perform(get("/test/auth").header("Authorization", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }
}
