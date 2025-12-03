package com.bm.education.feature.user.repository;

import com.bm.education.dto.user.UserDto;
import com.bm.education.feature.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(@Param("username") String username);

    @Query("SELECT DISTINCT u.department FROM User u")
    List<String> findDistinctDepartments();

    @Query("SELECT new com.bm.education.dto.user.UserDto(u.id, u.firstName, u.lastName, u.department, u.jobTitle, u.username, u.avatar, u.photoUrl, u.interests, u.developmentGoals, u.email, u.isActive, u.createdAt, u.updatedAt) FROM User u WHERE u.id = :id")
    Optional<UserDto> findUserDtoById(@Param("id") Long id);

    @Query("SELECT new com.bm.education.dto.user.UserDto(u.id, u.firstName, u.lastName, u.department, u.jobTitle, u.username, u.avatar, u.photoUrl, u.interests, u.developmentGoals, u.email, u.isActive, u.createdAt, u.updatedAt) FROM User u")
    Page<UserDto> findAllUserDtos(Pageable pageable);
}