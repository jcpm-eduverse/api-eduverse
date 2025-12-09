package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instituition;

    private String task_name;

    private String task_context;

    private String teacher;

    @ElementCollection
    private List<String> classes;

    @ElementCollection
    private List<String> students_completed;
}
