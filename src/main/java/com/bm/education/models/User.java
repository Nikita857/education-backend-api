package com.bm.education.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Size(min = 2, max = 50, message = "Поле отдел должно быть от 2 до 50 символов")
    @NotNull
    @Column(name = "department", nullable = false, length = 50)
    private String department;

    @Size(min = 2, max = 50, message = "Поле должность должно быть от 2 до 50 символов")
    @NotNull
    @Column(name = "job_title", nullable = false, length = 50)
    private String jobTitle;

    @Column(name = "qualification", length = 50)
    private String qualification;

    @Size(min = 5, max = 50, message = "Поле логин должно быть от 5 до 50 символов")
    @NotNull
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(min = 8, max = 255, message = "Длин пароля должна быть от 8 до 20 символов")
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 200)
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Offer> offers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<UserProgress> userProgresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<Notification> notifications = new HashSet<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "interests", columnDefinition = "TEXT")
    private String interests;

    @Column(name = "development_goals", columnDefinition = "TEXT")
    private String developmentGoals;

    @Column(name = "sso_id")
    private String ssoId; // For SSO integration with Active Directory

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Email(message = "Неверный формат email")
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "refresh_token_expiry_date")
    private Instant refreshTokenExpiryDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<UserSkill> userSkills = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<IndividualDevelopmentPlan> individualDevelopmentPlans = new LinkedHashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<BlogPost> blogPosts = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate() {
        this.setQualification("1");
        this.setAvatar("avatar.webp");
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass)
            return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
