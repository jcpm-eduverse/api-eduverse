package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.TaskCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Tasks;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.TaskRepository; // Atenção: Verifique se o nome é TaskRepository ou taskRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository; // Necessário para achar a turma

    public java.util.List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    public Tasks createTask(TaskCreateDTO dto) {
        // 1. Busca a Turma (Se não achar, erro)
        ClassRoom turma = classRoomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Erro: Turma com ID " + dto.getClassroomId() + " não encontrada."));

        // 2. Monta a Tarefa
        Tasks task = new Tasks();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setXpReward(dto.getXpReward());
        task.setDeadline(dto.getDeadline());

        // 3. Faz a associação
        task.setClassRoom(turma);

        return taskRepository.save(task);
    }

}