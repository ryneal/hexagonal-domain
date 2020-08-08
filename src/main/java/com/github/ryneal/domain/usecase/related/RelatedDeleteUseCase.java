package com.github.ryneal.domain.usecase.related;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;

public interface RelatedDeleteUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J> {
    void delete(J parentId, I id);
}
