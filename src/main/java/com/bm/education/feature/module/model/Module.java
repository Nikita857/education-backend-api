package com.bm.education.feature.module.model;

import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.lesson.model.Lesson;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "modules",
    indexes = {
        @Index(name = "idx_module_course_id", columnList = "course_id"),
        @Index(name = "idx_module_slug", columnList = "slug"),
        @Index(name = "idx_module_status", columnList = "status")
    }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    private Course course;

    @Size(max = 50)
    @NotNull
    @Column(name = "slug", nullable = false, length = 50)
    private String slug;

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModuleStatus status;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "module")
    @Builder.Default
    private Set<Lesson> lessons = new LinkedHashSet<>();


    @PrePersist
    protected void onCreate() {
        if (this.status == null)
            this.status = ModuleStatus.INACTIVE;
    }

}