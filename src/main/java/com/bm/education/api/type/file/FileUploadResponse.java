package com.bm.education.dto.common;

import lombok.Data;

@Data
public class FileUploadResponse {
    private String fileId;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String mimeType;
    private String uploadStatus;
}