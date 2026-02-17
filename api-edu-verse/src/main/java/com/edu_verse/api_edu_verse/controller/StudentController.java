package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.StudentCreateDTO;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import com.edu_verse.api_edu_verse.service.StudentService; // Importe o Service!
import com.edu_verse.api_edu_verse.service.cookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private cookieService cookieService;

    // --- CADASTRO SEGURO (DTO + Criptografia) ---
    @PostMapping("/new-student")
    public ResponseEntity<Student> newStudent(@RequestBody StudentCreateDTO dto){
        Student newStudent = studentService.createStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @GetMapping("/get-students")
    public List<Student> GetAllStudents(){
        // CUIDADO: Se não usar @JsonIgnore na Entidade, isso aqui vai dar Loop Infinito
        return studentRepository.findAll();
    }

    // LOGIN FAKE (Aviso abaixo)
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        // Isso aqui é perigoso. Você não está checando senha nenhuma.
        // Futuramente, você terá que validar:
        // if (passwordEncoder.matches(senhaDigitada, aluno.getSenha())) ...
        response.addCookie(cookieService.genCookie());
        return ResponseEntity.ok("Login (sem validação) realizado com sucesso!");
    }

    @PostMapping("/update-student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        // ATENÇÃO: Se atualizar a senha por aqui, ela será salva "crua" (sem criptografia).
        // O ideal é ter um endpoint específico para mudar senha ou usar DTO aqui também.
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