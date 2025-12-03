package com.bm.education.feature.content.service;


import com.bm.education.feature.comment.model.Comment;
import com.bm.education.feature.comment.repository.CommentRepository;
import com.bm.education.feature.content.model.BlogPost;
import com.bm.education.feature.user.model.User;
import com.bm.education.feature.user.service.UserService;
import com.bm.education.feature.content.repository.BlogPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeBaseService {

    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<BlogPost> getAllArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return blogPostRepository.findAll(pageable);
    }

    @Transactional
    public BlogPost createArticle(BlogPost article, String username) {
        User author = userService.findByUsername(username);
        article.setAuthor(author);
        return blogPostRepository.save(article);
    }

    @Transactional
    public BlogPost updateArticle(Long articleId, BlogPost articleDetails) {
        BlogPost article = blogPostRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found: " + articleId));

        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setSummary(articleDetails.getSummary());
        article.setImageUrl(articleDetails.getImageUrl());
        article.setIsPublished(articleDetails.getIsPublished());

        return blogPostRepository.save(article);
    }

    @Transactional(readOnly = true)
    public List<Comment> getArticleComments(Long articleId) {
        BlogPost article = blogPostRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found: " + articleId));
        return List.copyOf(article.getComments());
    }

    @Transactional
    public Comment addComment(Long articleId, Comment comment, String username) {
        BlogPost article = blogPostRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found: " + articleId));
        User author = userService.findByUsername(username);

        comment.setBlogPost(article);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }
}