package com.bm.education.auth.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String lastName;

    @NotBlank(message = "Отдел не может быть пустым")
    @Size(min = 2, max = 50, message = "Отдел должен быть от 2 до 50 символов")
    private String department;

    @NotBlank(message = "Должность не может быть пустой")
    @Size(min = 2, max = 50, message = "Должность должна быть от 2 до 50 символов")
    private String jobTitle;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 5, max = 50, message = "Имя пользователя должно быть от 5 до 50 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    private String password;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;
}