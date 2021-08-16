/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.alert.CreateAlertInput;
import com.deizon.frydasignagesoftware.model.alert.FindAlertInput;
import com.deizon.frydasignagesoftware.model.alert.UpdateAlertInput;
import com.deizon.frydasignagesoftware.repository.AlertRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AlertService
        extends BaseService<
                Alert, CreateAlertInput, UpdateAlertInput, FindAlertInput, AlertRepository> {

    public AlertService(AlertRepository repository) {
        super(Alert.class, repository, CreateAlertInput.class, UpdateAlertInput.class);
    }

    @Override
    protected Example<Alert> createExample(FindAlertInput input) {
        final Alert data = new Alert();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("type", input::getType, data::setType)
                .field("position", input::getPosition, data::setPosition)
                .field("value", input::getValue, data::setValue)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Alert processData(Alert entity, UpdateAlertInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .field(data::getType, entity::setType)
                        .field(data::getPosition, entity::setPosition)
                        .field(data::getValue, entity::setValue)
                        .field(data::getBorders, entity::setBorders)
                        .field(data::getHeight, entity::setHeight)
                        .field(data::getBackground, entity::setBackground)
                        .field(data::getTextColor, entity::setTextColor)
                        .field(data::getTextPosition, entity::setTextPosition)
                        .field(data::getTextSize, entity::setTextSize)
                        .field(data::getRunning, entity::setRunning)
                        .field(data::getValidity, entity::setValidity)
                        .getEntity(),
                data);
    }
}
