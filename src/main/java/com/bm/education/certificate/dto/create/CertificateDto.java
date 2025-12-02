package com.bm.education.certificate.dto.create;

import lombok.Data;

import java.time.Instant;

@Data
public class CertificateDto {
    private Long id;
    private String title;
    private String description;
    private Integer userId;
    private Integer courseId;
    private String certificateNumber;
    private Instant issueDate;
    private Instant expiryDate;
    private String certificateFilePath;
    private String certificateUrl;
    private Instant createdAt;

    public CertificateDto(Long id, String title, String description, Integer userId, Integer courseId, String certificateNumber, Instant issueDate, Instant expiryDate, String certificateFilePath, String certificateUrl, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.courseId = courseId;
        this.certificateNumber = certificateNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.certificateFilePath = certificateFilePath;
        this.certificateUrl = certificateUrl;
        this.createdAt = createdAt;
    }
}