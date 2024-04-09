package my.ecommerce.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import my.ecommerce.domain.user.User;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    public AuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = parseUser(httpRequest);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null);

        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);

        chain.doFilter(httpRequest, response);
    }

    private User parseUser(HttpServletRequest httpRequest) throws BadRequestException {
        String authToken = httpRequest.getHeader("Authorization");
        if (authToken == null) {
            throw new BadRequestException("Authorization header is missing");
        }
        try {
            long userId = Long.parseLong(authToken);
            return User.builder().id(userId).build();
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid auth token", e);
        }


    }
}
