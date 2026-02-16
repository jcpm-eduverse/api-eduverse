package com.edu_verse.api_edu_verse.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "attendance_records",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "student_id", "date" }) })
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean present; // true = Presente, false = Falta
}