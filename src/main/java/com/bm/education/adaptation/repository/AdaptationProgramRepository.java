package com.bm.education.adaptation.repository;

import com.bm.education.adaptation.model.AdaptationProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdaptationProgramRepository extends JpaRepository<AdaptationProgram, Long> {
}