package com.bm.education.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "module_id", nullable = false)
    @ToString.Exclude
    private Module module;

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Lob
    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private LessonContentType contentType;

    @Size(max = 255)
    @Column(name = "video")
    private String video;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "scorm_package_path")
    private String scormPackagePath;

    @Column(name = "webinar_url")
    private String webinarUrl;

    @Column(name = "webinar_start_time")
    private java.time.Instant webinarStartTime;

    @Column(name = "webinar_duration_minutes")
    private Integer webinarDurationMinutes;

    @Column(name = "file_path")
    private String filePath; // For PDF, PPT, Word documents

    @Column(name = "content_url")
    private String contentUrl; // For external content links

    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes; // Estimated time to complete the lesson

    @Column(name = "viewing_progress_required")
    @Builder.Default
    private Boolean viewingProgressRequired = false; // Whether viewing progress is tracked

    @Column(name = "viewing_percentage_threshold")
    @Builder.Default
    private Integer viewingPercentageThreshold = 100; // Percentage required to mark as completed

    @Size(max = 50)
    @NotNull
    @Column(name = "short_description", nullable = false, length = 50)
    private String shortDescription;

    @Size(max = 5000)
    @Column(name = "test_code", length = 5000)
    private String testCode;

    @Column(name = "content_length")
    private Long contentLength;

    @OneToMany(mappedBy = "lesson")
    @ToString.Exclude
    @Builder.Default
    private Set<UserProgress> userProgresses = new LinkedHashSet<>();
}