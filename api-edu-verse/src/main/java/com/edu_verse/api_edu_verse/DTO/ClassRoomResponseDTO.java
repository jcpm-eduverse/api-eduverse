package com.edu_verse.api_edu_verse.DTO;

import com.edu_verse.api_edu_verse.model.ClassRoom;
import lombok.Data;

@Data
public class ClassRoomResponseDTO {
    private Long id;
    private String name;
    private String discipline;
    private String code;
    private String teacherName; // Achatamos o objeto Professor em uma simples String

    public ClassRoomResponseDTO(ClassRoom classRoom) {
        this.id = classRoom.getId();
        this.name = classRoom.getName();
        this.discipline = classRoom.getDiscipline();
        this.code = classRoom.getCode();

        if (classRoom.getTeacher() != null) {
            this.teacherName = classRoom.getTeacher().getName();
        } else {
            this.teacherName = "Professor não atribuído";
        }
    }
}