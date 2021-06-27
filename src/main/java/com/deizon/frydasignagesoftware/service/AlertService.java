/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.services.model.Validity;
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
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .enumField("type", input::getType, (val) -> data.setType((Alert.Type) val))
                .enumField(
                        "position",
                        input::getPosition,
                        (val) -> data.setPosition((Alert.Position) val))
                .stringField("value", input::getValue, data::setValue)
                .booleanField("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Alert processData(Alert entity, UpdateAlertInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .stringField(data::getName, entity::setName)
                        .enumField(data::getType, (val) -> entity.setType((Alert.Type) val))
                        .enumField(
                                data::getPosition,
                                (val) -> entity.setPosition((Alert.Position) val))
                        .stringField(data::getValue, data::setValue)
                        .stringField(data::getBorders, entity::setBorders)
                        .stringField(data::getHeight, entity::setHeight)
                        .stringField(data::getBackground, entity::setBackground)
                        .stringField(data::getTextColor, entity::setTextColor)
                        .stringField(data::getTextPosition, entity::setTextPosition)
                        .stringField(data::getTextSize, entity::setTextSize)
                        .booleanField(data::getRunning, entity::setRunning)
                        .objectField(data::getValidity, (val) -> entity.setValidity((Validity) val))
                        .getEntity(),
                data);
    }
}
