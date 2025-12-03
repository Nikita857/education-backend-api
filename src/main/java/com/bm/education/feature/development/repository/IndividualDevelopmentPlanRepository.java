package com.bm.education.feature.development.repository;

import com.bm.education.feature.development.model.IndividualDevelopmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualDevelopmentPlanRepository extends JpaRepository<IndividualDevelopmentPlan, Long> {
}