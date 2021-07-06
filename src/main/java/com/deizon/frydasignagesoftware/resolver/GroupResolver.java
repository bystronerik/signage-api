/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.group.CreateGroupInput;
import com.deizon.frydasignagesoftware.model.group.FindGroupInput;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.group.UpdateGroupInput;
import com.deizon.frydasignagesoftware.repository.GroupRepository;
import com.deizon.frydasignagesoftware.service.GroupService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class GroupResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final GroupRepository groupRepository;
    private final GroupService service;

    public Iterable<Group> findAllGroups(FindGroupInput input) {
        return this.service.findAll(input);
    }

    public Group findGroup(FindGroupInput input) {
        return this.service.find(input);
    }

    public Group createGroup(CreateGroupInput data) {
        return this.service.create(data);
    }

    public Group updateGroup(String id, UpdateGroupInput data) {
        return this.service.update(id, data);
    }

    public Group deleteGroup(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteGroup(String id) {
        return this.service.totalDelete(id);
    }
}
