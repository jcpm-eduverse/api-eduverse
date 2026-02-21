package com.edu_verse.api_edu_verse.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edu_verse.api_edu_verse.model.Institution;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.model.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${ENV_COOKIE_KEY}")
    private String secret;


    public String generateToken(Teacher teacher) {
        return buildToken(teacher.getEmail(), "ROLE_TEACHER");
    }

    public String generateToken(Student student) {
        return buildToken(student.getEmail(), "ROLE_STUDENT");
    }

    public String generateToken(Institution institution) {
        return buildToken(institution.getEmail(), "ROLE_INSTITUTION");
    }

    private String buildToken(String email, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-edu-verse")
                    .withSubject(email)
                    .withClaim("role", role) // <--- O CARIMBO DE AUTORIZAÇÃO
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-edu-verse")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public String getRole(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-edu-verse")
                    .build()
                    .verify(token)
                    .getClaim("role").asString();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}