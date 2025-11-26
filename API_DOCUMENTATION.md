# Документация по API

Этот документ описывает все основные маршруты (endpoints) REST API образовательного портала.

## Общие сведения

- **Базовый URL:** `/api/v1`
- **Аутентификация:** Большинство маршрутов требуют `Bearer` токен в заголовке `Authorization`.
- **Стандартный ответ:** Все успешные ответы от API обернуты в объект `ApiResponse<T>` со следующей структурой:
    ```json
    {
      "success": true,
      "message": "Описание результата операции",
      "data": { // Здесь находится запрашиваемый объект данных, DTO или null }
      "timestamp": "ISO 8601 дата и время операции" 
    }
    ```
- **Роли:** Доступ к некоторым маршрутам ограничен ролями:
    - `ROLE_USER` (Сотрудник)
    - `ROLE_INSTRUCTOR` (Преподаватель)
    - `ROLE_ADMIN` (Администратор)
    - `ROLE_HR` (HR-менеджер)
    - `ROLE_MANAGER` (Менеджер)

---

## 1. Аутентификация (`/auth`)

**Контроллер:** `AuthController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/login` | Вход в систему и получение токенов. | Все | `AuthRequest` | `AuthResponse` |
| `POST` | `/register` | Регистрация нового пользователя. | Все | `RegisterRequest` | `AuthResponse` |
| `POST` | `/refresh` | Обновление пары токенов (access и refresh) с использованием refresh токена. Реализована ротация токенов. | Все | `RefreshTokenRequest` | `AuthResponse` |
| `POST` | `/logout` | Выход из системы. Инвалидирует все refresh‑токены текущего пользователя. | Авторизованные | - | `null` |

---

## 2. Курсы (`/courses`)

**Контроллер:** `CourseController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/` | Получение списка всех курсов (с пагинацией и фильтрацией). | Все | - | `PageResponse<CourseDto>` |
| `GET` | `/{courseId}` | Получение детальной информации о курсе. | Все | - | `CourseDto` |
| `POST` | `/` | Создание нового курса. | `ADMIN`, `INSTRUCTOR` | `CourseDto` | `CourseDto` |
| `PUT` | `/{courseId}` | Обновление информации о курсе. | `ADMIN`, `INSTRUCTOR` | `CourseDto` | `CourseDto` |
| `DELETE` | `/{courseId}` | Удаление курса. | `ADMIN` | - | `null` |
| `POST` | `/{courseId}/enroll` | Запись текущего пользователя на курс. | Авторизованные | - | `null` |

---

## 3. Отзывы на курсы (`/courses/{courseId}/reviews`)

**Контроллер:** `CourseReviewController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/` | Оставить отзыв и оценку для курса. Пользователь может оставить только один отзыв на курс. | Авторизованные | `CourseReviewRequest` | `CourseReviewDto` |

---

## 4. Модули и Уроки (`/courses`, `/modules`, `/lessons`)

**Контроллер:** `ModuleLessonController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/courses/{courseId}/modules` | Получение всех модулей курса. | Все | - | `List<ModuleDto>` |
| `POST` | `/courses/{courseId}/modules` | Создание нового модуля в курсе. | `ADMIN`, `INSTRUCTOR` | `ModuleDto` | `ModuleDto` |
| `PUT` | `/modules/{moduleId}` | Обновление модуля. | `ADMIN`, `INSTRUCTOR` | `ModuleDto` | `ModuleDto` |
| `DELETE` | `/modules/{moduleId}` | Удаление модуля. | `ADMIN`, `INSTRUCTOR` | - | `null` |
| `GET` | `/modules/{moduleId}/lessons` | Получение всех уроков модуля. | Все | - | `List<LessonDto>` |
| `POST` | `/modules/{moduleId}/lessons` | Создание нового урока в модуле. | `ADMIN`, `INSTRUCTOR` | `LessonDto` | `LessonDto` |
| `PUT` | `/lessons/{lessonId}` | Обновление урока. | `ADMIN`, `INSTRUCTOR` | `LessonDto` | `LessonDto` |
| `DELETE` | `/lessons/{lessonId}` | Удаление урока. | `ADMIN`, `INSTRUCTOR` | - | `null` |

