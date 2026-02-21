package com.edu_verse.api_edu_verse.config;

import com.edu_verse.api_edu_verse.repository.InstitutionRepository;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import com.edu_verse.api_edu_verse.repository.TeacherRepository;
import com.edu_verse.api_edu_verse.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // AQUI ESTÁ O SEGREDO QUE VOCÊ IGNOROU ANTES: O TRY-CATCH
        try {
            var email = tokenService.validateToken(token);
            var role = tokenService.getRole(token);

            if (email != null && !email.isEmpty() && role != null) {
                Object user = null;

                if ("ROLE_TEACHER".equals(role)) {
                    user = teacherRepository.findByEmail(email).orElse(null);
                } else if ("ROLE_STUDENT".equals(role)) {
                    user = studentRepository.findByEmail(email).orElse(null);
                } else if ("ROLE_INSTITUTION".equals(role)) {
                    user = institutionRepository.findByEmail(email).orElse(null);
                }

                if (user != null) {
                    var authorities = List.of(new SimpleGrantedAuthority(role));
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Se o token for inválido, o Swagger não toma 403, ele apenas é tratado como "Anônimo".
            System.out.println("Token ignorado: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}