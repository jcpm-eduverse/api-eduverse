package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

}