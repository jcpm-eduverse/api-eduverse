package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.TeacherCreateDTO;
import com.edu_verse.api_edu_verse.model.Teacher;
import com.edu_verse.api_edu_verse.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository; // Se isso estiver vermelho, o pacote do Repo está errado.

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- CREATE ---
    public Teacher createTeacher(TeacherCreateDTO dto) {
        Teacher teacher = new Teacher();

        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setCpf(dto.getCpf());

        // CUIDADO: O nome aqui depende do seu Teacher.java
        // Se no Teacher.java for 'phoneNumber', use setPhoneNumber.
        teacher.setPhoneNumber(dto.getPhoneNumber());

        // Criptografia da Senha
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));

        return teacherRepository.save(teacher);
    }

    // --- READ ALL ---
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // --- READ BY ID ---
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + id));
    }

    // --- UPDATE ---
    public Teacher updateTeacher(Long id, TeacherCreateDTO dto) {
        Teacher teacher = getTeacherById(id); // Garante que existe

        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setCpf(dto.getCpf());
        teacher.setPhoneNumber(dto.getPhoneNumber());

        // Só troca a senha se vier algo no JSON
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            teacher.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return teacherRepository.save(teacher);
    }

    // --- DELETE ---
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Professor não encontrado com ID: " + id);
        }
        teacherRepository.deleteById(id);
    }
}