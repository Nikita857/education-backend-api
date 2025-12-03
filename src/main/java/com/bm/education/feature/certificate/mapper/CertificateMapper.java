package com.bm.education.feature.certificate.mapper;

import com.bm.education.feature.certificate.dto.create.CertificateDto;
import com.bm.education.feature.certificate.model.Certificate;
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
