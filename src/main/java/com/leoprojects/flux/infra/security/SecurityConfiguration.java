package com.leoprojects.flux.infra.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Permitindo requisições dos domínios pré-definidos nas configurações.
                .csrf(AbstractHttpConfigurer::disable) // CSRF desabilitado porque a API é stateless e utiliza autenticação via JWT. Como não usamos sessão baseada em cookies, o risco de CSRF é reduzido.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Definindo que a aplicação é stateless (servidor não guarda informações entre sessões, quem faz isso é o JWT.)
                .authorizeHttpRequests( // Autorizando requisições.
                        authorize -> authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/transactions").permitAll()
                                .requestMatchers(HttpMethod.GET, "/summary/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/{id}").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Processa este filtro antes de qualquer requisição (autenticação do usuário)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // Permite requisições de um domínio para outro.
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));

        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
