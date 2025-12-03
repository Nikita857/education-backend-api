package com.bm.education.feature.certificate.repository;

import com.bm.education.feature.certificate.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
        List<Certificate> findByUserId(Long userId);

        Optional<Certificate> findByUserIdAndCourseId(Long userId, Long courseId);

        long countByUserId(Long userId);
}