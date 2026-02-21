package com.edu_verse.api_edu_verse.DTO;

import com.edu_verse.api_edu_verse.model.Student;
import lombok.Data;

@Data
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Integer level;
    private Integer xp;
    private String classroomName; // Apenas o nome, não o objeto ClassRoom inteiro!

    // Construtor que converte uma Entidade para DTO (O Mapeamento)
    public StudentResponseDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.level = student.getLevel();
        this.xp = student.getXp();

        // Evita NullPointerException se o aluno não tiver turma ainda
        if (student.getClassRoom() != null) {
            this.classroomName = student.getClassRoom().getName();
        } else {
            this.classroomName = "Sem turma";
        }
    }
}