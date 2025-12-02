package com.bm.education.certificate.controller;

import com.bm.education.certificate.dto.create.CertificateDto;
import com.bm.education.api.type.template.ApiResponse;
import com.bm.education.certificate.mapper.CertificateMapper;
import com.bm.education.certificate.model.Certificate;
import com.bm.education.user.model.User;
import com.bm.education.certificate.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateMapper certificateMapper;

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<CertificateDto>>> getMyCertificates(
            @AuthenticationPrincipal @NotNull User user) {
        List<Certificate> certificates = certificateService.getUserCertificates(user.getUsername());
        List<CertificateDto> certificateDtos = certificates.stream()
                .map(certificateMapper::toDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(certificateDtos));
    }

    @GetMapping("/courses/{courseId}/certificate")
    public ResponseEntity<ApiResponse<CertificateDto>> getCourseCertificate(@AuthenticationPrincipal @NotNull User user,
            @PathVariable Integer courseId) {
        Certificate certificate = certificateService.getCertificateByUserIdAndCourseId(user.getUsername(), courseId);
        return ResponseEntity.ok(ApiResponse.success(certificateMapper.toDto(certificate)));
    }

    @PostMapping("/courses/{courseId}/generate-certificate")
    public ResponseEntity<ApiResponse<CertificateDto>> generateCertificate(@AuthenticationPrincipal User user,
            @PathVariable Integer courseId) {
        Certificate certificate = certificateService.generateCertificate(user.getUsername(), courseId);
        return ResponseEntity.ok(ApiResponse.success(certificateMapper.toDto(certificate)));
    }
}