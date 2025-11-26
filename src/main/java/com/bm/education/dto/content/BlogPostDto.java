package com.bm.education.dto.content;

import com.bm.education.dto.discussion.CommentDto;
import com.bm.education.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostDto {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String imageUrl;
    private UserDto author;
    private Boolean isPublished;
    private Integer viewCount;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<String> tags; // Simplified for now
    private List<CommentDto> comments;
}
