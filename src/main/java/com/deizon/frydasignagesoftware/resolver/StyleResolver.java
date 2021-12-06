package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.style.CreateStyleInput;
import com.deizon.frydasignagesoftware.model.style.FindStyleInput;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.model.style.UpdateStyleInput;
import com.deizon.frydasignagesoftware.service.StyleService;
import com.deizon.services.resolver.BaseResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class StyleResolver extends BaseResolver {

    private final StyleService service;

    public Iterable<Style> findAllStyles(FindStyleInput input) {
        return this.service.findAll(input);
    }

    public Style findStyle(FindStyleInput input) {
        return this.service.find(input);
    }

    public Style createStyle(CreateStyleInput data) {
        return this.service.create(data);
    }

    public Style updateStyle(String id, UpdateStyleInput data) {
        return this.service.update(id, data);
    }

    public Style deleteStyle(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteStyle(String id) {
        return this.service.totalDelete(id);
    }
}
