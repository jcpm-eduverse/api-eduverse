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
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // --- CREATE ---
    @PostMapping("/new-task")
    public ResponseEntity<Tasks> newTask(@RequestBody TaskCreateDTO dto) {
        Tasks createdTask = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // --- READ (ALL) ---
    @GetMapping("/get-tasks")
    public List<Tasks> getAllTasks() {
        return taskService.getAllTasks();
    }

    // --- READ (BY CLASS) ---
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Tasks>> getTasksByClass(@PathVariable Long classId){
        // Correção: getTasksByClassId (com 'd' minúsculo)
        List<Tasks> tasks = taskService.getTasksByClassId(classId);
        return ResponseEntity.ok(tasks);
    }

    // --- READ (BY STUDENT) ---
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Tasks>> getTasksByStudent(@PathVariable Long studentId) {
        List<Tasks> tasks = taskService.getTasksByStudentId(studentId);
        return ResponseEntity.ok(tasks);
    }

    // --- DELETE ---
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        // Correção: deleteTask (com 'd' minúsculo)
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // --- UPDATE ---
    @PutMapping("/update-task/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable long id, @RequestBody TaskCreateDTO dto) {
        Tasks updatedTask = taskService.updateTask(id, dto);
        return ResponseEntity.ok(updatedTask);
    }
}