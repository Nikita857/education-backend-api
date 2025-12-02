package com.bm.education.discussion.repository;

import com.bm.education.discussion.model.DiscussionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopic, Long> {
}
