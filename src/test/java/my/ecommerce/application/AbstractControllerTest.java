package my.ecommerce.application;

import my.ecommerce.application.common.ApiControllerAdvice;
import my.ecommerce.config.security.AuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class AbstractControllerTest {
    protected MockMvc mockMvc;

    void buildConfiguredMockMvc(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ApiControllerAdvice.class)
                .addFilter(new AuthenticationFilter())
                .alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
                .alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
                .build();
    }

}