---

## 5. Тесты и Квизы (`/quizzes`, `/attempts`)

**Контроллер:** `QuizController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/{quizId}` | Получение информации о тесте (без правильных ответов). | Все | - | `QuizDto` |
| `POST` | `/` | Создание нового теста. | `ADMIN`, `INSTRUCTOR` | `QuizRequest` | `QuizDto` |
| `PUT` | `/{quizId}` | Обновление теста. | `ADMIN`, `INSTRUCTOR` | `QuizRequest` | `QuizDto` |
| `DELETE` | `/{quizId}` | Удаление теста. | `ADMIN`, `INSTRUCTOR` | - | `null` |
| `POST` | `/{quizId}/questions` | Добавление вопроса в тест. | `ADMIN`, `INSTRUCTOR` | `QuestionRequest` | `QuestionDto` |
| `POST` | `/{quizId}/attempt` | Начать новую попытку прохождения теста. | Авторизованные | - | `TestResultDto` |
| `PUT` | `/attempts/{attemptId}` | Сохранение промежуточных ответов пользователя. | Авторизованные | `QuizAnswersRequest` | `null` |
| `POST` | `/attempts/{attemptId}/submit` | Завершить попытку и отправить на проверку. | Авторизованные | - | `TestResultDto` |
| `GET` | `/attempts/{attemptId}/result` | Получение результата завершенной попытки. | Авторизованные | - | `TestResultDto` |

---

## 6. Прогресс Обучения (`/progress`)

**Контроллер:** `EnrollmentProgressController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/lessons/{lessonId}/complete` | Пометить урок (без теста) как завершенный. | Авторизованные | - | `null` |

---

## 7. Пользователи (`/users`)

**Контроллер:** `UserController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/me` | Получение информации о текущем пользователе. | Авторизованные | - | `UserDto` |
| `PUT` | `/me` | Обновление информации текущего пользователя. | Авторизованные | `UserDto` | `UserDto` |
| `GET` | `/` | Получение списка всех пользователей (с пагинацией). | `ADMIN`, `HR` | - | `PageResponse<UserDto>` |
| `POST` | `/` | Создание нового пользователя. | `ADMIN` | `UserDto` | `UserDto` |
| `GET` | `/{userId}` | Получение информации о конкретном пользователе. | Все | - | `UserDto` |
| `PUT` | `/{userId}` | Обновление пользователя. | `ADMIN` | `UserDto` | `UserDto` |
| `DELETE` | `/{userId}` | Деактивация пользователя. | `ADMIN` | - | `null` |
| `PUT` | `/{userId}/roles` | Назначение ролей пользователю. | `ADMIN` | `List<String>` | `UserDto` |
| `GET`| `/departments/distinct`| Получение списка уникальных названий отделов. | `ADMIN`, `HR` | - | `List<String>` |

---

## 8. Сертификаты (`/certificates`)

**Контроллер:** `CertificateController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/certificates` | Получить список сертификатов текущего пользователя. | Авторизованные | - | `List<CertificateDto>` |
| `GET` | `/courses/{courseId}/certificate` | Получить сертификат пользователя по курсу. | Авторизованные | - | `CertificateDto` |
| `POST` | `/courses/{courseId}/generate-certificate` | Сгенерировать сертификат для пользователя по завершённому курсу. | Авторизованные | - | `Certificate` |

---

## 9. Панель управления (`/dashboard`)

**Контроллер:** `DashboardController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/dashboard/summary` | Получить сводную информацию о системе (кол-во курсов, пользователей, активных сессий и т.д.). | `ADMIN` | - | `DashboardSummaryDto` |

---

## 10. Обсуждения (`/discussions`)

