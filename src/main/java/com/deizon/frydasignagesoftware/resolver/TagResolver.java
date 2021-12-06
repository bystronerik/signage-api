package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.tag.CreateTagInput;
import com.deizon.frydasignagesoftware.model.tag.FindTagInput;
import com.deizon.frydasignagesoftware.model.tag.Tag;
import com.deizon.frydasignagesoftware.model.tag.UpdateTagInput;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.frydasignagesoftware.service.TagService;
import com.deizon.services.resolver.BaseResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class TagResolver extends BaseResolver {

    private final TagService service;

    public Iterable<Tag> findAllTags(FindTagInput input) {
        return this.service.findAll(input);
    }

    public Tag findTag(FindTagInput input) {
        return this.service.find(input);
    }

    public Tag createTag(CreateTagInput data) {
        return this.service.create(data);
    }

    public Tag updateTag(String id, UpdateTagInput data) {
        return this.service.update(id, data);
    }

    public Tag deleteTag(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteTag(String id) {
        return this.service.totalDelete(id);
    }
}
