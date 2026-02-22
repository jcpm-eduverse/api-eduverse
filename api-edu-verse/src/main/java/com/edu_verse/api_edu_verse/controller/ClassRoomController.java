package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.ClassRoomCreateDTO;
import com.edu_verse.api_edu_verse.DTO.ClassRoomResponseDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.model.Teacher;
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

    @GetMapping("/teacher-classrooms")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')") // A porta blindada para os mestres
    public ResponseEntity<List<ClassRoomResponseDTO>> getTeacherClassRooms(
            @Parameter(hidden = true) Authentication auth) {

        // 1. O Cast correto e seguro: Quem entrou por essa porta TEM que ser Teacher
        Teacher connTeacher = (Teacher) auth.getPrincipal();

        // 2. A busca implacável: Traz apenas as turmas onde ele é o dono
        List<ClassRoom> classRooms = classRoomRepository.findByTeacherId(connTeacher.getId());

        // 3. A barreira de DTO: Converte as Entidades para não vazar a senha do professor no JSON
        List<ClassRoomResponseDTO> responseList = classRooms.stream()
                .map(ClassRoomResponseDTO::new)
                .toList();

        // 4. Retorna Status 200 com a lista (mesmo que seja vazia [])
        return ResponseEntity.ok(responseList);
    }

    }