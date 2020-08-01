package com.github.ryneal.domain.service.embedded;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;

import java.util.List;

public interface EmbeddedReadAllService<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J> {
    List<T> readAll(J parentId);
}
