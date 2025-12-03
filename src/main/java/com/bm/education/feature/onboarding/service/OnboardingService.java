package com.bm.education.feature.onboarding.service;

import com.bm.education.feature.adaptation.model.AdaptationProgram;
import com.bm.education.feature.adaptation.repository.AdaptationProgramRepository;
import com.bm.education.feature.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final AdaptationProgramRepository adaptationProgramRepository;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AdaptationProgram> getAllPrograms() {
        return adaptationProgramRepository.findAll();
    }

    @Transactional
    public AdaptationProgram createProgram(AdaptationProgram program) {
        return adaptationProgramRepository.save(program);
    }

    @Transactional
    public AdaptationProgram updateProgram(Long programId, AdaptationProgram programDetails) {
        AdaptationProgram program = adaptationProgramRepository.findById(programId).orElseThrow(() -> new EntityNotFoundException("Program not found: " + programId));

        program.setTitle(programDetails.getTitle());
        program.setDescription(programDetails.getDescription());
        program.setStartDate(programDetails.getStartDate());
        program.setEndDate(programDetails.getEndDate());
        program.setStatus(programDetails.getStatus());

        // Update relationships if needed (assignedTo, mentor)
        if (programDetails.getAssignedTo() != null) {
            program.setAssignedTo(programDetails.getAssignedTo());
        }
        if (programDetails.getMentor() != null) {
            program.setMentor(programDetails.getMentor());
        }

        return adaptationProgramRepository.save(program);
    }

    @Transactional
    public AdaptationProgram assignOnboardingProgram(AdaptationProgram program, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        program.setAssignedTo(user);
        return adaptationProgramRepository.save(program);
    }

    @Transactional(readOnly = true)
    public List<AdaptationProgram> getOnboardingAssignments() {
        return adaptationProgramRepository.findAll();
    }

    @Transactional(readOnly = true)
    public AdaptationProgram getOnboardingProgress(Long assignmentId) {
        return adaptationProgramRepository.findById(assignmentId).orElseThrow(() -> new EntityNotFoundException("Assignment not found: " + assignmentId));
    }
}
