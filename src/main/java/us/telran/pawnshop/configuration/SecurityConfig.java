package us.telran.pawnshop.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/pawn-shop/**").hasRole("DIRECTOR")
                        .requestMatchers("/pawn-shop/clients/new",
                                         "/pawn-shop/clients/show",
                                         "/pawn-shop/clients/update/",
                                         "/pawn-shop/pledge-categories/show",
                                         "/pawn-shop/pledges/new",
                                         "/pawn-shop/pledges/all",
                                         "/pawn-shop/interest-grid/show",
                                         "/pawn-shop/interest-grid/update/",
                                         "pawn-shop/loans/new",
                                         "/pawn-shop/loans/show",
                                         "/pawn-shop/loan-orders/income/*",
                                         "/pawn-shop/loan-orders/all",
                                         "pawn-shop/price/precious-metal/show",
                                         "/pawn-shop/price/precious-metal/change/",
                                         "/pawn-shop/cash-operations/*").hasAnyRole("MANAGER", "DIRECTOR")
                        .anyRequest().authenticated()
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
