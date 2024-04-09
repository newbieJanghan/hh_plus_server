package my.ecommerce.application.api.test;

import my.ecommerce.application.api.abstracts.BaseAuthenticatedController;
import my.ecommerce.domain.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO Test 환경에서만 mvc 요청이 들어올 수 있도록 수정해야 함.
 */
@RestController()
@RequestMapping("/test")
public class ApiTestController extends BaseAuthenticatedController {
    private final ApiTestService apiTestService;

    public ApiTestController(ApiTestService apiTestService) {
        this.apiTestService = apiTestService;
    }

    @GetMapping("/exception")
    public void exception() throws Exception {
        apiTestService.exception();
    }

    @GetMapping("/auth")
    public User auth() {
        return getAuthenticatedUser();
    }
}
