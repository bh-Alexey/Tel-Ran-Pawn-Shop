package us.telran.pawnshop.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-resources/**",
                                     "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .requestMatchers(HttpMethod.GET, "/pawn-shop/**").hasAnyRole("MANAGER", "DIRECTOR")
                    .requestMatchers(HttpMethod.PUT, "/pawn-shop/clients/update/*").hasAnyRole("MANAGER", "DIRECTOR")
                    .requestMatchers(HttpMethod.POST,"/pawn-shop/pledges/*",
                                                     "/pawn-shop/clients/*",
                                                     "/pawn-shop/loans/*",
                                                     "/pawn-shop/cash-operations/*",
                                                     "/pawn-shop/loan-orders/*").hasAnyRole("MANAGER", "DIRECTOR")
                    .requestMatchers("/pawn-shop/**").hasRole("DIRECTOR")

                    .anyRequest().authenticated()
            )
            .headers(headers -> headers
                    .permissionsPolicy(policy -> policy.policy("frame-ancestors 'self'"))
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .disable()
            )
            .logout(LogoutConfigurer::disable)
            .httpBasic(httpBasis -> httpBasis
                    .init(http));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
