package com.bm.education.services;

import com.bm.education.dto.skill.SkillAssessmentRequest;
import com.bm.education.models.Skill;
import com.bm.education.models.UserSkill;
import com.bm.education.repositories.SkillRepository;
import com.bm.education.repositories.UserRepository;
import com.bm.education.repositories.UserSkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Transactional
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Transactional
    public Skill updateSkill(Long skillId, Skill skill) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("Skill not found: " + skillId);
        }
        skill.setId(skillId);
        return skillRepository.save(skill);
    }

    @Transactional(readOnly = true)
    public List<UserSkill> getUserSkills(String username) {
        com.bm.education.models.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        return userSkillRepository.findByUserId(user.getId());
    }

    @Transactional
    public UserSkill createUserSkillAssessment(SkillAssessmentRequest request, String username) {
        com.bm.education.models.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + request.getSkillId()));

        var existingAssessment = userSkillRepository.findByUserIdAndSkillId(user.getId(), skill.getId());
        if (existingAssessment.isPresent()) {
            throw new IllegalArgumentException("Skill assessment already exists for this skill");
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setProficiencyLevel(request.getProficiencyLevel());
        userSkill.setAssessmentMethod(com.bm.education.models.AssessmentMethod.SELF);
        userSkill.setLastAssessed(java.time.Instant.now());

        return userSkillRepository.save(userSkill);
    }

    @Transactional
    public UserSkill updateSkillAssessment(Long assessmentId, SkillAssessmentRequest request) {
        UserSkill userSkill = userSkillRepository.findById(assessmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assessment not found: " + assessmentId));

        userSkill.setProficiencyLevel(request.getProficiencyLevel());
        userSkill.setLastAssessed(java.time.Instant.now());

        return userSkillRepository.save(userSkill);
    }
}
