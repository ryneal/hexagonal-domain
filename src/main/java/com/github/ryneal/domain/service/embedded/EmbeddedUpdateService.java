package com.github.ryneal.domain.service.embedded;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface EmbeddedUpdateService<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J> {
    Optional<T> update(J parentId, I id, T t);
}
