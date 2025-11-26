package com.bm.education.repositories;

import com.bm.education.dto.certificate.CertificateDto;
import com.bm.education.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query("SELECT new com.bm.education.dto.certificate.CertificateDto(" +
            "cert.id," +
            " cert.title," +
            " cert.description," +
            " cert.user.id," +
            " cert.course.id," +
            " cert.certificateNumber," +
            " cert.issueDate," +
            " cert.expiryDate," +
            " cert.certificateFilePath" +
            ", cert.certificateUrl, " +
            "cert.createdAt" +
            ") " +
            "FROM Certificate cert WHERE cert.user.id = :userId ORDER BY cert.id")
    List<CertificateDto> findCertificatesByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.bm.education.dto.certificate.CertificateDto(" +
            "c.id," +
            " c.title," +
            " c.description," +
            " c.user.id," +
            " c.course.id," +
            " c.certificateNumber," +
            " c.issueDate," +
            " c.expiryDate," +
            " c.certificateFilePath," +
            " c.certificateUrl," +
            " c.createdAt" +
            ") FROM Certificate c WHERE c.user.id = :userId AND c.course.id = :courseId")
    Optional<CertificateDto> findCertificateByUserIdAndCourseId(@Param("userId") Integer userId,
                                                               @Param("courseId") Integer courseId);

}