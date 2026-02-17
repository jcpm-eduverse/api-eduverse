package com.edu_verse.api_edu_verse.service;

import com.edu_verse.api_edu_verse.DTO.AttendanceCreateDTO;
import com.edu_verse.api_edu_verse.model.Attendance;
import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.repository.AttendanceRepository;
import com.edu_verse.api_edu_verse.repository.ClassRoomRepository;
import com.edu_verse.api_edu_verse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    public Attendance registerAttendance(AttendanceCreateDTO dto) {
        // 1. Busca o Aluno
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        // 2. Busca a Turma
        ClassRoom classRoom = classRoomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));

        // 3. Monta a Presença
        Attendance attendance = new Attendance();
        attendance.setPresent(dto.isPresent());
        attendance.setDate(dto.getDate());

        // 4. Faz a conexão dupla
        attendance.setStudent(student);
        attendance.setClassRoom(classRoom);

        return attendanceRepository.save(attendance);
    }
}