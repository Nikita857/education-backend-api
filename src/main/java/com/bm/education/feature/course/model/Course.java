package com.bm.education.feature.course.model;

import com.bm.education.feature.documentation.model.Documentation;
import com.bm.education.feature.tag.model.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "courses",
    indexes = {
        @Index(name = "idx_course_slug", columnList = "slug"),
        @Index(name = "idx_course_category_id", columnList = "category_id"),
        @Index(name = "idx_course_status", columnList = "status"),
        @Index(name = "idx_course_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NaturalIdCache
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ← Long!

    @NaturalId
    @Column(unique = true, nullable = false, length = 100)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "...")
    private String slug;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 255)
    @Column(name = "image")
    private String image;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<Module> modules = new LinkedHashSet<>();

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Documentation documentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private CourseCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_tags", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    @Builder.Default
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<CourseReview> reviews = new LinkedHashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private CourseDifficultyLevel difficultyLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private CourseFormat format;

    @Column(name = "scorm_package_path")
    private String scormPackagePath;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (status == null)
            status = CourseStatus.INACTIVE;
        if (averageRating == null)
            averageRating = 0.0;
        if (totalReviews == null)
            totalReviews = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
