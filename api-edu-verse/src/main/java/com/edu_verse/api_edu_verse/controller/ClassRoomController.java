package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.ClassRoomCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms") // Padrão de URL no plural
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    // --- CRIAÇÃO DE TURMA (Usa o DTO que pede o ID da Instituição) ---
    @PostMapping("/new-classroom")
    public ResponseEntity<ClassRoom> newClassRoom(@RequestBody ClassRoomCreateDTO dto) {
        ClassRoom newClassRoom = classRoomService.createClassRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClassRoom);
    }


    @GetMapping("/get-classrooms")
    public List<ClassRoom> getAllClassRooms() {
        // Certo: Chama a instância do serviço (letra minúscula)
        return classRoomService.getAllClassRooms();
    }
}