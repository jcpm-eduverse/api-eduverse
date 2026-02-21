package com.edu_verse.api_edu_verse.DTO;

import com.edu_verse.api_edu_verse.model.Teacher;
import lombok.Data;

@Data
public class TeacherResponseDTO {
    private Long id;
    private String name;
    private String email;
    // Sem CPF. Sem Password. Sem Phone Number.

    public TeacherResponseDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
    }
}