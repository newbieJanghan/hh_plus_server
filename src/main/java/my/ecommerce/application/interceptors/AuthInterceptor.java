package my.ecommerce.application.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@NoArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authToken = request.getHeader("Authorization");
        System.out.println("authToken: " + authToken);
        if (authToken == null) {
            throw new IllegalArgumentException("Authorization header is missing");
        }
        try {
            System.out.println("userId: " + Long.parseLong(authToken));
            request.setAttribute("userId", Long.parseLong(authToken));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        return true;
    }
}
