package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface taskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByTaskId(Long id);
}
