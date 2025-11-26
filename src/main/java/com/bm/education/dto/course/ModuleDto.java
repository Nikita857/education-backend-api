package com.bm.education.dto.course;

import com.bm.education.models.ModuleStatus;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class ModuleDto {
    private Integer id;
    private String title;
    private String slug;
    private ModuleStatus status;
    private List<LessonDto> lessons;

    // Конструктор для JPA проекции (БЕЗ lessons)
    public ModuleDto(Integer id, String title, String slug, ModuleStatus status) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.status = status;
        this.lessons = new ArrayList<>();
    }

    // Полный конструктор (если нужен)
    public ModuleDto(Integer id, String title, String slug, ModuleStatus status, List<LessonDto> lessons) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.status = status;
        this.lessons = lessons != null ? lessons : new ArrayList<>();
    }

    // Конструктор по умолчанию (для Jackson)
    public ModuleDto() {
        this.lessons = new ArrayList<>();
    }
}