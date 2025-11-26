package com.bm.education.repositories;

import com.bm.education.models.AdaptationProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdaptationProgramRepository extends JpaRepository<AdaptationProgram, Long> {
}