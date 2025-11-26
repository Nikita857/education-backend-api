package com.bm.education.mapper;

import com.bm.education.dto.content.BlogPostDto;
import com.bm.education.models.BlogPost;
import com.bm.education.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { UserMapper.class, DiscussionMapper.class })
public interface BlogPostMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTagsToStrings")
    BlogPostDto toDto(BlogPost blogPost);

    @Mapping(target = "tags", ignore = true) // Handle tags separately or implement reverse mapping
    BlogPost toEntity(BlogPostDto dto);

    @Named("mapTagsToStrings")
    default Set<String> mapTagsToStrings(Set<Tag> tags) {
        if (tags == null)
            return null;
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
}
