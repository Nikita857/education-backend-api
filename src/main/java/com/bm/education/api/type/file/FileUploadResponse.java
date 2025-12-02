package com.bm.education.api.type.file;

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