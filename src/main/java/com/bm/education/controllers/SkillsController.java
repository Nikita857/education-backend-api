package com.bm.education.controllers;

import com.bm.education.dto.skill.SkillAssessmentRequest;
import com.bm.education.models.Skill;
import com.bm.education.models.UserSkill;
import com.bm.education.repositories.SkillRepository;
import com.bm.education.repositories.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;

    @GetMapping("/matrix")
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<Skill>> getSkillsMatrix() {
        List<Skill> skills = skillRepository.findAll();
        return ResponseEntity.ok(skills);
    }

    @PostMapping("/matrix")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill savedSkill = skillRepository.save(skill);
        return ResponseEntity.ok(savedSkill);
    }

    @PutMapping("/matrix/{skillId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long skillId, @RequestBody Skill skill) {
        if (!skillRepository.existsById(skillId)) {
            throw new RuntimeException("Skill not found: " + skillId);
        }
        skill.setId(skillId);
        Skill updatedSkill = skillRepository.save(skill);
        return ResponseEntity.ok(updatedSkill);
    }

    @GetMapping("/assessments")
    public ResponseEntity<List<UserSkill>> getUserSkills() {
        // Implementation to get user's skills assessments
        return ResponseEntity.ok(List.of()); // Placeholder
    }

    @PostMapping("/assessments")
    public ResponseEntity<UserSkill> createUserSkillAssessment(@RequestBody SkillAssessmentRequest request) {
        // Implementation to create skill assessment
        return ResponseEntity.ok(new UserSkill()); // Placeholder
    }

    @PutMapping("/assessments/{assessmentId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<UserSkill> updateSkillAssessment(
            @PathVariable Long assessmentId, 
            @RequestBody SkillAssessmentRequest request) {
        // Implementation to update skill assessment by manager/HR
        return ResponseEntity.ok(new UserSkill()); // Placeholder
    }
}