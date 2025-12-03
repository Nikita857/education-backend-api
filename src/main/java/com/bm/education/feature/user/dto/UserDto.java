package com.bm.education.feature.user.dto;

import com.bm.education.feature.auth.model.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String department;
    private String jobTitle;
    private String username;
    private String avatar;
    private String photoUrl;
    private String interests;
    private String developmentGoals;
    @Email(message = "Неверный формат email")
    private String email;
    private Boolean isActive;
    private Set<Role> roles;
    private Instant createdAt;
    private Instant updatedAt;

    public UserDto(Integer id, String firstName, String lastName, String department, String jobTitle, String username, String avatar, String photoUrl, String interests, String developmentGoals, String email, Boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.jobTitle = jobTitle;
        this.username = username;
        this.avatar = avatar;
        this.photoUrl = photoUrl;
        this.interests = interests;
        this.developmentGoals = developmentGoals;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = null; // Not included in projection
    }
}