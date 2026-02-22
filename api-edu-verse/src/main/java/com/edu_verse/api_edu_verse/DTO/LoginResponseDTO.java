package com.edu_verse.api_edu_verse.DTO;

public record LoginResponseDTO(
        Long id,
        String name,
        String email,
        String token
) {}