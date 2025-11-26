package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.discussion.CommentDto;
import com.bm.education.dto.discussion.DiscussionTopicDto;
import com.bm.education.mapper.DiscussionMapper;
import com.bm.education.models.Comment;
import com.bm.education.models.DiscussionTopic;
import com.bm.education.services.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/discussions")
@RequiredArgsConstructor
public class DiscussionController {

    private final DiscussionService discussionService;
    private final DiscussionMapper discussionMapper;

    @GetMapping("/topics")
    public ResponseEntity<ApiResponse<List<DiscussionTopicDto>>> getDiscussionTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<DiscussionTopic> topics = discussionService.getAllTopics(page, size);
        List<DiscussionTopicDto> topicDtos = topics.stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(topicDtos));
    }

    @PostMapping("/topics")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<DiscussionTopicDto>> createDiscussionTopic(
            @RequestBody DiscussionTopicDto topicDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DiscussionTopic topic = discussionMapper.toEntity(topicDto);
        DiscussionTopic createdTopic = discussionService.createTopic(topic, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(discussionMapper.toDto(createdTopic)));
    }

    @PutMapping("/topics/{topicId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<DiscussionTopicDto>> updateTopic(@PathVariable Long topicId,
            @RequestBody DiscussionTopicDto topicDto) {
        DiscussionTopic topic = discussionMapper.toEntity(topicDto);
        DiscussionTopic updatedTopic = discussionService.updateTopic(topicId, topic);
        return ResponseEntity.ok(ApiResponse.success(discussionMapper.toDto(updatedTopic)));
    }

    @DeleteMapping("/topics/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteTopic(@PathVariable Long topicId) {
        discussionService.deleteTopic(topicId);
        return ResponseEntity.ok(ApiResponse.success("Topic deleted successfully"));
    }

    @GetMapping("/topics/{topicId}/posts")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getTopicPosts(@PathVariable Long topicId) {
        List<Comment> comments = discussionService.getTopicPosts(topicId);
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> discussionMapper.toDto(comment))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(commentDtos));
    }

    @PostMapping("/topics/{topicId}/posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CommentDto>> createPost(@PathVariable Long topicId,
            @RequestBody CommentDto postDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = discussionMapper.toEntity(postDto);
        Comment createdComment = discussionService.addPost(topicId, comment, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(discussionMapper.toDto(createdComment)));
    }
}