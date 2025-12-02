package com.bm.education.services;

import com.bm.education.models.AdaptationProgram;
import com.bm.education.user.model.User;
import com.bm.education.repositories.AdaptationProgramRepository;
import com.bm.education.user.repository.UserRepository;
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
class OnboardingServiceTest {

    @Mock
    private AdaptationProgramRepository adaptationProgramRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OnboardingService onboardingService;

    private AdaptationProgram program;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testuser");

        program = new AdaptationProgram();
        program.setId(1L);
        program.setTitle("Onboarding Program");
    }

    @Test
    void createProgram_ShouldSaveProgram() {
        when(adaptationProgramRepository.save(any(AdaptationProgram.class))).thenReturn(program);

        AdaptationProgram result = onboardingService.createProgram(program);

        assertNotNull(result);
        assertEquals("Onboarding Program", result.getTitle());
        verify(adaptationProgramRepository, times(1)).save(program);
    }

    @Test
    void updateProgram_ShouldUpdateProgram() {
        AdaptationProgram details = new AdaptationProgram();
        details.setTitle("Updated Program");

        when(adaptationProgramRepository.findById(1L)).thenReturn(Optional.of(program));
        when(adaptationProgramRepository.save(any(AdaptationProgram.class))).thenReturn(program);

        AdaptationProgram result = onboardingService.updateProgram(1L, details);

        assertNotNull(result);
        assertEquals("Updated Program", result.getTitle());
        verify(adaptationProgramRepository, times(1)).save(program);
    }

    @Test
    void assignOnboardingProgram_ShouldAssignUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(adaptationProgramRepository.save(any(AdaptationProgram.class))).thenReturn(program);

        AdaptationProgram result = onboardingService.assignOnboardingProgram(program, 1);

        assertNotNull(result);
        assertEquals(user, result.getAssignedTo());
        verify(userRepository, times(1)).findById(1);
        verify(adaptationProgramRepository, times(1)).save(program);
    }

    @Test
    void getOnboardingAssignments_ShouldReturnList() {
        when(adaptationProgramRepository.findAll()).thenReturn(List.of(program));

        List<AdaptationProgram> result = onboardingService.getOnboardingAssignments();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(adaptationProgramRepository, times(1)).findAll();
    }
}
