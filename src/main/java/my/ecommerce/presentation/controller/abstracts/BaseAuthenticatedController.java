package my.ecommerce.presentation.controller.abstracts;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import my.ecommerce.domain.user.User;

public abstract class BaseAuthenticatedController {
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User)authentication.getPrincipal();
	}
}
