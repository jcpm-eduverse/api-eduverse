package com.edu_verse.api_edu_verse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private int age;

    private String email;

    private String password;

    private String phone_number;

    private int level;

    private int xp;

    private List<String> classes;

    private String cpf;

    private String instituition;

    private String Salt;

    private List<String> tag;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom; // O aluno está em UMA turma
}
