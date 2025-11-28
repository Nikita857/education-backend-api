package com.bm.education.repositories;

import com.bm.education.models.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserId(Integer userId);

    Optional<UserSkill> findByUserIdAndSkillId(Integer userId, Long skillId);
}