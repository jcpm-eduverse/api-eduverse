package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.ClassRoomCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Teacher;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.TeacherRepository; // Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public ClassRoom createClassRoom(ClassRoomCreateDTO dto) {
        // 1. O ESPIÃO: Descobre quem está logado pelo Token JWT
        // No SecurityFilter, nós salvamos o objeto 'Teacher' como principal.
        // Aqui nós recuperamos ele.
        Teacher teacherLogado = (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // (Opcional: Se quiser garantir que o dado está fresco do banco, busque pelo ID do token)
        // Mas usar o cast direto do getPrincipal() é mais rápido e já deve funcionar com seu filtro atual.

        // 2. Monta a Turma
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(dto.getName());
        classRoom.setDiscipline(dto.getDiscipline());

        // 3. A VINCULAÇÃO SEGURA: O dono é OBRIGATORIAMENTE quem está logado
        classRoom.setTeacher(teacherLogado);

        // 4. Salva
        return classRoomRepository.save(classRoom);
    }

    public List<ClassRoom> getAllClassRooms() {
        return classRoomRepository.findAll();
    }

    // BÔNUS: Listar apenas as turmas do professor logado (Mais seguro que listar todas)
    public List<ClassRoom> getMyClassRooms() {
        Teacher teacherLogado = (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return classRoomRepository.findByTeacher(teacherLogado);
    }
}