package com.bm.education.certificate.repository;

import com.bm.education.certificate.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
        List<Certificate> findByUserId(Integer userId);

        Optional<Certificate> findByUserIdAndCourseId(Integer userId, Integer courseId);

        long countByUserId(Integer userId);
}