**Контроллер:** `DiscussionController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/discussions` | Получить список обсуждений. | Все | - | `List<DiscussionDto>` |
| `POST` | `/discussions` | Создать новое обсуждение. | `ADMIN`, `INSTRUCTOR` | `DiscussionRequest` | `DiscussionDto` |
| `PUT` | `/discussions/{id}` | Обновить обсуждение. | `ADMIN`, `INSTRUCTOR` | `DiscussionRequest` | `DiscussionDto` |
| `DELETE` | `/discussions/{id}` | Удалить обсуждение. | `ADMIN` | - | `null` |

---

## 11. Файлы (`/files`)

**Контроллер:** `FileController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/files/upload` | Загрузка файла. | Авторизованные | `MultipartFile` | `FileMetadataDto` |
| `GET` | `/files/{fileId}` | Скачать файл по ID. | Авторизованные | - | `Resource` |
| `DELETE` | `/files/{fileId}` | Удалить файл. | `ADMIN` | - | `null` |

---

## 12. База знаний (`/knowledge-base`)

**Контроллер:** `KnowledgeBaseController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/knowledge-base/articles` | Получить список статей. | Все | - | `List<ArticleDto>` |
| `GET` | `/knowledge-base/articles/{id}` | Получить отдельную статью. | Все | - | `ArticleDto` |
| `POST` | `/knowledge-base/articles` | Создать новую статью. | `ADMIN`, `INSTRUCTOR` | `ArticleRequest` | `ArticleDto` |
| `PUT` | `/knowledge-base/articles/{id}` | Обновить статью. | `ADMIN`, `INSTRUCTOR` | `ArticleRequest` | `ArticleDto` |
| `DELETE` | `/knowledge-base/articles/{id}` | Удалить статью. | `ADMIN` | - | `null` |

---

## 13. Онбординг (`/onboarding`)

**Контроллер:** `OnboardingController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/onboarding/steps` | Получить список шагов онбординга. | Авторизованные | - | `List<OnboardingStepDto>` |
| `POST` | `/onboarding/complete` | Завершить онбординг для текущего пользователя. | Авторизованные | - | `null` |

---

## 14. Отчётность (`/reporting`)

**Контроллер:** `ReportingController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/reporting/course-stats` | Получить статистику по курсам (кол‑во записей, средний рейтинг и т.д.). | `ADMIN` | - | `CourseStatsDto` |
| `GET` | `/reporting/user-progress` | Получить прогресс пользователей. | `ADMIN` | - | `List<UserProgressDto>` |

---

## 15. Навыки (`/skills`)

**Контроллер:** `SkillsController`

