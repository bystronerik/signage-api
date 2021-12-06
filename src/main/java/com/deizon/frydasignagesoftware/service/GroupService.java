package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.group.CreateGroupInput;
import com.deizon.frydasignagesoftware.model.group.FindGroupInput;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.group.UpdateGroupInput;
import com.deizon.frydasignagesoftware.repository.GroupRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class GroupService
        extends BaseService<
                Group, CreateGroupInput, UpdateGroupInput, FindGroupInput, GroupRepository> {

    public GroupService(GroupRepository repository) {
        super(Group.class, repository, CreateGroupInput.class, UpdateGroupInput.class);
    }

    @Override
    protected Example<Group> createExample(FindGroupInput input) {
        final Group data = new Group();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Group processData(Group entity, UpdateGroupInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .listField(data::getAssetLists, entity::getAssetLists)
                        .field(data::getAlert, entity::setAlert)
                        .getEntity(),
                data);
    }
}
