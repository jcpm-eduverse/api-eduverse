package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassRoomId(Long classRoomId);

    Optional<Student> findByEmail(String emal);
}
