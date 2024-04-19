package my.ecommerce.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.ecommerce.domain.user.User;
import my.ecommerce.api.controller.abstracts.BaseAuthenticatedController;

/**
 * TODO Test 환경에서만 mvc 요청이 들어올 수 있도록 수정해야 함.
 */
@RestController()
@RequestMapping("/test")
public class ApiTestController extends BaseAuthenticatedController {
	private final TestService testService;

	public ApiTestController(TestService testService) {
		this.testService = testService;
	}

	@GetMapping("/exception")
	public void exception() throws Exception {
		testService.exception();
	}

	@GetMapping("/auth")
	public User auth() {
		return getAuthenticatedUser();
	}
}
