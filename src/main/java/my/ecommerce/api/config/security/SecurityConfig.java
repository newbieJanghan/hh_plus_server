package my.ecommerce.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain chainFilter(HttpSecurity http) throws Exception {
		return http
			.csrf(CsrfConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/error").permitAll()
				.anyRequest().authenticated())
			.addFilterBefore(new AuthenticationFilter(), BasicAuthenticationFilter.class)
			.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.build();
	}
}
