package com.edu_verse.api_edu_verse.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edu_verse.api_edu_verse.model.Teacher;
import com.edu_verse.api_edu_verse.repository.TeacherRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${ENV_COOKIE_KEY}")
    private String secret;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        // DEBUG 1: O token chegou?
        if (token != null) {
            System.out.println("DEBUG: Token recebido: " + token);
            try {
                var algorithm = Algorithm.HMAC256(secret);
                var email = JWT.require(algorithm)
                        .withIssuer("api-edu-verse")
                        .build()
                        .verify(token)
                        .getSubject();

                // DEBUG 2: O token é válido e leu o email?
                System.out.println("DEBUG: Email decifrado: " + email);

                Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

                // DEBUG 3: Achou o professor no banco?
                if (teacher != null) {
                    System.out.println("DEBUG: Professor encontrado: " + teacher.getName());
                    var authentication = new UsernamePasswordAuthenticationToken(teacher, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("DEBUG: Professor NÃO encontrado no banco para o email: " + email);
                }
            } catch (JWTVerificationException e) {
                // DEBUG 4: Deu ruim na validação
                System.out.println("DEBUG: Erro ao validar token: " + e.getMessage());
            }
        } else {
            System.out.println("DEBUG: Token veio NULO ou mal formatado.");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}