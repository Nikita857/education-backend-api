package com.bm.education.seeders;

import com.bm.education.models.*;
import com.bm.education.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExtendedDatabaseSeeder implements CommandLineRunner {

    private final CourseCategoryRepository courseCategoryRepository;
    private final SkillRepository skillRepository;
    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {
        // Заполняем базу данных только если она пуста
        try {
            if (courseCategoryRepository.count() == 0) {
                createCourseCategories();
            }

            if (skillRepository.count() == 0) {
                createSkills();
            }

            if (tagRepository.count() == 0) {
                createTags();
            }
        } catch (Exception e) {
            // Если таблицы еще не созданы, откладываем заполнение данных
            System.out.println("Не удалось заполнить расширенную базу данных: " + e.getMessage());
        }
    }

    private void createCourseCategories() {
        // Создаем категории курсов
        CourseCategory category1 = new CourseCategory();
        category1.setName("Программирование");
        category1.setDescription("Курсы по программированию");
        category1.setSlug("programmirovanie");
        courseCategoryRepository.save(category1);

        CourseCategory category2 = new CourseCategory();
        category2.setName("Безопасность");
        category2.setDescription("Курсы по информационной безопасности");
        category2.setSlug("bezopasnost");
        courseCategoryRepository.save(category2);

        CourseCategory category3 = new CourseCategory();
        category3.setName("Лидерство");
        category3.setDescription("Курсы по лидерству и управлению");
        category3.setSlug("liderstvo");
        courseCategoryRepository.save(category3);

        CourseCategory category4 = new CourseCategory();
        category4.setName("Коммуникации");
        category4.setDescription("Курсы по коммуникациям и презентациям");
        category4.setSlug("kommunikacii");
        courseCategoryRepository.save(category4);
    }

    private void createSkills() {
        // Создаем навыки для оценки
        Skill skill1 = new Skill();
        skill1.setName("Java Programming");
        skill1.setDescription("Навыки программирования на Java");
        skill1.setCategory("Programming");
        skillRepository.save(skill1);

        Skill skill2 = new Skill();
        skill2.setName("Leadership");
        skill2.setDescription("Навыки лидерства и управления командой");
        skill2.setCategory("Management");
        skillRepository.save(skill2);

        Skill skill3 = new Skill();
        skill3.setName("Communication");
        skill3.setDescription("Навыки эффективной коммуникации");
        skill3.setCategory("Soft Skills");
        skillRepository.save(skill3);

        Skill skill4 = new Skill();
        skill4.setName("Cybersecurity");
        skill4.setDescription("Навыки в области кибербезопасности");
        skill4.setCategory("Security");
        skillRepository.save(skill4);

        Skill skill5 = new Skill();
        skill5.setName("Project Management");
        skill5.setDescription("Навыки управления проектами");
        skill5.setCategory("Management");
        skillRepository.save(skill5);
    }

    private void createTags() {
        // Создаем теги для курсов
        Tag tag1 = new Tag("Программирование");
        tagRepository.save(tag1);

        Tag tag2 = new Tag("Безопасность");
        tagRepository.save(tag2);

        Tag tag3 = new Tag("Управление");
        tagRepository.save(tag3);

        Tag tag4 = new Tag("Коммуникации");
        tagRepository.save(tag4);

        Tag tag5 = new Tag("Новичок");
        tagRepository.save(tag5);

        Tag tag6 = new Tag("Продвинутый");
        tagRepository.save(tag6);
    }
}