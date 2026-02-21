package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.ClassRoomCreateDTO;
import com.edu_verse.api_edu_verse.DTO.ClassRoomResponseDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.service.ClassRoomService;
import com.edu_verse.api_edu_verse.service.TokenService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/classrooms") // Padrão de URL no plural
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/new-classroom")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')") // Agora sim, só professores entram
    public ResponseEntity<ClassRoomResponseDTO> newClassRoom(@RequestBody ClassRoomCreateDTO dto) {

        ClassRoom newClassRoom = classRoomService.createClassRoom(dto);

        // Empacota a entidade no DTO seguro, igual fizemos com o Student
        ClassRoomResponseDTO safeResponse = new ClassRoomResponseDTO(newClassRoom);

        return ResponseEntity.status(HttpStatus.CREATED).body(safeResponse);
    }


    @GetMapping("/get-classrooms")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<List<ClassRoomResponseDTO>> getAllClassRooms(
            @Parameter(hidden = true) Authentication auth) {

        Student connStudent = (Student) auth.getPrincipal();
        String studentEmail = connStudent.getEmail();

        List<ClassRoom> classRooms = classRoomRepository.findByStudentEmail(studentEmail);

        List<ClassRoomResponseDTO> responseList = classRooms.stream()
                .map(ClassRoomResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    }