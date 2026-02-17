package com.edu_verse.api_edu_verse.controller;

import com.edu_verse.api_edu_verse.DTO.TaskCreateDTO;
import com.edu_verse.api_edu_verse.model.Tasks;
import com.edu_verse.api_edu_verse.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks") // Define a rota base como /tasks
public class TaskController {

    @Autowired
    private TaskService taskService;

    // --- CRIAÇÃO DE TAREFA (O Jeito Certo: Usando DTO e Service) ---
    @PostMapping("/new-task")
    public ResponseEntity<Tasks> newTask(@RequestBody TaskCreateDTO dto) {
        // O Controller não sabe montar tarefa. Ele pede pro Service.
        Tasks createdTask = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/get-tasks")
    public List<Tasks> getAllTasks() {
        return taskService.getAllTasks();
    }

}