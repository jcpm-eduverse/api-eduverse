package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mudamos de 'task_name' para 'title' para bater com o DTO
    private String title;

    // Mudamos de 'task_context' para 'description'
    private String description;

    // Adicionamos a recompensa de XP
    private int xpReward;

    // Adicionamos o prazo
    private LocalDate deadline;

    // --- O VÍNCULO IMPORTANTE ---
    // Substitui aquela List<String> classes que não servia para nada relational
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    // Lista de alunos que completaram (pode manter, mas idealmente seria outra tabela)
    @ElementCollection
    private java.util.List<String> studentsCompleted;
}