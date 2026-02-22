package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.LoginDTO;
import com.edu_verse.api_edu_verse.DTO.LoginResponseDTO;
import com.edu_verse.api_edu_verse.model.Teacher;
import com.edu_verse.api_edu_verse.repository.TeacherRepository;
import com.edu_verse.api_edu_verse.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dto) {
        // 1. O Spring Security autentica
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        // 2. Você pega o Professor validado
        Teacher teacher = (Teacher) auth.getPrincipal();

        // 3. Gera o Token
        String token = tokenService.generateToken(teacher);

        // 4. Devolve o pacote completo (O Crachá + A Chave)
        return ResponseEntity.ok(new LoginResponseDTO(teacher.getId(), teacher.getName(), teacher.getEmail(), token));
    }

    // Classe auxiliar interna apenas para devolver o JSON bonitinho { "token": "..." }
    // Pode criar um DTO separado se preferir
    private record TokenResponse(String token) {}
}