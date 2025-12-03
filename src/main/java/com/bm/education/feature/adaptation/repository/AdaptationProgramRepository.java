package com.bm.education.feature.adaptation.repository;

import com.bm.education.feature.adaptation.model.AdaptationProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdaptationProgramRepository extends JpaRepository<AdaptationProgram, Long> {
}