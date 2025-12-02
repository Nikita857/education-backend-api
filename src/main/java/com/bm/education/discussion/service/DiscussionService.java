package com.bm.education.discussion.service;

import com.bm.education.comment.model.Comment;
import com.bm.education.discussion.model.DiscussionTopic;
import com.bm.education.user.model.User;
import com.bm.education.comment.repository.CommentRepository;
import com.bm.education.discussion.repository.DiscussionTopicRepository;
import com.bm.education.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {

    private final DiscussionTopicRepository discussionTopicRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<DiscussionTopic> getAllTopics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return discussionTopicRepository.findAll(pageable).getContent();
    }

    @Transactional
    public DiscussionTopic createTopic(DiscussionTopic topic, String username) {
        User author = userService.findByUsername(username);
        topic.setAuthor(author);
        return discussionTopicRepository.save(topic);
    }

    @Transactional
    public DiscussionTopic updateTopic(Long topicId, DiscussionTopic topicDetails) {
        DiscussionTopic topic = discussionTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + topicId));

        topic.setTitle(topicDetails.getTitle());
        topic.setContent(topicDetails.getContent());
        return discussionTopicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(Long topicId) {
        if (!discussionTopicRepository.existsById(topicId)) {
            throw new EntityNotFoundException("Topic not found: " + topicId);
        }
        discussionTopicRepository.deleteById(topicId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getTopicPosts(Long topicId) {
        DiscussionTopic topic = discussionTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + topicId));
        // Assuming Comment has a way to be fetched by topic.
        // Since we added discussionTopic to Comment, we need a repository method or
        // just use the collection in topic if eager/transactional.
        // But better to use repository for pagination if needed. For now, returning
        // list from entity or repo.
        // Let's assume we can get it from topic.getComments() but we need to initialize
        // it or fetch via repo.
        // Let's add a method to CommentRepository.
        return List.copyOf(topic.getComments());
    }

    @Transactional
    public Comment addPost(Long topicId, Comment comment, String username) {
        DiscussionTopic topic = discussionTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + topicId));
        User author = userService.findByUsername(username);

        comment.setDiscussionTopic(topic);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }
}
