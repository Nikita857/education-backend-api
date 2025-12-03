package com.bm.education.feature.discussion.dto;

import com.bm.education.feature.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionTopicDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
    private Boolean isClosed;
    private Integer viewCount;
    private Instant createdAt;
    private Instant updatedAt;
    private List<CommentDto> comments;
}
