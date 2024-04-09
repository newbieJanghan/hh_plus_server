package my.ecommerce.application;

import my.ecommerce.application.common.BaseAuthenticatedController;
import my.ecommerce.domain.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController extends BaseAuthenticatedController {
    @RequestMapping("/auth")
    public User testAuthentication() {
        return this.getAuthenticatedUser();
    }
}
