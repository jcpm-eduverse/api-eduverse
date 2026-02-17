package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    // Atenção: ClassRoom com R e Id com I
    // Isso busca a propriedade 'classRoom' dentro da entidade 'Tasks'
    List<Tasks> findByClassRoomId(Long id);
}