| Метод | Маршрут | Назначение | Доступ | Тело запроса | Ответ (`data`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/skills` | Получить список всех навыков. | Все | - | `List<SkillDto>` |
| `POST` | `/skills` | Добавить новый навык. | `ADMIN` | `SkillRequest` | `SkillDto` |
| `PUT` | `/skills/{id}` | Обновить навык. | `ADMIN` | `SkillRequest` | `SkillDto` |
| `DELETE` | `/skills/{id}` | Удалить навык. | `ADMIN` | - | `null` |

---

## 8. Структуры данных (DTO)

### 8.1. Общие DTO

#### `ApiResponse<T>`

Стандартный формат ответа API для всех успешных операций.

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `success` | `boolean` | Индикатор успешности операции. |
| `message` | `String` | Сообщение, описывающее результат операции. |
| `data` | `T` | Основной объект данных, возвращаемый операцией. Тип `T` зависит от конкретного маршрута. |
| `timestamp` | `Instant` | Временная метка выполнения операции в формате ISO 8601. |

#### `PageResponse<T>`

Стандартный формат ответа для пагинированных списков.

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `content` | `List<T>` | Список объектов данных для текущей страницы. |
| `page` | `int` | Номер текущей страницы (начиная с 0). |
| `size` | `int` | Количество элементов на текущей странице. |
| `totalElements` | `long` | Общее количество элементов во всех страницах. |
| `totalPages` | `int` | Общее количество страниц. |
| `first` | `boolean` | `true`, если это первая страница. |
| `last` | `boolean` | `true`, если это последняя страница. |

### 8.2. DTO для Аутентификации и Авторизации

#### `AuthRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `username` | `String` | Имя пользователя. |
| `password` | `String` | Пароль пользователя. |

#### `AuthResponse`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `accessToken` | `String` | JWT Access Token. |
| `refreshToken` | `String` | JWT Refresh Token. |
| `tokenType` | `String` | Тип токена (всегда "Bearer"). |
| `expiresIn` | `Long` | Время жизни Access Token в миллисекундах. |
| `user` | `UserDto` | Детали пользователя, выполнившего вход. |

#### `RegisterRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `firstName` | `String` | Имя пользователя. |
| `lastName` | `String` | Фамилия пользователя. |
| `department` | `String` | Отдел пользователя. |
| `jobTitle` | `String` | Должность пользователя. |
| `username` | `String` | Имя пользователя для входа. |
| `password` | `String` | Пароль пользователя. |
| `email` | `String` | Электронная почта пользователя. |

#### `RefreshTokenRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `refreshToken` | `String` | Refresh Token, полученный при входе или предыдущем обновлении. |

### 8.3. DTO для Курсов и Модулей

#### `CourseDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Integer` | Идентификатор курса. |
| `title` | `String` | Название курса. |
| `image` | `String` | URL или путь к изображению курса. |
| `description` | `String` | Полное описание курса. |
| `slug` | `String` | Уникальный URL-идентификатор курса. |
| `durationHours` | `Integer` | Предполагаемая продолжительность курса в часах. |
| `status` | `CourseStatus` | Статус курса (`ACTIVE`, `INACTIVE`, `DRAFT`). |
| `difficultyLevel` | `CourseDifficultyLevel` | Уровень сложности курса (`BEGINNER`, `INTERMEDIATE`, `ADVANCED`). |
| `format` | `CourseFormat` | Формат курса (например, `VIDEO`, `TEXT`, `INTERACTIVE`). |
| `scormPackagePath` | `String` | Путь к SCORM‑пакету (если применимо). |
| `averageRating` | `Double` | Средний рейтинг курса. |
| `totalReviews` | `Integer` | Общее количество отзывов. |
| `createdAt` | `LocalDateTime` | Дата создания курса. |
| `updatedAt` | `LocalDateTime` | Дата последнего обновления курса. |
| `categoryId` | `Long` | Идентификатор категории курса. |
| `tagIds` | `List<Long>` | Список идентификаторов тегов. |
| `modules` | `List<ModuleDto>` | Список модулей, входящих в курс. |

#### `ModuleDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Integer` | Идентификатор модуля. |
| `title` | `String` | Название модуля. |
| `slug` | `String` | Уникальный URL-идентификатор модуля. |
| `status` | `ModuleStatus` | Статус модуля (`ACTIVE`, `INACTIVE`, `DRAFT`). |
| `lessons` | `List<LessonDto>` | Список уроков, входящих в модуль. |

#### `LessonDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Integer` | Идентификатор урока. |
| `title` | `String` | Название урока. |
| `description` | `String` | Описание урока. |
| `textContent` | `String` | Текстовое содержимое урока (если `contentType` - `TEXT`). |
| `contentType` | `LessonContentType` | Тип контента урока (`TEXT`, `VIDEO`, `PDF`, `CODE`). |
| `video` | `String` | URL или путь к видеофайлу (если `contentType` - `VIDEO`). |
| `scormPackagePath` | `String` | Путь к SCORM‑пакету (если применимо). |
| `webinarUrl` | `String` | URL вебинара (если урок - вебинар). |
| `webinarStartTime` | `Instant` | Время начала вебинара. |
| `webinarDurationMinutes` | `Integer` | Продолжительность вебинара в минутах. |
| `filePath` | `String` | Путь к файлу (например, PDF, PPT) (если `contentType` - `PDF`). |
| `contentUrl` | `String` | URL внешнего контента. |
| `estimatedDurationMinutes` | `Integer` | Предполагаемая продолжительность урока в минутах. |
| `viewingProgressRequired` | `Boolean` | Требуется ли отслеживание прогресса просмотра. |
| `viewingPercentageThreshold` | `Integer` | Процент просмотра, необходимый для отметки о завершении. |

#### `CourseReviewDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Long` | Идентификатор отзыва. |
| `userId` | `Integer` | Идентификатор пользователя, оставившего отзыв. |
| `userFirstName` | `String` | Имя пользователя, оставившего отзыв. |
| `courseId` | `Integer` | Идентификатор курса, на который оставлен отзыв. |
| `rating` | `Integer` | Оценка курса (от 1 до 5). |
| `comment` | `String` | Текстовый комментарий к отзыву. |
| `createdAt` | `Instant` | Дата и время создания отзыва. |

### 8.4. DTO для Тестов и Квизов

#### `QuizDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Long` | Идентификатор теста. |
| `title` | `String` | Название теста. |
| `description` | `String` | Описание теста. |
| `questions` | `List<QuestionDto>` | Список вопросов теста. |

#### `QuestionDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Long` | Идентификатор вопроса. |
| `questionText` | `String` | Текст вопроса. |
| `questionType` | `QuestionType` | Тип вопроса (например, `SINGLE_CHOICE`, `MULTIPLE_CHOICE`). |
| `points` | `Integer` | Количество баллов за правильный ответ. |
| `answerOptions` | `List<AnswerOptionDto>` | Список вариантов ответов. |

#### `AnswerOptionDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Long` | Идентификатор варианта ответа. |
| `optionText` | `String` | Текст варианта ответа. |

#### `QuestionRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `questionText` | `String` | Текст вопроса. |
| `questionType` | `QuestionType` | Тип вопроса. |
| `points` | `Integer` | Количество баллов. |
| `answerOptions` | `List<AnswerOptionRequest>` | Список вариантов ответов с указанием правильности. |

#### `AnswerOptionRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `optionText` | `String` | Текст варианта ответа. |
| `isCorrect` | `boolean` | `true`, если вариант ответа является правильным. |

#### `QuizAnswersRequest`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `selectedAnswers` | `Map<Long, List<Long>>` | Выбранные варианты ответов: `questionId` → список `answerOptionIds`. |
| `textAnswers` | `Map<Long, String>` | Текстовые ответы: `questionId` → текстовый ответ. |

#### `TestResultDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Long` | Идентификатор результата попытки. |
| `testId` | `Long` | Идентификатор пройденного теста. |
| `userId` | `Integer` | Идентификатор пользователя. |
| `score` | `Integer` | Набранные баллы. |
| `maxScore` | `Integer` | Максимально возможные баллы. |
| `percentage` | `Double` | Процент набранных баллов. |
| `passed` | `Boolean` | `true`, если тест пройден успешно. |
| `completedAt` | `Instant` | Время завершения попытки. |

### 8.5. DTO для Пользователей

#### `UserDto`

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| `id` | `Integer` | Идентификатор пользователя. |
| `firstName` | `String` | Имя. |
| `lastName` | `String` | Фамилия. |
| `department` | `String` | Отдел. |
| `jobTitle` | `String` | Должность. |
| `username` | `String` | Имя пользователя (логин). |
| `avatar` | `String` | URL или путь к аватару. |
| `photoUrl` | `String` | URL или путь к фотографии. |
| `interests` | `String` | Интересы пользователя (строка). |
| `developmentGoals` | `String` | Цели развития пользователя (строка). |
| `email` | `String` | Электронная почта. |
| `isActive` | `Boolean` | `true`, если аккаунт активен. |
| `roles` | `Set<Role>` | Список ролей пользователя. |
| `createdAt` | `Instant` | Дата создания аккаунта. |
| `updatedAt` | `Instant` | Дата последнего обновления аккаунта. |

---

*Документация будет поддерживаться в актуальном состоянии по мере развития проекта.*
