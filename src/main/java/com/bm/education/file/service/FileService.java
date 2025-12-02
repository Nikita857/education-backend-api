package com.bm.education.file.service;

import com.bm.education.dto.FileUploadResponse;
import com.bm.education.file.model.FileMetadata;
import com.bm.education.file.repository.FileMetadataRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMetadataRepository fileMetadataRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public FileUploadResponse storeFile(MultipartFile file, String username) {
        // Normalize file name
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            originalFileName = "unknown_file";
        }
        String fileExtension = "";
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID()+ fileExtension;

        try {
            // Check if the file's name contains invalid characters
            if (originalFileName != null && originalFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileMetadata fileMetadata = FileMetadata.builder()
                    .fileName(originalFileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(targetLocation.toString())
                    .uploadedBy(username)
                    .build();

            FileMetadata savedFile = fileMetadataRepository.save(fileMetadata);

            return FileUploadResponse.builder()
                    .fileId(savedFile.getId())
                    .fileName(savedFile.getFileName())
                    .fileSize(savedFile.getFileSize())
                    .mimeType(savedFile.getFileType())
                    .uploadStatus("success")
                    .fileUrl(targetLocation.toUri().toString())
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store generated file " + fileName, ex);
        }
    }

    @Transactional
    public FileUploadResponse storeGeneratedFile(byte[] content, String originalFileName, String contentType,
            String username) {
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.write(targetLocation, content);

            FileMetadata fileMetadata = FileMetadata.builder()
                    .fileName(originalFileName)
                    .fileType(contentType)
                    .fileSize((long) content.length)
                    .filePath(targetLocation.toString())
                    .uploadedBy(username)
                    .build();

            FileMetadata savedFile = fileMetadataRepository.save(fileMetadata);

            return FileUploadResponse.builder()
                    .fileId(savedFile.getId())
                    .fileName(savedFile.getFileName())
                    .fileSize(savedFile.getFileSize())
                    .mimeType(savedFile.getFileType())
                    .uploadStatus("success")
                    .fileUrl(targetLocation.toUri().toString())
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store generated file " + fileName, ex);
        }
    }

    public Resource loadFileAsResource(String fileId) {
        try {
            FileMetadata fileMetadata = fileMetadataRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));

            Path filePath = Paths.get(fileMetadata.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileId);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileId, ex);
        }
    }

    @Transactional
    public void deleteFile(String fileId) {
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));

        try {
            Path filePath = Paths.get(fileMetadata.getFilePath());
            Files.deleteIfExists(filePath);
            fileMetadataRepository.delete(fileMetadata);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileId, ex);
        }
    }

    public FileMetadata getFileMetadata(String fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }
}
