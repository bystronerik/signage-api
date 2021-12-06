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
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("type", input::getType, data::setType)
                .field("valueType", input::getValueType, data::setValueType)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Style processData(Style entity, UpdateStyleInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .field(data::getType, entity::setType)
                        .field(data::getValueType, entity::setValueType)
                        .field(data::getValue, entity::setValue)
                        .getEntity(),
                data);
    }
}
