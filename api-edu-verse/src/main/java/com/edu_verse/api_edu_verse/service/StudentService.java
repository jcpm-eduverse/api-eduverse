package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.StudentCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository; // Agora existe!

    @Autowired
    private PasswordEncoder passwordEncoder; // Vem do SecurityConfig

    public Student createStudent(StudentCreateDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setCpf(dto.getCpf());

        // Criptografia ativada
        student.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Valores iniciais
        student.setLevel(1);
        student.setXp(0);

        return studentRepository.save(student);
    }

    public void joinClassRoom(String emailDoAlunoLogado, String codigoDaTurma) {
        Student student = studentRepository.findByEmail(emailDoAlunoLogado)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        ClassRoom classRoom = classRoomRepository.findByCode(codigoDaTurma)
                .orElseThrow(() -> new RuntimeException("Código de turma inválido."));

        student.setClassRoom(classRoom);
        studentRepository.save(student);
    }

    public Student updateStudent(Long id, StudentCreateDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("aluno não encontrado"));

        student.setName(dto.getName());

        if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return studentRepository.save(student);
    }
}