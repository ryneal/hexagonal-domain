package com.github.ryneal.domain.service.embedded;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface EmbeddedCreateService<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J> {
    Optional<T> create(J parentId, T t);
}
