package com.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/forgot-password",
                                "/api/auth/verify-otp",
                                "/api/auth/reset-password",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/uploads/**"
                        ).permitAll()

                        .requestMatchers("/api/auth/me")
                        .authenticated()
                        // ======================
                        // Product APIs
                        // ======================

                        .requestMatchers(HttpMethod.GET,
                                "/api/products/**").permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/products/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/products/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/products/**").hasRole("ADMIN")

                        // ======================
                        // Category APIs
                        // ======================

                        .requestMatchers(HttpMethod.GET,
                                "/api/categories/**").permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**").hasRole("ADMIN")

                        // ======================
                        // Cart
                        // ======================

                        .requestMatchers("/api/cart/**")
                        .hasRole("USER")


                                // ======================
                                // Address APIs
                               // ======================

                                .requestMatchers("/api/address/**")
                                .hasRole("USER")

                        // ======================
                        // Orders
                        // ======================

                        .requestMatchers("/api/orders/**")
                        .hasAnyRole("USER", "ADMIN")


                        // ======================
                        // Wishlist
                        // ======================

                        .requestMatchers("/api/wishlist/**")
                        .hasRole("USER")

                        // ======================
                        // Payments
                        // ======================

                        .requestMatchers("/api/payments/**")
                        .hasRole("USER")

                        // ======================
                        // Reviews
                        // ======================

                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews/**").permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/reviews/**")
                        .hasAnyRole("USER", "ADMIN")

                        // ======================
                        // Users
                        // ======================

                        .requestMatchers(HttpMethod.GET,
                                "/api/users/profile")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/api/users/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/users/**")
                        .hasRole("ADMIN")
                        // ======================
                        // Coupon APIs
                        // ======================

                        .requestMatchers(HttpMethod.GET,
                                "/api/coupons/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/coupons/apply")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/api/invoice/**")
                        .hasAnyRole("USER", "ADMIN")

                        // ======================
                        // Dashboard
                        // ======================

                        .requestMatchers("/api/dashboard/**")
                        .hasRole("ADMIN")

                        // ======================
                        // Reports
                        // ======================

                        .requestMatchers("/api/reports/**")
                        .hasRole("ADMIN")

                        // ======================
                        // Inventory
                        // ======================

                        .requestMatchers("/api/inventory/**")
                        .hasRole("ADMIN")

                        // ======================
                        // Upload
                        // ======================

                        .requestMatchers("/api/upload/**")
                        .authenticated()



                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/upload/**")
                        .hasAnyRole("ADMIN","USER")



                        .anyRequest().authenticated()

                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}