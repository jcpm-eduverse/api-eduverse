package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.TaskCreateDTO;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.model.Tasks;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import com.edu_verse.api_edu_verse.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    // --- LISTAR TUDO ---
    public List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    // --- CRIAR ---
    public Tasks createTask(TaskCreateDTO dto) {
        ClassRoom turma = classRoomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Erro: Turma com ID " + dto.getClassroomId() + " não encontrada."));

        Tasks task = new Tasks();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setXpReward(dto.getXpReward());
        task.setDeadline(dto.getDeadline());
        task.setClassRoom(turma);

        return taskRepository.save(task);
    }

    // --- DELETAR ---
    // Corrigido de 'DeleteTask' para 'deleteTask' (Padrão Java)
    public void deleteTask(Long id){
        if(!taskRepository.existsById(id)){
            throw new RuntimeException("Tarefa não encontrada id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // --- ATUALIZAR ---
    public Tasks updateTask(Long id, TaskCreateDTO dto){
        Tasks task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada id: " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setXpReward(dto.getXpReward());
        task.setDeadline(dto.getDeadline());

        if(dto.getClassroomId() != null){
            ClassRoom newClass = classRoomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Nova turma não encontrada id: " + dto.getClassroomId()));
            task.setClassRoom(newClass);
        }

        return taskRepository.save(task);
    }

    // --- LISTAR POR TURMA (Corrigido para 'Id' no final) ---
    public List<Tasks> getTasksByClassId(Long classId){
        if(!classRoomRepository.existsById(classId)){
            throw new RuntimeException("Turma não encontrada id: " + classId);
        }
        // ATENÇÃO: Se der erro aqui, verifique se no TaskRepository está escrito 'findByClassRoomId'
        return taskRepository.findByClassRoomId(classId);
    }

    // --- LISTAR POR ALUNO ---
    public List<Tasks> getTasksByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + studentId));

        if (student.getClassRoom() == null) {
            throw new RuntimeException("Este aluno não está matriculado em nenhuma turma.");
        }

        return getTasksByClassId(student.getClassRoom().getId());
    }
}