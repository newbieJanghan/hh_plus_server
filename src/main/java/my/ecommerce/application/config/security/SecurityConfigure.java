package my.ecommerce.application.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfigure {
    @Bean
    public SecurityFilterChain chainFilter(HttpSecurity http) throws Exception {
        return http
                .addFilter(new AuthenticationFilter())
                .sessionManagement(new SessionConfigureCustomizer())
                .build();
    }
}
