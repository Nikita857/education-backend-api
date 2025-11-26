package com.bm.education.services;

import com.bm.education.dto.auth.RegisterRequest;
import com.bm.education.dto.common.PageResponse;
import com.bm.education.dto.user.UserDto;
import com.bm.education.mapper.UserMapper;
import com.bm.education.models.Role;
import com.bm.education.models.User;
import com.bm.education.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    public UserDto toUserDto(User user) {
        return userMapper.toDto(user);
    }

    public User fromUserDto(UserDto userDto) {
        return userMapper.toEntity(userDto);
    }

    public User registerNewUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new EntityExistsException("User with username " + request.getUsername() + " already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .department(request.getDepartment())
                .jobTitle(request.getJobTitle())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .build();

        return userRepository.save(user);
    }

    public PageResponse<UserDto> getAllUsers(int page, int size, String sortBy, String sortDir,
            String department, String jobTitle) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // DTO projection is now used, but filtering logic needs to be implemented
        // in the repository with a custom query if needed.
        Page<UserDto> usersPage = userRepository.findAllUserDtos(pageable);

        PageResponse<UserDto> response = new PageResponse<>();
        response.setContent(usersPage.getContent());
        response.setPage(usersPage.getNumber());
        response.setSize(usersPage.getSize());
        response.setTotalElements(usersPage.getTotalElements());
        response.setTotalPages(usersPage.getTotalPages());
        response.setFirst(usersPage.isFirst());
        response.setLast(usersPage.isLast());

        return response;
    }

    public UserDto getUserById(Integer userId) {
        return userRepository.findUserDtoById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }

    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // Update user fields
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setJobTitle(userDto.getJobTitle());
        user.setInterests(userDto.getInterests());
        user.setDevelopmentGoals(userDto.getDevelopmentGoals());
        user.setIsActive(userDto.getIsActive());

        userRepository.save(user);
        return userRepository.findUserDtoById(userId).orElseThrow();
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new EntityExistsException("User with username " + userDto.getUsername() + " already exists");
        }

        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .department(userDto.getDepartment())
                .jobTitle(userDto.getJobTitle())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode("password123"))
                .roles(userDto.getRoles() != null && !userDto.getRoles().isEmpty() ? userDto.getRoles()
                        : Set.of(Role.ROLE_USER))
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        return userRepository.findUserDtoById(savedUser.getId()).orElseThrow();
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // Instead of deleting, we can deactivate user
        user.setIsActive(false);
        userRepository.save(user);
    }

    public UserDto assignRoles(Integer userId, List<String> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Set<Role> userRoles = roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        user.setRoles(userRoles);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public List<String> getDistinctDepartments() {

        return userRepository.findDistinctDepartments();

    }

    public UserDto getCurrentUserDto() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = findByUsername(username);
        return userMapper.toDto(user);
    }

    public UserDto updateCurrentUserSettings(UserDto userDto) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = findByUsername(username);

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setJobTitle(userDto.getJobTitle());
        user.setInterests(userDto.getInterests());
        user.setDevelopmentGoals(userDto.getDevelopmentGoals());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
}
