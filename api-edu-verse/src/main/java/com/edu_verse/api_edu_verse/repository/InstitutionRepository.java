package com.edu_verse.api_edu_verse.repository;

import com.edu_verse.api_edu_verse.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}
