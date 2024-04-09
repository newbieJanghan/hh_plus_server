package my.ecommerce.application;

import jakarta.persistence.EntityNotFoundException;
import my.ecommerce.application.api.test.ApiTestController;
import my.ecommerce.application.api.test.ApiTestService;
import my.ecommerce.application.utils.MockAuthentication;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiTestController.class)
public class ControllerAdviceTest {
    private MockMvc mockMvc;
    @MockBean
    private ApiTestService apiTestService;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ApiTestController(apiTestService))
                .setControllerAdvice(ApiControllerAdvice.class)
                .alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
                .alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
                .build();
        MockAuthentication.setAuthenticatedContext();
    }

    @Test
    @DisplayName("Runtime Exception 발생 시 500 응답")
    public void runtimeException() throws Exception {
        doThrow(new RuntimeException("test")).when(apiTestService).exception();

        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message").value("test"));
    }

    @Test
    @DisplayName("BadRequestException 발생 시 400 응답")
    public void badRequestException() throws Exception {
        doThrow(new BadRequestException("test")).when(apiTestService).exception();

        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("test"));
    }

    @Test
    @DisplayName("IllegalArgumentException 발생 시 400 응답")
    public void illegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException("test")).when(apiTestService).exception();

        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("test"));
    }

    @Test
    @DisplayName("EntityNotFoundException 발생 시 404 응답")
    public void entityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("test")).when(apiTestService).exception();

        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("test"));
    }
}
