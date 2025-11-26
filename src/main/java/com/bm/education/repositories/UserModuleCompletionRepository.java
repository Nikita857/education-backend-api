package com.bm.education.repositories;

import com.bm.education.models.UserModuleCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModuleCompletionRepository extends JpaRepository<UserModuleCompletion, Long> {
}