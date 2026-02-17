package com.edu_verse.api_edu_verse.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceCreateDTO {
    private Long studentId;   // Quem?
    private Long classroomId; // Onde?
    private boolean present;  // Veio?
    private LocalDate date;   // Quando?
}