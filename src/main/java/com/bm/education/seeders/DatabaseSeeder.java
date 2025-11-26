package com.bm.education.seeders;

import com.bm.education.models.*;
import com.bm.education.models.Module;
import com.bm.education.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final PasswordEncoder passwordEncoder;

    public void seedDatabase() {
        if (userRepository.count() == 0) {
            createDefaultUsers();
        }
        if (courseRepository.count() == 0) {
            createDefaultCourses();
        }
    }

    private void createDefaultUsers() {
        // Создаем администратора
        User admin = new User();
        admin.setFirstName("Админ");
        admin.setLastName("Системы");
        admin.setDepartment("IT");
        admin.setJobTitle("Администратор");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_INSTRUCTOR));
        userRepository.save(admin);

        // Создаем менеджера
        User manager = new User();
        manager.setFirstName("Иван");
        manager.setLastName("Менеджеров");
        manager.setDepartment("Управление");
        manager.setJobTitle("Менеджер");
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setRoles(Set.of(Role.ROLE_MANAGER));
        userRepository.save(manager);

        // Создаем инструктора
        User instructor = new User();
        instructor.setFirstName("Петр");
        instructor.setLastName("Преподаватель");
        instructor.setDepartment("Обучение");
        instructor.setJobTitle("Инструктор");
        instructor.setUsername("instructor");
        instructor.setPassword(passwordEncoder.encode("instructor123"));
        instructor.setRoles(Set.of(Role.ROLE_INSTRUCTOR));
        userRepository.save(instructor);

        // Создаем HR
        User hr = new User();
        hr.setFirstName("Анна");
        hr.setLastName("HR");
        hr.setDepartment("HR");
        hr.setJobTitle("HR-менеджер");
        hr.setUsername("hr-manager");
        hr.setPassword(passwordEncoder.encode("hr123"));
        hr.setRoles(Set.of(Role.ROLE_HR));
        userRepository.save(hr);

        // Создаем обычных пользователей
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setFirstName("Сотрудник" + i);
            user.setLastName("Фамилия" + i);
            user.setDepartment("Отдел" + (i % 4 + 1));
            user.setJobTitle("Работник");
            user.setUsername("user_" + i);
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(user);
        }
    }

    private void createDefaultCourses() {
        // Курс 1: Основы программирования
        Course course1 = createCourse(
                "Основы программирования",
                "Полный курс для начинающих программистов. Изучите основные концепции программирования, работу с переменными, циклами и функциями.",
                "osnovy-programmirovaniya",
                40,
                CourseStatus.ACTIVE,
                CourseDifficultyLevel.BEGINNER
        );

        Module module1_1 = createModule(course1, "Введение в программирование", "vvedenie-v-programmirovanie");
        createLesson(module1_1, "Что такое программирование", "Введение в понятие программирования и его основные концепции.", "Введение", LessonContentType.VIDEO, "Это урок в формате видео.", 15);
        createLesson(module1_1, "Переменные и типы данных", "Основы работы с переменными и различными типами данных.", "Переменные", LessonContentType.TEXT, "Это текстовый урок о переменных.", 20);
        Lesson lesson1_1_3 = createLesson(module1_1, "Операторы и выражения", "Изучение различных операторов и создания выражений.", "Операторы", LessonContentType.VIDEO, "Видео-урок об операторах.", 25);
        createTest(lesson1_1_3, "Тест по основам программирования", "Проверьте свои знания по основам программирования", 70, 30, 3, true, true, "программирование");

        Module module1_2 = createModule(course1, "Управляющие структуры", "upravlyayushchie-struktury");
        createLesson(module1_2, "Условные операторы", "Изучение if-else конструкций и switch-case.", "Условия", LessonContentType.VIDEO, "Видео об if-else.", 20);
        createLesson(module1_2, "Циклы", "Работа с циклами for, while, do-while.", "Циклы", LessonContentType.TEXT, "Текстовый урок о циклах.", 30);
        Lesson lesson1_2_3 = createLesson(module1_2, "Вложенные циклы", "Использование циклов внутри циклов.", "Вложенные циклы", LessonContentType.VIDEO, "Видео о вложенных циклах.", 25);
        createTest(lesson1_2_3, "Тест по управляющим структурам", "Проверка знаний по условиям и циклам", 75, 25, 3, true, true, "программирование");

        Module module1_3 = createModule(course1, "Функции и методы", "funktsii-i-metody");
        createLesson(module1_3, "Создание функций", "Изучение создания и использования функций.", "Функции", LessonContentType.VIDEO, "Видео о функциях.", 30);
        Lesson lesson1_3_2 = createLesson(module1_3, "Параметры и возврат значений", "Работа с параметрами функций и возвратом значений.", "Параметры", LessonContentType.TEXT, "Текст о параметрах.", 25);
        createTest(lesson1_3_2, "Итоговый тест по функциям", "Финальная проверка знаний по функциям", 80, 30, 2, false, true, "программирование");


        // Курс 2: Информационная безопасность
        Course course2 = createCourse(
                "Информационная безопасность",
                "Курс по основам информационной безопасности. Изучите принципы защиты информации, криптографию и методы защиты от кибератак.",
                "info-bezopasnost",
                30,
                CourseStatus.ACTIVE,
                CourseDifficultyLevel.INTERMEDIATE
        );

        Module module2_1 = createModule(course2, "Основы информационной безопасности", "osnovy-info-bezopasnosti");
        createLesson(module2_1, "Введение в информационную безопасность", "Основные понятия и принципы.", "Основы", LessonContentType.TEXT, "Текст об основах.", 20);
        createLesson(module2_1, "Угрозы информационной безопасности", "Основные типы угроз и методы их классификации.", "Угрозы", LessonContentType.VIDEO, "Видео об угрозах.", 25);
        Lesson lesson2_1_3 = createLesson(module2_1, "Модели безопасности", "Изучение различных моделей обеспечения безопасности.", "Модели", LessonContentType.TEXT, "Текст о моделях.", 30);
        createTest(lesson2_1_3, "Тест по основам ИБ", "Проверка базовых знаний", 70, 30, 3, true, false, "безопасность");

        Module module2_2 = createModule(course2, "Криптография", "kriptografiya");
        createLesson(module2_2, "Симметричное шифрование", "Принципы симметричного шифрования.", "Симметричное", LessonContentType.TEXT, "Текст о симметричном шифровании.", 25);
        createLesson(module2_2, "Асимметричное шифрование", "Основы асимметричной криптографии.", "Асимметричное", LessonContentType.VIDEO, "Видео об ассиметричном шифровании.", 30);
        Lesson lesson2_2_3 = createLesson(module2_2, "Хеширование и цифровые подписи", "Использование хеш-функций и электронных подписей.", "Хеширование", LessonContentType.TEXT, "Текст о хешировании.", 20);
        createTest(lesson2_2_3, "Тест по криптографии", "Проверка знаний по криптографическим методам", 80, 35, 3, true, true, "криптография");

    }

    private Course createCourse(String title, String description, String slug, Integer durationHours,
                                CourseStatus status, CourseDifficultyLevel difficultyLevel) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setSlug(slug);
        course.setDurationHours(durationHours);
        course.setStatus(status);
        course.setDifficultyLevel(difficultyLevel);
        return courseRepository.save(course);
    }

    private Module createModule(Course course, String title, String slug) {
        Module module = new Module();
        module.setTitle(title);
        module.setSlug(slug);
        module.setStatus(ModuleStatus.ACTIVE);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    private Lesson createLesson(Module module, String title, String description, String shortDescription,
                                LessonContentType contentType, String textContent, Integer estimatedMinutes) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setShortDescription(shortDescription);
        lesson.setModule(module);
        lesson.setContentType(contentType);
        lesson.setTextContent(textContent);
        lesson.setEstimatedDurationMinutes(estimatedMinutes);
        return lessonRepository.save(lesson);
    }

    private Test createTest(Lesson lesson, String title, String description, Integer passingScore,
                            Integer timeLimitMinutes, Integer maxAttempts, Boolean randomizeQuestions,
                            Boolean showCorrectAnswers, String questionTopic) {
        Test test = new Test();
        test.setTitle(title);
        test.setDescription(description);
        test.setPassingScore(passingScore);
        test.setTimeLimitMinutes(timeLimitMinutes);
        test.setMaxAttempts(maxAttempts);
        test.setRandomizeQuestions(randomizeQuestions);
        test.setShowCorrectAnswers(showCorrectAnswers);
        test.setLesson(lesson);
        Test savedTest = testRepository.save(test);

        // Создаем вопросы для теста
        createQuestionsForTest(savedTest, questionTopic);

        return savedTest;
    }

    private void createQuestionsForTest(Test test, String topic) {
        // Вопросы зависят от темы
        switch (topic) {
            case "программирование":
                createProgrammingQuestions(test);
                break;
            case "безопасность":
            case "криптография":
                createSecurityQuestions(test);
                break;
            default:
                createGenericQuestions(test);
                break;
        }
    }

    private void createProgrammingQuestions(Test test) {
        Question q1 = createQuestion(test, "Что такое переменная в программировании?", QuestionType.SINGLE_CHOICE, 1);
        createAnswerOption(q1, "Контейнер для хранения данных", true);
        createAnswerOption(q1, "Тип данных", false);
        createAnswerOption(q1, "Оператор", false);

        Question q2 = createQuestion(test, "Какие из следующих являются типами данных?", QuestionType.MULTIPLE_CHOICE, 2);
        createAnswerOption(q2, "Целое число (int)", true);
        createAnswerOption(q2, "Строка (string)", true);
        createAnswerOption(q2, "Переменная", false);
    }

    private void createSecurityQuestions(Test test) {
        Question q1 = createQuestion(test, "Что такое симметричное шифрование?", QuestionType.SINGLE_CHOICE, 1);
        createAnswerOption(q1, "Шифрование с одним ключом для шифрования и дешифрования", true);
        createAnswerOption(q1, "Шифрование с разными ключами", false);
        createAnswerOption(q1, "Шифрование без ключей", false);

        Question q2 = createQuestion(test, "Что такое хеш-функция?", QuestionType.SINGLE_CHOICE, 1);
        createAnswerOption(q2, "Функция, преобразующая данные в фиксированную строку", true);
        createAnswerOption(q2, "Метод шифрования", false);
    }

    private void createGenericQuestions(Test test) {
        Question q1 = createQuestion(test, "Что является важным в обучении?", QuestionType.MULTIPLE_CHOICE, 1);
        createAnswerOption(q1, "Регулярная практика", true);
        createAnswerOption(q1, "Понимание материала", true);
        createAnswerOption(q1, "Игнорирование ошибок", false);
    }

    private Question createQuestion(Test test, String questionText, QuestionType questionType, Integer points) {
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setQuestionType(questionType);
        question.setPoints(points);
        question.setTest(test);
        return questionRepository.save(question);
    }

    private AnswerOption createAnswerOption(Question question, String optionText, Boolean isCorrect) {
        AnswerOption option = new AnswerOption();
        option.setOptionText(optionText);
        option.setIsCorrect(isCorrect);
        option.setQuestion(question);
        return answerOptionRepository.save(option);
    }
}