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
        // 1. Busca a Sala no banco pelo ID que veio no DTO
        ClassRoom sala = classRoomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Erro: Sala com ID " + dto.getClassroomId() + " não encontrada."));

        // 2. Transfere os dados do DTO para a Entidade
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setCpf(dto.getCpf());

        // 3. Criptografa a senha (O pulo do gato)
        String senhaCriptografada = passwordEncoder.encode(dto.getPassword());
        student.setPassword(senhaCriptografada);

        // 4. Faz o vínculo da Sala encontrada
        student.setClassRoom(sala);

        // 5. Define os valores iniciais do jogo
        student.setLevel(1);
        student.setXp(0);

        // 6. Salva no banco
        return studentRepository.save(student);
    }
}