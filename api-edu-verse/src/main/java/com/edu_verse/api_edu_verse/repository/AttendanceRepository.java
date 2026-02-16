package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Buscar chamada de um dia específico
    List<Attendance> findByDate(LocalDate date);

    // Buscar histórico de um aluno
    List<Attendance> findByStudentId(Long studentId);
}