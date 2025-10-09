package com.porfolio.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authz -> authz
                                                // ✅ Accès public
                                                .requestMatchers("/", "/home", "/galerie", "/paints/**").permitAll()
                                                .requestMatchers("/api/**").permitAll()
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**")
                                                .permitAll()
                                                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                                                // ✅ Accès administrateur uniquement
                                                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/paints/create", "/api/paints/update/**",
                                                                "/api/paints/delete/**")
                                                .hasRole("ADMIN")

                                                // ✅ Accès utilisateur connecté
                                                .requestMatchers("/profile/**", "/api/user/**")
                                                .hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/orders/**", "/api/orders/**")
                                                .hasAnyRole("USER", "ADMIN")

                                                // ✅ Tout le reste nécessite une authentification
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/")
                                                .permitAll())
                                .csrf(csrf -> csrf.disable()) // Pour les APIs REST
                                .cors(Customizer.withDefaults());

                // Éviter les redirections /login pour les appels API non autorisés
                http.exceptionHandling(ex -> ex
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

                return http.build();
        }
}
