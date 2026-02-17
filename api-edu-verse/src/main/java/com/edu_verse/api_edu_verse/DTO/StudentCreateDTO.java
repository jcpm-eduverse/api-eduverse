package com.edu_verse.api_edu_verse.DTO;

import lombok.Data;

@Data
public class StudentCreateDTO {
    private String name;
    private String email;
    private String password;
    private String cpf;
    private Long classroomId;
}