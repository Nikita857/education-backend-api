package com.bm.education.mapper;

import com.bm.education.dto.discussion.CommentDto;
import com.bm.education.dto.discussion.DiscussionTopicDto;
import com.bm.education.models.Comment;
import com.bm.education.models.DiscussionTopic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface DiscussionMapper {

    @Mapping(target = "comments", source = "comments")
    DiscussionTopicDto toDto(DiscussionTopic topic);

    @Mapping(target = "comments", ignore = true) // Handle comments separately or let JPA handle it
    DiscussionTopic toEntity(DiscussionTopicDto dto);

    CommentDto toDto(Comment comment);

    @Mapping(target = "blogPost", ignore = true)
    @Mapping(target = "discussionTopic", ignore = true)
    Comment toEntity(CommentDto dto);
}
