/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.alert.CreateAlertInput;
import com.deizon.frydasignagesoftware.model.alert.FindAlertInput;
import com.deizon.frydasignagesoftware.model.alert.UpdateAlertInput;
import com.deizon.frydasignagesoftware.service.AlertService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class AlertResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final AlertService service;

    public Iterable<Alert> findAllAlerts(FindAlertInput input) {
        return this.service.findAll(input);
    }

    public Alert findAlert(FindAlertInput input) {
        return this.service.find(input);
    }

    public Alert createAlert(CreateAlertInput data) {
        return this.service.create(data);
    }

    public Alert updateAlert(String id, UpdateAlertInput data) {
        return this.service.update(id, data);
    }

    public Alert deleteAlert(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteAlert(String id) {
        return this.service.totalDelete(id);
    }
}
