/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.tag.CreateTagInput;
import com.deizon.frydasignagesoftware.model.tag.FindTagInput;
import com.deizon.frydasignagesoftware.model.tag.Tag;
import com.deizon.frydasignagesoftware.model.tag.UpdateTagInput;
import com.deizon.frydasignagesoftware.repository.TagRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TagService
        extends BaseService<Tag, CreateTagInput, UpdateTagInput, FindTagInput, TagRepository> {

    public TagService(TagRepository repository) {
        super(Tag.class, repository, CreateTagInput.class, UpdateTagInput.class);
    }

    @Override
    protected Example<Tag> createExample(FindTagInput input) {
        final Tag data = new Tag();
        return new ExampleBuilder<>(data)
                .exact()
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .enumField("color", input::getColor, (val) -> data.setColor((Tag.Color) val))
                .booleanField("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Tag processData(Tag entity, UpdateTagInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .stringField(data::getName, entity::setName)
                        .enumField(data::getColor, (val) -> entity.setColor((Tag.Color) val))
                        .getEntity(),
                data);
    }
}
