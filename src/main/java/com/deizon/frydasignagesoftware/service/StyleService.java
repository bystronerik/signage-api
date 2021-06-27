/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.style.CreateStyleInput;
import com.deizon.frydasignagesoftware.model.style.FindStyleInput;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.model.style.UpdateStyleInput;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StyleService
        extends BaseService<
                Style, CreateStyleInput, UpdateStyleInput, FindStyleInput, StyleRepository> {

    public StyleService(StyleRepository repository) {
        super(Style.class, repository, CreateStyleInput.class, UpdateStyleInput.class);
    }

    @Override
    protected Example<Style> createExample(FindStyleInput input) {
        final Style data = new Style();
        return new ExampleBuilder<>(data)
                .exact()
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .enumField("type", input::getType, (val) -> data.setType((Style.Type) val))
                .enumField(
                        "valueType",
                        input::getValueType,
                        (val) -> data.setValueType((Style.ValueType) val))
                .booleanField("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Style processData(Style entity, UpdateStyleInput data) {
        return super.processData(new EntityBuilder<>(entity)
                .stringField(data::getName, entity::setName)
                .enumField(data::getType, (val) -> entity.setType((Style.Type) val))
                .enumField(data::getValueType, (val) -> entity.setValueType((Style.ValueType) val))
                .stringField(data::getValue, entity::setValue)
                .getEntity(), data);
    }
}
