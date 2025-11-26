package com.bm.education.repositories;

import com.bm.education.models.IndividualDevelopmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualDevelopmentPlanRepository extends JpaRepository<IndividualDevelopmentPlan, Long> {
}