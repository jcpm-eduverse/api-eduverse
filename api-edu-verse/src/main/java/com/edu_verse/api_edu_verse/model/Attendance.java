package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean present;
    private LocalDate date;

    // VÍNCULO 1: O Aluno
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // VÍNCULO 2: A Turma (Para garantir que a presença é naquela aula específica)
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;
}