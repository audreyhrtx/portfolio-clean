package com.porfolio.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                // Désactive CSRF pour API REST
                                .csrf(csrf -> csrf.disable())
                                // Configure CORS avec les valeurs par défaut (si configuré ailleurs)
                                .cors(Customizer.withDefaults())
                                // Désactive l'authentification par formulaire et de base, on utilisera les
                                // tokens
                                .formLogin(AbstractHttpConfigurer::disable)
                                .httpBasic(AbstractHttpConfigurer::disable)
                                .logout(AbstractHttpConfigurer::disable)

                                // Configuration des autorisations
                                .authorizeHttpRequests(authz -> authz
                                                // Permettre les requêtes preflight CORS (OPTIONS)
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // 🚨 AJOUTEZ VOTRE API D'AUTHENTIFICATION ICI (OBLIGATOIRE !)
                                                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                                                // ✅ Règle pour toutes les requêtes GET (Lecture)
                                                // C'est souvent la seule chose dont a besoin un portfolio public
                                                .requestMatchers(HttpMethod.GET, "/**").permitAll() // Autorise TOUTES
                                                                                                    // les lectures
                                                                                                    // (GET)
                                                
                                                .requestMatchers("/api/cart/**").permitAll()
                                                // ✅ Règle pour les ressources statiques (très important !)
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**")
                                                .permitAll()

                                                // ✅ Autres chemins publics (si vous en avez)
                                                .requestMatchers("/", "/home", "/galerie", "/paints/**").permitAll()

                                                // ✅ Accès administrateur uniquement
                                                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/paints/create", "/api/paints/update/**",
                                                                "/api/paints/delete/**")
                                                .hasRole("ADMIN")

                                                // ✅ Tout le reste nécessite une authentification (POST/PUT/DELETE non
                                                // inclus dans GET ci-dessus)
                                                .anyRequest().authenticated())

                                // S'assurer que les requêtes non autorisées renvoient un 401 propre (sans
                                // redirection)
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(
                                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

                return http.build();
        }
}
