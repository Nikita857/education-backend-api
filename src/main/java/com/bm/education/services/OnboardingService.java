package com.bm.education.services;

import com.bm.education.models.AdaptationProgram;
import com.bm.education.repositories.AdaptationProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final AdaptationProgramRepository adaptationProgramRepository;

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
        AdaptationProgram program = adaptationProgramRepository.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException("Program not found: " + programId));

        program.setTitle(programDetails.getTitle());
        program.setDescription(programDetails.getDescription());
        program.setStartDate(programDetails.getStartDate());
        program.setEndDate(programDetails.getEndDate());
        program.setStatus(programDetails.getStatus());

        // Update relationships if needed (assignedTo, mentor)

        return adaptationProgramRepository.save(program);
    }
}
