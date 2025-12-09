package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.studentRepository;
import com.edu_verse.api_edu_verse.service.cookieService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class studentController {

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private cookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        response.addCookie(cookieService.genCookie());
        return ResponseEntity.ok("Login de Estudante realizado com sucesso!");
    }

    @GetMapping("/get-students")
    public List<Student> GetAllStudents(){
        return studentRepository.findAll();
    }

    @PostMapping("/new-student")
    public ResponseEntity<Student> newStudent(@RequestBody Student student){
        Student saveStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveStudent);
    }

    @PostMapping("/update-student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student updateStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateStudent);
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id,
            @CookieValue(value = "auth_token", defaultValue = "") String token
    ){
        if (!cookieService.isCookieValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!studentRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}