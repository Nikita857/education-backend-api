package com.bm.education.controllers;

import com.bm.education.api.type.template.ApiResponse;
import com.bm.education.api.type.page.PageResponse;
import com.bm.education.dto.content.BlogPostDto;
import com.bm.education.dto.discussion.CommentDto;
import com.bm.education.mapper.BlogPostMapper;
import com.bm.education.mapper.DiscussionMapper;
import com.bm.education.models.BlogPost;
import com.bm.education.comment.model.Comment;
import com.bm.education.services.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/knowledge-base")
@RequiredArgsConstructor
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;
    private final BlogPostMapper blogPostMapper;
    private final DiscussionMapper discussionMapper;

    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<PageResponse<BlogPostDto>>> getKnowledgeBaseArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BlogPost> articlesPage = knowledgeBaseService.getAllArticles(page, size);
        List<BlogPostDto> articleDtos = articlesPage.getContent().stream()
                .map(blogPostMapper::toDto)
                .collect(Collectors.toList());

        PageResponse<BlogPostDto> response = new PageResponse<>();
        response.setContent(articleDtos);
        response.setPage(articlesPage.getNumber());
        response.setSize(articlesPage.getSize());
        response.setTotalElements(articlesPage.getTotalElements());
        response.setTotalPages(articlesPage.getTotalPages());
        response.setFirst(articlesPage.isFirst());
        response.setLast(articlesPage.isLast());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/articles")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BlogPostDto>> createArticle(@RequestBody BlogPostDto articleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BlogPost article = blogPostMapper.toEntity(articleDto);
        BlogPost savedArticle = knowledgeBaseService.createArticle(article, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(blogPostMapper.toDto(savedArticle)));
    }

    @PutMapping("/articles/{articleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BlogPostDto>> updateArticle(@PathVariable Long articleId,
            @RequestBody BlogPostDto articleDto) {
        BlogPost article = blogPostMapper.toEntity(articleDto);
        BlogPost updatedArticle = knowledgeBaseService.updateArticle(articleId, article);
        return ResponseEntity.ok(ApiResponse.success(blogPostMapper.toDto(updatedArticle)));
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getArticleComments(@PathVariable Long articleId) {
        List<Comment> comments = knowledgeBaseService.getArticleComments(articleId);
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> discussionMapper.toDto(comment))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(commentDtos));
    }

    @PostMapping("/articles/{articleId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CommentDto>> addComment(@PathVariable Long articleId,
            @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = discussionMapper.toEntity(commentDto);
        Comment savedComment = knowledgeBaseService.addComment(articleId, comment, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(discussionMapper.toDto(savedComment)));
    }
}