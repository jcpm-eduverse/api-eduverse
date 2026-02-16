package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.model.Attendance;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.AttendanceRepository;
import com.edu_verse.api_edu_verse.repository.StudentRepository; // Assumindo que você renomeou o repository corretamente!
import com.edu_verse.api_edu_verse.DTO.AttendanceDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository; // Você precisa disso para achar o aluno

    @PostMapping
    public ResponseEntity<String> registerAttendance(@RequestBody List<AttendanceDTO> dtos) {
        List<Attendance> recordsToSave = new ArrayList<>();

        for (AttendanceDTO dto : dtos) {
            // 1. Achar o aluno pelo ID
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + dto.getStudentId()));

            // 2. Criar o objeto de chamada
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(dto.getDate());
            attendance.setPresent(dto.isPresent());

            recordsToSave.add(attendance);
        }

        // 3. Salvar tudo de uma vez
        attendanceRepository.saveAll(recordsToSave);

        return ResponseEntity.ok("Chamada realizada com sucesso para " + recordsToSave.size() + " alunos.");
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Attendance>> getByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(attendanceRepository.findByDate(date));
    }


    @GetMapping("/sheet/{classRoomId}")
    public ResponseEntity<List<Student>> getAttendanceSheet(@PathVariable Long classRoomId) {
        return ResponseEntity.ok(studentRepository.findByClassRoomId(classRoomId));
    }
}