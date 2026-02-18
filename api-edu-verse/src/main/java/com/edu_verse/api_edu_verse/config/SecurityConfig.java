package com.edu_verse.api_edu_verse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <--- O IMPORT QUE FALTAVA (1)
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // <--- O IMPORT QUE FALTAVA (2)
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Agora o Java sabe o que é SessionCreationPolicy
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ADICIONE "/api-docs/**" AQUI NA LISTA:
                        .requestMatchers("/auth/login", "/v3/api-docs/**", "/swagger-ui/**", "/api-docs/**").permitAll()

                        // ... o resto continua igual ...
                        .requestMatchers(HttpMethod.POST, "/teachers").permitAll()
                        .anyRequest().authenticated()
                )
                // ADICIONA O FILTRO JWT
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Mantivemos esse usuário "dev_front" em memória, mas lembre-se:
    // Como tiramos o .httpBasic(), ele não vai pedir login no navegador.
    // Esse usuário só servirá se você reativar o httpBasic ou usar endpoints específicos.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails docsUser = User.builder()
                .username("dev_front")
                .password(passwordEncoder().encode("front123"))
                .roles("DOCS")
                .build();

        return new InMemoryUserDetailsManager(docsUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}