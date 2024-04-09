package my.ecommerce.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.ecommerce.application.ErrorResponse;
import my.ecommerce.domain.user.User;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.UUID;

public class AuthenticationFilter extends GenericFilterBean {

    public AuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            User user = parseUser(httpRequest);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null);

            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);

            chain.doFilter(httpRequest, httpResponse);
        } catch (BadRequestException exception) {
            handleException(httpResponse, exception);
        }

    }

    private User parseUser(HttpServletRequest httpRequest) throws BadRequestException {
        String authToken = httpRequest.getHeader("Authorization");
        if (authToken == null) {
            throw new BadRequestException("Authorization header is missing");
        }
        try {
            UUID userId = UUID.fromString(authToken);
            return User.builder().id(userId).build();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid token");
        }
    }

    private void handleException(HttpServletResponse response, Exception exception) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", exception.getMessage());
        ObjectMapper om = new ObjectMapper();
        response.setStatus(400);
        response.getWriter().write(om.writeValueAsString(errorResponse));
    }
}
