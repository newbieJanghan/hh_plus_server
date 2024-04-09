package my.ecommerce.application.utils;

import my.ecommerce.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class MockAuthentication {
    public static void setAuthenticatedContext() {
        User mockUser = User.builder().id(UUID.randomUUID()).build();
        org.springframework.security.core.Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(mockUser, null);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(mockAuthentication);
        SecurityContextHolder.setContext(context);
    }
}
