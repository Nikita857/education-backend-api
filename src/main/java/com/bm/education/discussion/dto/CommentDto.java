package com.bm.education.discussion.dto;

import com.bm.education.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private UserDto author;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean isLiked;
}
