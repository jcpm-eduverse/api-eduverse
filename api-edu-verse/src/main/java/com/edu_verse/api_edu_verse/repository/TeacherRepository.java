package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// ATENÇÃO: Tem que ser 'interface' e tem que ter 'extends JpaRepository<Teacher, Long>'
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Se não tiver essa linha, o Service não acha o método 'existsByEmail'
    boolean existsByEmail(String email);

    // Opcional, mas útil
    Optional<Teacher> findByEmail(String email);
}