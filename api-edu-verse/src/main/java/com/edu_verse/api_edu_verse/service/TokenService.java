package com.edu_verse.api_edu_verse.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.edu_verse.api_edu_verse.model.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Pega a chave secreta do application.properties (ENV_COOKIE_KEY)
    @Value("${ENV_COOKIE_KEY}")
    private String secret;

    public String generateToken(Teacher teacher) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-edu-verse") // Quem gerou o token
                    .withSubject(teacher.getEmail()) // Quem é o dono do token (Email é melhor que ID aqui)
                    .withExpiresAt(genExpirationDate()) // Quando expira
                    .sign(algorithm); // Assina
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Define expiração para 2 horas (ajuste conforme necessário)
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}