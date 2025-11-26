package com.bm.education.repositories;

import com.bm.education.models.DiscussionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopic, Long> {
}
