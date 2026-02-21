package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.JoinClassDTO;
import com.edu_verse.api_edu_verse.DTO.StudentCreateDTO;
import com.edu_verse.api_edu_verse.DTO.StudentResponseDTO;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import com.edu_verse.api_edu_verse.service.StudentService;
import com.edu_verse.api_edu_verse.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new-student")
    public ResponseEntity<StudentResponseDTO> newStudent(@RequestBody StudentCreateDTO dto) {
        Student newStudent = studentService.createStudent(dto);

        StudentResponseDTO safeResponse = new StudentResponseDTO(newStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(safeResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentCreateDTO dto) {
        Student student = studentRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), student.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }

        String token = tokenService.generateToken(student);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/join-class")
    public ResponseEntity<String> joinClass(@RequestBody JoinClassDTO dto) {
        // Pega quem é o aluno logado através do token
        Student alunoLogado = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        studentService.joinClassRoom(alunoLogado.getEmail(), dto.getCode());
        return ResponseEntity.ok("Matriculado com sucesso na turma!");
    }

    @GetMapping("/get-students")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
        List<Student> students = studentRepository.findAll();

        List<StudentResponseDTO> responseList = students.stream()
                .map(StudentResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/update-student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student updateStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateStudent);
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        if(!studentRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}