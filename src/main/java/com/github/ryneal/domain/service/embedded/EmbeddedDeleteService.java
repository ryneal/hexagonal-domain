package com.github.ryneal.domain.service.embedded;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;

public interface EmbeddedDeleteService<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J> {
    void delete(J parentId, I id);
}
