package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.LoginDTO;
import com.edu_verse.api_edu_verse.model.Teacher;
import com.edu_verse.api_edu_verse.repository.TeacherRepository;
import com.edu_verse.api_edu_verse.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO body) {
        // 1. Busca o usuário pelo email
        Teacher teacher = teacherRepository.findByEmail(body.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Verifica se a senha bate (Texto puro vs Hash no banco)
        if (passwordEncoder.matches(body.getPassword(), teacher.getPassword())) {

            // 3. Se bater, gera o token
            String token = tokenService.generateToken(teacher);

            // Retorna o token num JSON simples
            return ResponseEntity.ok().body(new TokenResponse(token));
        }

        // 4. Se não bater, erro
        return ResponseEntity.badRequest().build();
    }

    // Classe auxiliar interna apenas para devolver o JSON bonitinho { "token": "..." }
    // Pode criar um DTO separado se preferir
    private record TokenResponse(String token) {}
}