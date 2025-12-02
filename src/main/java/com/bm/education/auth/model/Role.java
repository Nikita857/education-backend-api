package com.bm.education.auth.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,        // Employee (Learner)
    ROLE_ADMIN,       // Administrator
    ROLE_MANAGER,     // Manager
    ROLE_INSTRUCTOR,  // Instructor/Tutor
    ROLE_HR;          // HR / L&D Manager

    @Override
    public String getAuthority() {
        return name();
    }
}
