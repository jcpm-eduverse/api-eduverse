package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "instituition")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String email;

    private String password;

    private String phone_number;

    private String description;

    @ElementCollection
    private List<String> classes;

    @ElementCollection
    private List<String> teachers;

    @ElementCollection
    private List<String> students;

    private String salt;
}
