package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
