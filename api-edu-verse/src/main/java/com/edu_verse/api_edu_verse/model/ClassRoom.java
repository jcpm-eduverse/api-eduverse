package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "classrooms")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String discipline;

    private String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false) // A turma TEM que ter um professor
    private Teacher teacher;
}