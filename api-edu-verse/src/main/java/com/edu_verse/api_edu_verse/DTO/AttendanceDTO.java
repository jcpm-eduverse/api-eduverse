package com.edu_verse.api_edu_verse.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceDTO {
    private Long studentId;
    private boolean present;
    private LocalDate date;
}