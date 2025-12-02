package com.bm.education.controllers;

import com.bm.education.api.type.template.ApiResponse;
import com.bm.education.dto.skill.SkillDto;
import com.bm.education.dto.skill.SkillAssessmentRequest;
import com.bm.education.dto.skill.UserSkillDto;
import com.bm.education.mapper.SkillMapper;
import com.bm.education.models.Skill;
import com.bm.education.models.UserSkill;
import com.bm.education.services.SkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillsService skillsService;
    private final SkillMapper skillMapper;

    @GetMapping("/matrix")
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SkillDto>>> getSkillsMatrix() {
        List<Skill> skills = skillsService.getAllSkills();
        List<SkillDto> skillDtos = skills.stream()
                .map(skillMapper::toSkillDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(skillDtos));
    }

    @PostMapping("/matrix")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SkillDto>> createSkill(@RequestBody SkillDto skillDto) {
        Skill skill = skillMapper.toSkillEntity(skillDto);
        Skill savedSkill = skillsService.createSkill(skill);
        return ResponseEntity.ok(ApiResponse.success(skillMapper.toSkillDto(savedSkill)));
    }

    @PutMapping("/matrix/{skillId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SkillDto>> updateSkill(@PathVariable Long skillId,
            @RequestBody SkillDto skillDto) {
        Skill skill = skillMapper.toSkillEntity(skillDto);
        Skill updatedSkill = skillsService.updateSkill(skillId, skill);
        return ResponseEntity.ok(ApiResponse.success(skillMapper.toSkillDto(updatedSkill)));
    }

    @GetMapping("/assessments")
    public ResponseEntity<ApiResponse<List<UserSkillDto>>> getUserSkills() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        List<UserSkill> userSkills = skillsService.getUserSkills(username);
        List<UserSkillDto> userSkillDtos = userSkills.stream()
                .map(skillMapper::toUserSkillDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(userSkillDtos));
    }

    @PostMapping("/assessments")
    public ResponseEntity<ApiResponse<UserSkillDto>> createUserSkillAssessment(
            @RequestBody SkillAssessmentRequest request) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        UserSkill userSkill = skillsService.createUserSkillAssessment(request, username);
        return ResponseEntity.ok(ApiResponse.success(skillMapper.toUserSkillDto(userSkill)));
    }

    @PutMapping("/assessments/{assessmentId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserSkillDto>> updateSkillAssessment(
            @PathVariable Long assessmentId,
            @RequestBody SkillAssessmentRequest request) {
        UserSkill userSkill = skillsService.updateSkillAssessment(assessmentId, request);
        return ResponseEntity.ok(ApiResponse.success(skillMapper.toUserSkillDto(userSkill)));
    }
}