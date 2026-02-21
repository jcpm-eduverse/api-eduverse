package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.ClassRoom;
import com.edu_verse.api_edu_verse.model.Student;
import com.edu_verse.api_edu_verse.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> findByTeacher(Teacher teacher);
    Optional<ClassRoom> findByCode(String code);

    @Query("SELECT s.classRoom FROM Student s WHERE s.email = :email AND s.classRoom IS NOT NULL")
    List<ClassRoom> findByStudentEmail(@Param("email") String email);
}