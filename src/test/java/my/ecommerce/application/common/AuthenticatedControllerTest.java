package my.ecommerce.application.common;

import my.ecommerce.application.config.security.AuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AuthenticatedControllerTest {
    protected MockMvc mockMvc;

    protected void buildAuthConfiguredMockMvc(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ApiControllerAdvice.class)
                .addFilter(new AuthenticationFilter())
                .alwaysDo(result -> System.out.println("status: " + result.getResponse().getStatus()))
                .alwaysDo(result -> System.out.println("content: " + result.getResponse().getContentAsString()))
                .build();
    }

}
