package com.edu_verse.api_edu_verse.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskCreateDTO {

    // --- O CAMPO QUE FALTA ---
    private String title;

    private String description;
    private int xpReward;
    private LocalDate deadline;
    private Long classroomId;
}