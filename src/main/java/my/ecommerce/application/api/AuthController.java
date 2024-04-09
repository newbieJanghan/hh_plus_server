package my.ecommerce.application.api;

import my.ecommerce.application.api.abstracts.BaseAuthenticatedController;
import my.ecommerce.domain.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseAuthenticatedController {
    @RequestMapping("")
    public User testAuthentication() {
        return this.getAuthenticatedUser();
    }
}
