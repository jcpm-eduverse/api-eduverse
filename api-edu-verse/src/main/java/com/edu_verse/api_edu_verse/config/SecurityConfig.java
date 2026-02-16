package com.edu_verse.api_edu_verse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer; // Importante
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar o dev
                .authorizeHttpRequests(auth -> auth
                        // 1. BLOQUEIA O SWAGGER (Exige autenticação)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").authenticated()

                        // 2. LIBERA O RESTO (Seus endpoints da API continuam públicos por enquanto)
                        .anyRequest().permitAll()
                )
                // 3. ATIVA O POPUP DE LOGIN (HTTP Basic)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Cria um usuário APENAS para o Swagger, sem tocar no Banco de Dados
        UserDetails docsUser = User.builder()
                .username("dev_front") // O usuário que seu dev vai usar
                .password(passwordEncoder().encode("front123")) // A senha (mude se quiser)
                .roles("DOCS")
                .build();

        return new InMemoryUserDetailsManager(docsUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}