package com.bm.education.services;

import com.bm.education.dto.skill.SkillAssessmentRequest;
import com.bm.education.models.AssessmentMethod;
import com.bm.education.models.Skill;
import com.bm.education.user.model.User;
import com.bm.education.models.UserSkill;
import com.bm.education.repositories.SkillRepository;
import com.bm.education.user.repository.UserRepository;
import com.bm.education.repositories.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillsServiceTest {

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillsService skillsService;

    private User user;
    private Skill skill;
    private UserSkill userSkill;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testuser");

        skill = new Skill();
        skill.setId(1L);
        skill.setName("Java");

        userSkill = new UserSkill();
        userSkill.setId(1L);
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setProficiencyLevel(3);
        userSkill.setAssessmentMethod(AssessmentMethod.SELF);
    }

    @Test
    void getAllSkills_ShouldReturnListOfSkills() {
        when(skillRepository.findAll()).thenReturn(List.of(skill));

        List<Skill> result = skillsService.getAllSkills();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void getUserSkills_ShouldReturnUserSkills() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userSkillRepository.findByUserId(1)).thenReturn(List.of(userSkill));

        List<UserSkill> result = skillsService.getUserSkills("testuser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userSkill, result.get(0));
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userSkillRepository, times(1)).findByUserId(1);
    }

    @Test
    void createUserSkillAssessment_ShouldCreateAssessment() {
        SkillAssessmentRequest request = new SkillAssessmentRequest();
        request.setSkillId(1L);
        request.setProficiencyLevel(4);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(userSkillRepository.findByUserIdAndSkillId(1, 1L)).thenReturn(Optional.empty());
        when(userSkillRepository.save(any(UserSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserSkill result = skillsService.createUserSkillAssessment(request, "testuser");

        assertNotNull(result);
        assertEquals(4, result.getProficiencyLevel());
        assertEquals(AssessmentMethod.SELF, result.getAssessmentMethod());
        verify(userSkillRepository, times(1)).save(any(UserSkill.class));
    }

    @Test
    void updateSkillAssessment_ShouldUpdateAssessment() {
        SkillAssessmentRequest request = new SkillAssessmentRequest();
        request.setProficiencyLevel(5);

        when(userSkillRepository.findById(1L)).thenReturn(Optional.of(userSkill));
        when(userSkillRepository.save(any(UserSkill.class))).thenReturn(userSkill);

        UserSkill result = skillsService.updateSkillAssessment(1L, request);

        assertNotNull(result);
        assertEquals(5, result.getProficiencyLevel());
        verify(userSkillRepository, times(1)).save(userSkill);
    }
}
