package com.bm.education.certificate.service;

import com.bm.education.dto.FileUploadResponse;
import com.bm.education.certificate.model.Certificate;
import com.bm.education.course.model.Course;
import com.bm.education.user.model.User;
import com.bm.education.certificate.repository.CertificateRepository;
import com.bm.education.course.repository.CourseRepository;
import com.bm.education.services.FileService;
import com.bm.education.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final UserService userService;
    private final CertificateRepository certificateRepository;
    private final CourseRepository courseRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<Certificate> getUserCertificates(String username) {
        List<Certificate> certificates = certificateRepository
                .findByUserId(userService.findByUsername(username).getId());
        return !certificates.isEmpty() ? certificates : Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public Certificate getCertificateByUserIdAndCourseId(String username, Integer courseId) {
        Integer userId = userService.findByUsername(username).getId();
        return certificateRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Certificate not found for user ID: " + userId + " and course ID: " + courseId));
    }

    @Transactional
    public Certificate generateCertificate(String username, Integer courseId) {
        User user = userService.findByUsername(username);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + courseId));

        // Check if certificate already exists
        if (certificateRepository.findByUserIdAndCourseId(user.getId(), courseId).isPresent()) {
            // Ideally return existing certificate, but for now let's throw or just return a
            // new one?
            // Let's assume re-generation is allowed or we just return the existing one if
            // we had the entity.
            // But the repo returns DTO. Let's find entity.
            // For simplicity, let's proceed to generate a new one or update.
        }

        try {
            byte[] certificateImage = createCertificateImage(user, course);
            String fileName = "certificate_" + user.getId() + "_" + course.getId() + ".png";
            FileUploadResponse fileResponse = fileService.storeGeneratedFile(certificateImage, fileName, "image/png",
                    username);

            Certificate certificate = new Certificate();
            certificate.setUser(user);
            certificate.setCourse(course);
            certificate.setTitle("Certificate of Completion: " + course.getTitle());
            certificate.setDescription("Awarded to " + user.getFirstName() + " " + user.getLastName()
                    + " for completing " + course.getTitle());
            certificate.setCertificateNumber(UUID.randomUUID().toString());
            certificate.setCertificateFilePath(fileResponse.getFileId()); // Storing File ID as path for retrieval
            certificate.setCertificateUrl("/api/v1/files/" + fileResponse.getFileId());

            return certificateRepository.save(certificate);

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate certificate", e);
        }
    }

    private byte[] createCertificateImage(User user, Course course) throws IOException {
        int width = 800;
        int height = 600;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Border
        g2d.setColor(new Color(25, 25, 112)); // Midnight Blue
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(20, 20, width - 40, height - 40);

        // Title
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 48));
        String title = "Certificate of Completion";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, x, 100);

        // Subtitle
        g2d.setFont(new Font("Serif", Font.PLAIN, 24));
        String subtitle = "This is to certify that";
        x = (width - fm.stringWidth(subtitle)) / 2;
        g2d.drawString(subtitle, x, 180);

        // User Name
        g2d.setFont(new Font("Serif", Font.BOLD, 36));
        String userName = user.getFirstName() + " " + user.getLastName();
        x = (width - fm.stringWidth(userName)) / 2;
        g2d.drawString(userName, x, 240);

        // Text
        g2d.setFont(new Font("Serif", Font.PLAIN, 24));
        String text = "has successfully completed the course";
        x = (width - fm.stringWidth(text)) / 2;
        g2d.drawString(text, x, 300);

        // Course Name
        g2d.setFont(new Font("Serif", Font.BOLD, 32));
        String courseName = course.getTitle();
        x = (width - fm.stringWidth(courseName)) / 2;
        g2d.drawString(courseName, x, 360);

        // Date
        g2d.setFont(new Font("Serif", Font.PLAIN, 18));
        String date = "Date: " + java.time.LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        g2d.drawString(date, 100, 500);

        // Signature (Placeholder)
        g2d.drawString("Signature: ________________", 500, 500);

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }
}
