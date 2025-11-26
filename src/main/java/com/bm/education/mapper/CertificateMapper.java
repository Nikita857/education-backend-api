package com.bm.education.mapper;

import com.bm.education.dto.certificate.CertificateDto;
import com.bm.education.models.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "courseId", source = "course.id")
    CertificateDto toDto(Certificate certificate);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "course.id", source = "courseId")
    Certificate toEntity(CertificateDto dto);
}
