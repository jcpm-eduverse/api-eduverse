package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "classrooms")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ex: "Turma 1 - Manhã"

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution; // A turma pertence a UMA instituição
}